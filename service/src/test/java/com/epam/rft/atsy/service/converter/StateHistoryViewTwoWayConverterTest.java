package com.epam.rft.atsy.service.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import com.epam.rft.atsy.persistence.entities.StatesHistoryEntity;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.converter.impl.StateHistoryViewTwoWayConverter;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryViewDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class StateHistoryViewTwoWayConverterTest {

  private static final Long STATE_ID = 1L;
  private static final String STATE_NAME = "state";

  private static final String STATE_HISTORY_DESCRIPTION = "ok";
  private static final Long STATE_HISTORY_OFFERED_MONEY = 5555L;
  private static final Long STATE_HISTORY_CLAIM = 5555L;
  private static final Short STATE_HISTORY_RESULT = 89;
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
      .channelEntity(channelEntity)
      .positionEntity(positionEntity)
      .creationDate(APPLICATION_CREATION_DATE)
      .description(STATE_HISTORY_DESCRIPTION)
      .result(STATE_HISTORY_RESULT)
      .offeredMoney(STATE_HISTORY_OFFERED_MONEY)
      .claim(STATE_HISTORY_CLAIM)
      .feedbackDate(APPLICATION_CREATION_DATE)
      .dayOfStart(APPLICATION_CREATION_DATE)
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

  private StateHistoryViewDTO stateHistoryViewDTO = StateHistoryViewDTO.builder()
      .id(STATE_HISTORY_ID)
      .candidateId(CANDIDATE_ID)
      .position(positionDTO)
      .channel(channelDTO)
      .applicationDTO(applicationDTO)
      .description(STATE_HISTORY_DESCRIPTION)
      .result(STATE_HISTORY_RESULT)
      .offeredMoney(STATE_HISTORY_OFFERED_MONEY)
      .claim(STATE_HISTORY_CLAIM)
      .feedbackDate(APPLICATION_CREATION_DATE)
      .dayOfStart(APPLICATION_CREATION_DATE)
      .stateDTO(stateDTO)
      .creationDate(APPLICATION_CREATION_DATE)
      .build();

  @Mock
  private ConverterService converterService;

  private StateHistoryViewTwoWayConverter stateHistoryViewTwoWayConverter;

  @Before
  public void setUp() {
    stateHistoryViewTwoWayConverter = new StateHistoryViewTwoWayConverter(converterService);
  }

  @Test(expected = IllegalArgumentException.class)
  public void firstTypeToSecondTypeShouldThrowIllegalArgumentExceptionWhenSourceIsNull() {
    //Given

    //When
    stateHistoryViewTwoWayConverter.firstTypeToSecondType(null);

    //Then
  }

  @Test
  public void firstTypeToSecondTypeShouldReturnCorrectStateHistoryViewDTO() {
    //Given
    given(converterService.convert(positionEntity, PositionDTO.class)).willReturn(positionDTO);
    given(converterService.convert(channelEntity, ChannelDTO.class)).willReturn(channelDTO);
    given(converterService.convert(applicationEntity, ApplicationDTO.class))
        .willReturn(applicationDTO);
    given(converterService.convert(statesEntity, StateDTO.class)).willReturn(stateDTO);

    //When
    StateHistoryViewDTO result =
        stateHistoryViewTwoWayConverter.firstTypeToSecondType(statesHistoryEntity);

    //Then
    assertThat(result, notNullValue());
    assertThat(result, is(stateHistoryViewDTO));

    then(converterService).should().convert(applicationEntity, ApplicationDTO.class);
    then(converterService).should().convert(channelEntity, ChannelDTO.class);
    then(converterService).should().convert(positionEntity, PositionDTO.class);
    then(converterService).should().convert(statesEntity, StateDTO.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void secondTypeToFirstTypeShouldThrowIllegalArgumentExceptionWhenSourceIsNull() {
    //Given

    //When
    stateHistoryViewTwoWayConverter.secondTypeToFirstType(null);

    //Then
  }

  @Test
  public void secondTypeToFirstTypeShouldReturnCorrectStateHistoryEntity() {
    //Given
    given(converterService.convert(applicationDTO, ApplicationEntity.class))
        .willReturn(applicationEntity);
    given(converterService.convert(channelDTO, ChannelEntity.class)).willReturn(channelEntity);
    given(converterService.convert(positionDTO, PositionEntity.class)).willReturn(positionEntity);
    given(converterService.convert(stateDTO, StatesEntity.class)).willReturn(statesEntity);

    //When
    StatesHistoryEntity result =
        stateHistoryViewTwoWayConverter.secondTypeToFirstType(stateHistoryViewDTO);

    //Then
    assertThat(result, notNullValue());
    assertThat(result, is(statesHistoryEntity));

    then(converterService).should().convert(applicationDTO, ApplicationEntity.class);
    then(converterService).should().convert(channelDTO, ChannelEntity.class);
    then(converterService).should().convert(positionDTO, PositionEntity.class);
    then(converterService).should().convert(stateDTO, StatesEntity.class);
  }
}
