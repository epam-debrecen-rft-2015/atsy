package com.epam.rft.atsy.service.impl;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.epam.rft.atsy.persistence.entities.StateFlowEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import com.epam.rft.atsy.persistence.repositories.StateFlowRepository;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateFlowDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class StateFlowServiceImplTest {

  private static final List<StateFlowEntity> EMPTY_STATE_FLOW_ENTITY_LIST = Collections.emptyList();

  private static final Long FIRST_STATE_ID = 1L;
  private static final Long SECOND_STATE_ID = 2L;
  private static final Long THIRD_STATE_ID = 3L;
  private static final Long INVALID_STATE_ID = 0L;
  private static final Long STATE_WITH_SINGLE_STATE_FLOW_ID = 4L;
  private static final Long STATE_WITH_THREE_STATE_FLOW_ID = 5L;

  private static final String FIRST_STATE_NAME = "FirstStateName";
  private static final String SECOND_STATE_NAME = "SecondStateName";
  private static final String THIRD_STATE_NAME = "ThirdStateName";
  private static final String INVALID_STATE_NAME = "InvalidName";
  private static final String STATE_WITH_SINGLE_STATE_FLOW_NAME = "SingleStateFlowStateName";
  private static final String STATE_WITH_THREE_STATE_FLOW_NAME = "ThreeStateFlowStateName";

  @Mock
  private StateFlowRepository stateFlowRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private StateFlowServiceImpl stateFlowService;

  @Test(expected = IllegalArgumentException.class)
  public void getStateFlowDTOByFromStateDTOShouldThrowIllegalArgumentExceptionWHenStateDtoIsNull() {
    //Given

    //When
    stateFlowService.getStateFlowDTOByFromStateDTO(null);

    //Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void getStateFlowDTOByFromStateDTOShouldThrowIllegalArgumentExceptionWhenIdIsNullInStateDto() {
    //Given
    StateDTO stateDTO = StateDTO.builder().id(null).name(FIRST_STATE_NAME).build();

    //When
    stateFlowService.getStateFlowDTOByFromStateDTO(stateDTO);

    //Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void getStateFlowDTOByFromStateDTOShouldThrowIllegalArgumentExceptionWhenNameIsNullInStateDto() {
    //Given
    StateDTO stateDTO = StateDTO.builder().id(FIRST_STATE_ID).name(null).build();

    //When
    stateFlowService.getStateFlowDTOByFromStateDTO(stateDTO);

    //Then
  }

  @Test
  public void getStateFlowDTOByFromStateDTOShouldReturnEmptyListWhenInvalidStateDtoIsGiven() {
    //Given
    StateDTO
        invalidStateDTO =
        StateDTO.builder().id(INVALID_STATE_ID).name(INVALID_STATE_NAME).build();

    StatesEntity
        invalidStatesEntity =
        StatesEntity.builder().id(INVALID_STATE_ID).name(INVALID_STATE_NAME).build();

    given(modelMapper.map(invalidStateDTO, StatesEntity.class)).willReturn(invalidStatesEntity);
    given(stateFlowRepository.findByFromStateEntity(invalidStatesEntity))
        .willReturn(EMPTY_STATE_FLOW_ENTITY_LIST);

    //When
    Collection<StateFlowDTO>
        result =
        stateFlowService.getStateFlowDTOByFromStateDTO(invalidStateDTO);

    //Then
    assertThat(result, notNullValue());
    assertThat(result, empty());

    then(modelMapper).should().map(invalidStateDTO, StatesEntity.class);
    then(stateFlowRepository).should().findByFromStateEntity(invalidStatesEntity);
  }

  @Test
  public void getStateFlowDTOByFromStateDTOShouldReturnListWithSingleElementWhenStateDtoHasSingleStateFlow() {
    //Given
    StateDTO stateDTO = StateDTO.builder().id(STATE_WITH_SINGLE_STATE_FLOW_ID).name(
        STATE_WITH_SINGLE_STATE_FLOW_NAME).build();

    StatesEntity
        statesEntity =
        StatesEntity.builder().id(STATE_WITH_SINGLE_STATE_FLOW_ID)
            .name(STATE_WITH_SINGLE_STATE_FLOW_NAME).build();

    StatesEntity
        stateEntityInStateFlowEntityList =
        StatesEntity.builder().id(FIRST_STATE_ID).name(FIRST_STATE_NAME).build();

    StateDTO
        stateDTOInStateFlowDTOList =
        StateDTO.builder().id(FIRST_STATE_ID).name(FIRST_STATE_NAME).build();

    List<StateFlowEntity> singleElementStateFlowEntityList = Collections.singletonList(
        StateFlowEntity.builder().fromStateEntity(statesEntity)
            .toStateEntity(stateEntityInStateFlowEntityList)
            .build()
    );

    List<StateFlowDTO> expectedSingleElementStateFlowDtoList = Collections.singletonList(
        StateFlowDTO.builder().fromStateDTO(stateDTO).toStateDTO(stateDTOInStateFlowDTOList)
            .build()
    );

    given(modelMapper.map(stateDTO, StatesEntity.class)).willReturn(statesEntity);

    given(stateFlowRepository.findByFromStateEntity(statesEntity))
        .willReturn(singleElementStateFlowEntityList);

    given(modelMapper
        .map(singleElementStateFlowEntityList.get(0).getFromStateEntity(), StateDTO.class))
        .willReturn(stateDTO);

    given(modelMapper
        .map(singleElementStateFlowEntityList.get(0).getToStateEntity(), StateDTO.class))
        .willReturn(stateDTOInStateFlowDTOList);

    //When
    Collection<StateFlowDTO> result = stateFlowService.getStateFlowDTOByFromStateDTO(stateDTO);

    //Then
    assertThat(result, notNullValue());
    assertThat(result, is(expectedSingleElementStateFlowDtoList));

    then(modelMapper).should().map(stateDTO, StatesEntity.class);
    then(stateFlowRepository).should().findByFromStateEntity(statesEntity);
    then(modelMapper).should()
        .map(singleElementStateFlowEntityList.get(0).getFromStateEntity(), StateDTO.class);
    then(modelMapper).should()
        .map(singleElementStateFlowEntityList.get(0).getToStateEntity(), StateDTO.class);
  }

  @Test
  public void getStateFlowDTOByFromStateDTOShouldReturnListWithThreeElementWhenStateDtoHasThreeStateFlow() {
    //Given
    StateDTO stateDTO = StateDTO.builder().id(STATE_WITH_THREE_STATE_FLOW_ID).name(
        STATE_WITH_THREE_STATE_FLOW_NAME).build();

    StatesEntity
        statesEntity =
        StatesEntity.builder().id(STATE_WITH_THREE_STATE_FLOW_ID)
            .name(STATE_WITH_THREE_STATE_FLOW_NAME).build();

    List<StatesEntity> statesEntitiesInStateFlowEntitiesList = Arrays.asList(
        StatesEntity.builder().id(FIRST_STATE_ID).name(FIRST_STATE_NAME).build(),
        StatesEntity.builder().id(SECOND_STATE_ID).name(SECOND_STATE_NAME).build(),
        StatesEntity.builder().id(THIRD_STATE_ID).name(THIRD_STATE_NAME).build()
    );

    List<StateDTO> stateDTOsInStateFlowDTOsList = Arrays.asList(
        StateDTO.builder().id(FIRST_STATE_ID).name(FIRST_STATE_NAME).build(),
        StateDTO.builder().id(SECOND_STATE_ID).name(SECOND_STATE_NAME).build(),
        StateDTO.builder().id(THIRD_STATE_ID).name(THIRD_STATE_NAME).build()
    );

    List<StateFlowEntity> threeElementStateFlowEntityList = Arrays.asList(
        StateFlowEntity.builder().fromStateEntity(statesEntity)
            .toStateEntity(statesEntitiesInStateFlowEntitiesList.get(0))
            .build(),
        StateFlowEntity.builder().fromStateEntity(statesEntity)
            .toStateEntity(statesEntitiesInStateFlowEntitiesList.get(1))
            .build(),
        StateFlowEntity.builder().fromStateEntity(statesEntity)
            .toStateEntity(statesEntitiesInStateFlowEntitiesList.get(2))
            .build()
    );

    List<StateFlowDTO> expectedThreeElementStateFlowDtoList = Arrays.asList(
        StateFlowDTO.builder().fromStateDTO(stateDTO)
            .toStateDTO(stateDTOsInStateFlowDTOsList.get(0)).build(),
        StateFlowDTO.builder().fromStateDTO(stateDTO)
            .toStateDTO(stateDTOsInStateFlowDTOsList.get(1)).build(),
        StateFlowDTO.builder().fromStateDTO(stateDTO)
            .toStateDTO(stateDTOsInStateFlowDTOsList.get(2)).build()
    );

    given(modelMapper.map(stateDTO, StatesEntity.class)).willReturn(statesEntity);

    given(stateFlowRepository.findByFromStateEntity(statesEntity))
        .willReturn(threeElementStateFlowEntityList);

    given(modelMapper
        .map(statesEntity, StateDTO.class))
        .willReturn(stateDTO);

    for (int i = 0; i < 3; ++i) {
      given(modelMapper
          .map(threeElementStateFlowEntityList.get(i).getToStateEntity(), StateDTO.class))
          .willReturn(stateDTOsInStateFlowDTOsList.get(i));
    }

    //When
    Collection<StateFlowDTO> result = stateFlowService.getStateFlowDTOByFromStateDTO(stateDTO);

    //Then
    assertThat(result, notNullValue());
    assertThat(result, is(expectedThreeElementStateFlowDtoList));

    then(modelMapper).should().map(stateDTO, StatesEntity.class);
    then(stateFlowRepository).should().findByFromStateEntity(statesEntity);
    then(modelMapper).should(times(3)).map(statesEntity, StateDTO.class);
    
    for (int i = 0; i < 3; ++i) {
      then(modelMapper).should()
          .map(threeElementStateFlowEntityList.get(i).getToStateEntity(), StateDTO.class);
    }
  }

}
