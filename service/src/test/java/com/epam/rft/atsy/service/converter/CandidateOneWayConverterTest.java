package com.epam.rft.atsy.service.converter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.converter.impl.CandidateOneWayConverter;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ui2016 on 2016.08.23..
 */

@RunWith(MockitoJUnitRunner.class)
public class CandidateOneWayConverterTest {

  private static final Long CANDIDATE_ID = 2L;
  private static final String CANDIDATE_DESCRIPTION = "Candidate description";
  private static final String CANDIDATE_EMAIL = "candidate@atsy.aa";
  private static final Short CANDIDATE_LANGUAGE_SKILL = 6;
  private static final String CANDIDATE_NAME = "Candidate D";
  private static final String CANDIDATE_PHONE_NUMBER = "+55555555555";
  private static final String CANDIDATE_REFERRER = "google";

  private static final long CHANNEL_ID = 4L;
  private static final String CHANNEL_NAME = "google";

  private static final Long DEVELOPER_APPLICATION_ID = 1L;
  private static final Long TESTER_APPLICATION_ID = 2L;
  private static final Long DESIGNER_APPLICATION_ID = 3L;
  private static final Date APPLICATION_CREATION_DATE = new Date();

  private static final Long DEVELOPER_POSITION_ID = 1L;
  private static final String DEVELOPER_POSITION_NAME = "Developer";
  private static final Long TESTER_POSITION_ID = 2L;
  private static final String TESTER_POSITION_NAME = "Tester";
  private static final Long DESIGNER_POSITION_ID = 3L;
  private static final String DESIGNER_POSITION_NAME = "Designer";

  private static final Set<String>
      positionsSet =
      new HashSet<>(
          Arrays.asList(DEVELOPER_POSITION_NAME, TESTER_POSITION_NAME, DESIGNER_POSITION_NAME));

  private CandidateEntity candidateEntity = CandidateEntity.builder()
      .id(CANDIDATE_ID)
      .description(CANDIDATE_DESCRIPTION)
      .email(CANDIDATE_EMAIL)
      .languageSkill(CANDIDATE_LANGUAGE_SKILL)
      .name(CANDIDATE_NAME)
      .phone(CANDIDATE_PHONE_NUMBER)
      .referer(CANDIDATE_REFERRER)
      .build();

  private CandidateDTO candidateDTO = CandidateDTO.builder()
      .id(CANDIDATE_ID)
      .description(CANDIDATE_DESCRIPTION)
      .email(CANDIDATE_EMAIL)
      .languageSkill(CANDIDATE_LANGUAGE_SKILL)
      .name(CANDIDATE_NAME)
      .phone(CANDIDATE_PHONE_NUMBER)
      .referer(CANDIDATE_REFERRER)
      .positions(positionsSet)
      .build();

  private ApplicationDTO developerApplication = ApplicationDTO.builder()
      .id(DEVELOPER_APPLICATION_ID)
      .creationDate(APPLICATION_CREATION_DATE)
      .candidateId(CANDIDATE_ID)
      .positionId(DEVELOPER_POSITION_ID)
      .channelId(CHANNEL_ID)
      .build();

  private ApplicationDTO testerApplication = ApplicationDTO.builder()
      .id(TESTER_APPLICATION_ID)
      .creationDate(APPLICATION_CREATION_DATE)
      .candidateId(CANDIDATE_ID)
      .positionId(TESTER_POSITION_ID)
      .channelId(CHANNEL_ID)
      .build();

  private ApplicationDTO designerApplication = ApplicationDTO.builder()
      .id(DESIGNER_APPLICATION_ID)
      .creationDate(APPLICATION_CREATION_DATE)
      .candidateId(CANDIDATE_ID)
      .positionId(DESIGNER_POSITION_ID)
      .channelId(CHANNEL_ID)
      .build();

  private List<ApplicationDTO>
      applicationsList =
      new ArrayList<>(Arrays.asList(developerApplication, testerApplication, designerApplication));

  private PositionDTO developerPosition = PositionDTO.builder()
      .id(DEVELOPER_POSITION_ID)
      .name(DEVELOPER_POSITION_NAME)
      .build();

  private PositionDTO testerPosition = PositionDTO.builder()
      .id(TESTER_POSITION_ID)
      .name(TESTER_POSITION_NAME)
      .build();

  private PositionDTO designerPosition = PositionDTO.builder()
      .id(DESIGNER_POSITION_ID)
      .name(DESIGNER_POSITION_NAME)
      .build();

  private CandidateOneWayConverter candidateOneWayConverter;

  @Mock
  private ApplicationsService applicationsService;

  @Mock
  private PositionService positionService;

  @Before
  public void setUp() {

    candidateOneWayConverter = new CandidateOneWayConverter(applicationsService, positionService);

  }

  @Test(expected = IllegalArgumentException.class)
  public void firstTypeToSecondTypeShouldThrowIllegalArgumentExceptionWhenSourceIsNull() {

    // Given

    // When
    CandidateDTO candidateDTO = candidateOneWayConverter.firstTypeToSecondType(null);

    // Then

  }

  @Test
  public void firstTypeToSecondTypeShouldReturnCorrectCandidateDTO() {

    // Given
    given(applicationsService.getApplicationsByCandidateDTO(any(CandidateDTO.class)))
        .willReturn(applicationsList);
    given(positionService.getPositionById(developerApplication.getPositionId()))
        .willReturn(developerPosition);
    given(positionService.getPositionById(testerApplication.getPositionId()))
        .willReturn(testerPosition);
    given(positionService.getPositionById(designerApplication.getPositionId()))
        .willReturn(designerPosition);

    // When
    CandidateDTO result = candidateOneWayConverter.firstTypeToSecondType(candidateEntity);

    // Then
    assertThat(result, equalTo(candidateDTO));

  }


}
