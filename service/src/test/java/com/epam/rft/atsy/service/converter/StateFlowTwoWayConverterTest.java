package com.epam.rft.atsy.service.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

import com.epam.rft.atsy.persistence.entities.StateFlowEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.converter.impl.StateFlowTwoWayConverter;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateFlowDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StateFlowTwoWayConverterTest {

  private static final long FROM_STATE_ID = 3L;
  private static final String FROM_STATE_NAME = "from";

  private static final long TO_STATE_ID = 4L;
  private static final String TO_STATE_NAME = "to";

  private StatesEntity fromStatesEntity = StatesEntity.builder()
      .id(FROM_STATE_ID)
      .name(FROM_STATE_NAME)
      .build();

  private StatesEntity toStatesEntity = StatesEntity.builder()
      .id(TO_STATE_ID)
      .name(TO_STATE_NAME)
      .build();

  private StateFlowEntity stateFlowEntity = StateFlowEntity.builder()
      .fromStateEntity(fromStatesEntity)
      .toStateEntity(toStatesEntity)
      .build();

  private StateDTO fromStateDTO = StateDTO.builder()
      .id(FROM_STATE_ID)
      .name(FROM_STATE_NAME)
      .build();

  private StateDTO toStateDTO = StateDTO.builder()
      .id(TO_STATE_ID)
      .name(TO_STATE_NAME)
      .build();

  private StateFlowDTO stateFlowDTO = StateFlowDTO.builder()
      .fromStateDTO(fromStateDTO)
      .toStateDTO(toStateDTO)
      .build();

  @Mock
  private ConverterService converterService;

  private StateFlowTwoWayConverter stateFlowTwoWayConverter;

  @Before
  public void setUp() {
    stateFlowTwoWayConverter = new StateFlowTwoWayConverter(converterService);
  }

  @Test(expected = IllegalArgumentException.class)
  public void firstTypeToSecondTypeShouldThrowIllegalArgumentExceptionWhenSourceIsNull() {
    //Given

    //When
    stateFlowTwoWayConverter.firstTypeToSecondType(null);

    //Then
  }

  @Test
  public void firstTypeToSecondTypeShouldReturnCorrectStateFlowDTO() {
    //Given
    given(converterService.convert(fromStatesEntity, StateDTO.class)).willReturn(fromStateDTO);
    given(converterService.convert(toStatesEntity, StateDTO.class)).willReturn(toStateDTO);

    //When
    StateFlowDTO result = stateFlowTwoWayConverter.firstTypeToSecondType(stateFlowEntity);

    //Then
    assertThat(result, notNullValue());
    assertThat(result, is(stateFlowDTO));
  }

  @Test(expected = IllegalArgumentException.class)
  public void secondTypeToFirstTypeShouldThrowIllegalArgumentExceptionWhenSourceIsNull() {
    //Given

    //When
    stateFlowTwoWayConverter.secondTypeToFirstType(null);

    //Then
  }

  @Test
  public void secondTypeToFirstTypeShouldReturnCorrectStateFlowEntity() {
    //Given
    given(converterService.convert(fromStateDTO, StatesEntity.class)).willReturn(fromStatesEntity);
    given(converterService.convert(toStateDTO, StatesEntity.class)).willReturn(toStatesEntity);

    //When
    StateFlowEntity result = stateFlowTwoWayConverter.secondTypeToFirstType(stateFlowDTO);

    //Then
    assertThat(result, notNullValue());
    assertThat(result, is(stateFlowEntity));
  }

}
