package com.epam.rft.atsy.service.impl;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

import com.epam.rft.atsy.persistence.entities.StatesEntity;
import com.epam.rft.atsy.persistence.repositories.StatesRepository;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class StateServiceImplTest {

  private static final Long VALID_STATE_ID = 1L;
  private static final Long INVALID_STATE_ID = 0L;
  private static final String VALID_STATE_NAME = "valid";
  private static final String INVALID_STATE_NAME = "invalid";

  private StatesEntity validStatesEntity;
  private StateDTO validStateDTO;

  @Mock
  private ModelMapper modelMapper;

  @Mock
  private StatesRepository statesRepository;

  @InjectMocks
  private StateServiceImpl stateService;

  @Before
  public void setUp() {
    validStatesEntity = StatesEntity.builder().id(VALID_STATE_ID).name(VALID_STATE_NAME).build();

    validStateDTO = StateDTO.builder().id(VALID_STATE_ID).name(VALID_STATE_NAME).build();
  }

  @Test
  public void getStateDtoByIdShouldReturnStateDtoWhenValidIdIsGiven() {
    //Given
    given(statesRepository.findOne(VALID_STATE_ID)).willReturn(validStatesEntity);
    given(modelMapper.map(validStatesEntity, StateDTO.class)).willReturn(validStateDTO);

    StateDTO
        expectedStateDTO =
        StateDTO.builder().id(VALID_STATE_ID).name(VALID_STATE_NAME).build();

    //When
    StateDTO result = stateService.getStateDtoById(VALID_STATE_ID);

    //Then
    assertThat(result, notNullValue());
    assertThat(result, is(expectedStateDTO));
  }

  @Test
  public void getStateDtoByIdShouldNotReturnStateDtoWhenInvalidIdIsGiven() {
    //Given
    given(statesRepository.findOne(INVALID_STATE_ID)).willReturn(null);

    //When
    StateDTO result = stateService.getStateDtoById(INVALID_STATE_ID);

    //Then
    assertThat(result, nullValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void getStateDtoByIdShouldThrowIllegalArgumentExceptionWhenIdIsNull() {
    //Given

    //When
    StateDTO result = stateService.getStateDtoById(null);

    //Then
  }

  @Test
  public void getStateDtoByNameShouldReturnStateDtoWhenValidNameIsGiven() {
    //Given
    given(statesRepository.findByName(VALID_STATE_NAME)).willReturn(validStatesEntity);
    given(modelMapper.map(validStatesEntity, StateDTO.class)).willReturn(validStateDTO);

    StateDTO
        expectedStateDTO =
        StateDTO.builder().id(VALID_STATE_ID).name(VALID_STATE_NAME).build();

    //When
    StateDTO result = stateService.getStateDtoByName(VALID_STATE_NAME);

    //Then
    assertThat(result, notNullValue());
    assertThat(result, is(expectedStateDTO));
  }

  @Test
  public void getStateDtoByNameShouldNotReturnStateDtoWhenInvalidNameIsGiven() {
    //Given
    given(statesRepository.findByName(INVALID_STATE_NAME)).willReturn(null);

    //When
    StateDTO result = stateService.getStateDtoByName(INVALID_STATE_NAME);

    //Then
    assertThat(result, nullValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void getStateDtoByNameShouldThrowIllegalArgumentExceptionWhenNameIsNull() {
    //Given

    //When
    StateDTO result = stateService.getStateDtoByName(null);

    //Then
  }

}
