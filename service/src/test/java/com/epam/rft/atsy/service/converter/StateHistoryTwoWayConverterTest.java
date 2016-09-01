package com.epam.rft.atsy.service.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import com.epam.rft.atsy.persistence.entities.StatesHistoryEntity;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.converter.impl.StateHistoryTwoWayConverter;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class StateHistoryTwoWayConverterTest {

  private static final Long STATE_ID = 1L;
  private static final String STATE_NAME = "state";

  private static final String STATE_HISTORY_DESCRIPTION = "ok";
  private static final Long STATE_HISTORY_OFFERED_MONEY = 5555L;
  private static final Long STATE_HISTORY_CLAIM = 5555L;
  private static final Short STATE_HISTORY_RESULT = 56;
  private static final Long STATE_HISTORY_ID = 2L;

  private static final Long APPLICATION_ID = 1L;
  private static final Date APPLICATION_CREATION_DATE = new Date();

  private static final Long CANDIDATE_ID = 1L;
  private static final String CANDIDATE_DESCRIPTION = "Candidate description";
  private static final String CANDIDATE_EMAIL = "candidate@atsy.aa";
  private static final Short CANDIDATE_LANGUAGE_SKILL = 6;
  private static final String CANDIDATE_NAME = "Candidate D";
  private static final String CANDIDATE_PHONE_NUMBER = "+55555555555";
  private static final String CANDIDATE_REFERRER = "google";

  private static final Long POSITION_ID = 1L;
  private static final String POSITION_NAME = "Developer";

  private static final Long CHANNEL_ID = 1L;
  private static final String CHANNEL_NAME = "google";

  private StatesEntity statesEntity = StatesEntity.builder()
      .id(STATE_ID)
      .name(STATE_NAME)
      .build();

  private CandidateEntity candidateEntity = CandidateEntity.builder()
      .id(CANDIDATE_ID)
      .description(CANDIDATE_DESCRIPTION)
      .email(CANDIDATE_EMAIL)
      .languageSkill(CANDIDATE_LANGUAGE_SKILL)
      .name(CANDIDATE_NAME)
      .phone(CANDIDATE_PHONE_NUMBER)
      .referer(CANDIDATE_REFERRER)
      .build();

  private PositionEntity positionEntity = PositionEntity.builder()
      .id(POSITION_ID)
      .name(POSITION_NAME)
      .build();

  private ChannelEntity channelEntity = ChannelEntity.builder()
      .id(CHANNEL_ID)
      .name(CHANNEL_NAME)
      .build();

  private ApplicationEntity applicationEntity = ApplicationEntity.builder()
      .id(APPLICATION_ID)
      .creationDate(APPLICATION_CREATION_DATE)
      .candidateEntity(candidateEntity)
      .positionEntity(positionEntity)
      .channelEntity(channelEntity)
      .build();

  private StatesHistoryEntity statesHistoryEntity = StatesHistoryEntity.builder()
      .id(STATE_HISTORY_ID)
      .applicationEntity(applicationEntity)
      .creationDate(APPLICATION_CREATION_DATE)
      .languageSkill(CANDIDATE_LANGUAGE_SKILL)
      .description(STATE_HISTORY_DESCRIPTION)
      .result(STATE_HISTORY_RESULT)
      .offeredMoney(STATE_HISTORY_OFFERED_MONEY)
      .claim(STATE_HISTORY_CLAIM)
      .feedbackDate(APPLICATION_CREATION_DATE)
      .dayOfStart(APPLICATION_CREATION_DATE)
      .dateOfEnter(APPLICATION_CREATION_DATE)
      .statesEntity(statesEntity)
      .build();

  private ApplicationDTO applicationDTO = ApplicationDTO.builder()
      .id(APPLICATION_ID)
      .creationDate(APPLICATION_CREATION_DATE)
      .candidateId(CANDIDATE_ID)
      .positionId(POSITION_ID)
      .channelId(CHANNEL_ID)
      .build();

  private PositionDTO positionDTO = PositionDTO.builder()
      .id(POSITION_ID)
      .name(POSITION_NAME)
      .build();

  private ChannelDTO channelDTO = ChannelDTO.builder()
      .id(CHANNEL_ID)
      .name(CHANNEL_NAME)
      .build();

  private StateDTO stateDTO = StateDTO.builder()
      .id(STATE_ID)
      .name(STATE_NAME)
      .build();

  private StateHistoryDTO stateHistoryDTO = StateHistoryDTO.builder()
      .id(STATE_HISTORY_ID)
      .candidateId(CANDIDATE_ID)
      .position(positionDTO)
      .channel(channelDTO)
      .applicationDTO(applicationDTO)
      .languageSkill(CANDIDATE_LANGUAGE_SKILL)
      .description(STATE_HISTORY_DESCRIPTION)
      .result(STATE_HISTORY_RESULT)
      .offeredMoney(STATE_HISTORY_OFFERED_MONEY)
      .claim(STATE_HISTORY_CLAIM)
      .feedbackDate(APPLICATION_CREATION_DATE)
      .dayOfStart(APPLICATION_CREATION_DATE)
      .dateOfEnter(APPLICATION_CREATION_DATE)
      .stateDTO(stateDTO)
      .creationDate(APPLICATION_CREATION_DATE)
      .build();

  @Mock
  private ConverterService converterService;

  private StateHistoryTwoWayConverter stateHistoryTwoWayConverter;

  @Before
  public void setUp() {
    stateHistoryTwoWayConverter = new StateHistoryTwoWayConverter(converterService);
  }

  @Test(expected = IllegalArgumentException.class)
  public void firstTypeToSecondTypeShouldThrowIllegalArgumentExceptionWhenSourceIsNull() {
    //Given

    //When
    stateHistoryTwoWayConverter.secondTypeToFirstType(null);

    //Then
  }

  @Test
  public void firstTypeToSecondTypeShouldReturnCorrectStateHistoryDTO() {
    //Given
    given(converterService.convert(positionEntity, PositionDTO.class)).willReturn(positionDTO);
    given(converterService.convert(channelEntity, ChannelDTO.class)).willReturn(channelDTO);
    given(converterService.convert(applicationEntity, ApplicationDTO.class))
        .willReturn(applicationDTO);
    given(converterService.convert(statesEntity, StateDTO.class)).willReturn(stateDTO);

    //When
    StateHistoryDTO result = stateHistoryTwoWayConverter.firstTypeToSecondType(statesHistoryEntity);

    //Then
    assertThat(result, notNullValue());
    assertThat(result, is(stateHistoryDTO));
  }

  @Test(expected = IllegalArgumentException.class)
  public void secondTypeToFirstTypeShouldThrowIllegalArgumentExceptionWhenSourceIsNull() {
    //Given

    //When
    stateHistoryTwoWayConverter.secondTypeToFirstType(null);

    //Then
  }

  @Test
  public void secondTypeToFirstTypeShouldReturnCorrectStatesHistoryEntity() {
    //Given
    given(converterService.convert(applicationDTO, ApplicationEntity.class))
        .willReturn(applicationEntity);
    given(converterService.convert(stateDTO, StatesEntity.class)).willReturn(statesEntity);

    //When
    StatesHistoryEntity result = stateHistoryTwoWayConverter.secondTypeToFirstType(stateHistoryDTO);

    //Then
    assertThat(result, notNullValue());
    assertThat(result, is(statesHistoryEntity));
  }

}
