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
import com.epam.rft.atsy.persistence.repositories.StatesHistoryRepository;
import com.epam.rft.atsy.service.converter.impl.CandidateApplicationOneWayConverter;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class CandidateApplicationOneWayConverterTest {

  private static final Long APPLICATION_ID = 1L;
  private static final Date APPLICATION_CREATION_DATE = new Date();

  private static final Long CANDIDATE_ID = 2L;
  private static final String CANDIDATE_DESCRIPTION = "Candidate description";
  private static final String CANDIDATE_EMAIL = "candidate@atsy.aa";
  private static final Short CANDIDATE_LANGUAGE_SKILL = 6;
  private static final String CANDIDATE_NAME = "Candidate D";
  private static final String CANDIDATE_PHONE_NUMBER = "+55555555555";
  private static final String CANDIDATE_REFERRER = "google";

  private static final Long POSITION_ID = 3L;
  private static final String POSITION_NAME = "Developer";

  private static final long CHANNEL_ID = 4L;
  private static final String CHANNEL_NAME = "google";

  private static final long STATE_ID = 5L;
  private static final String STATE_TYPE = "hr";

  private static final Long STATE_HISTORY_ID = 6L;

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

  private StatesEntity statesEntity = StatesEntity.builder()
      .id(STATE_ID)
      .name(STATE_TYPE)
      .build();

  private StatesHistoryEntity statesHistoryEntity = StatesHistoryEntity.builder()
      .id(STATE_HISTORY_ID)
      .applicationEntity(applicationEntity)
      .creationDate(APPLICATION_CREATION_DATE)
      .description(CANDIDATE_DESCRIPTION)
      .statesEntity(statesEntity)
      .build();

  private CandidateApplicationDTO candidateApplicationDTO = CandidateApplicationDTO.builder()
      .lastStateId(STATE_HISTORY_ID)
      .applicationId(APPLICATION_ID)
      .positionName(POSITION_NAME)
      .creationDate(APPLICATION_CREATION_DATE)
      .modificationDate(APPLICATION_CREATION_DATE)
      .stateType(STATE_TYPE)
      .build();

  @Mock
  private StatesHistoryRepository statesHistoryRepository;

  private CandidateApplicationOneWayConverter candidateApplicationOneWayConverter;

  @Before
  public void setup() {
    candidateApplicationOneWayConverter =
        new CandidateApplicationOneWayConverter(statesHistoryRepository);
  }

  @Test(expected = IllegalArgumentException.class)
  public void sourceTypeToTargetTypeShouldThrowIllegalArgumentExceptionWhenSourceIsNull() {
    //Given

    //When
    candidateApplicationOneWayConverter.firstTypeToSecondType(null);

    //Then
  }

  @Test
  public void sourceTypeToTargetTypeShouldReturnCorrectCandidateApplicationDTO() {
    //Given
    given(statesHistoryRepository
        .findTopByApplicationEntityOrderByCreationDateDesc(applicationEntity))
        .willReturn(statesHistoryEntity);

    //When
    CandidateApplicationDTO
        result =
        candidateApplicationOneWayConverter.firstTypeToSecondType(applicationEntity);

    //Then
    assertThat(result, notNullValue());
    assertThat(result, is(candidateApplicationDTO));
  }

}
