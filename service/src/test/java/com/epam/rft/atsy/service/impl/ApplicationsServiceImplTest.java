package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationsServiceImplTest {

  private static final Long APPLICATION_ID = 1L;
  private static final Date APPLICATION_CREATION_DATE = new Date();

  private static final Long CANDIDATE_ID = 1L;
  private static final String CANDIDATE_NAME = "Candidate A";
  private static final String CANDIDATE_EMAIL = "candidate.a@atsy.com";
  private static final String CANDIDATE_PHONE = "+36105555555";
  private static final String CANDIDATE_DESCRIPTION = "Elegáns, kicsit furi";
  private static final String CANDIDATE_REFERRER = "google";
  private static final Short CANDIDATE_LANG_SKILL = 5;

  private static final Long POSITION_ID = 1L;
  private static final String POSITION_NAME = "Fejlesztő";

  private static final Long CHANNEL_ID = 8L;
  private static final String CHANNEL_NAME = "állásbörze";

  private final ApplicationDTO applicationDTO =
      ApplicationDTO.builder().id(APPLICATION_ID).creationDate(APPLICATION_CREATION_DATE)
          .candidateId(CANDIDATE_ID)
          .positionId(POSITION_ID).channelId(CHANNEL_ID).build();

  private final CandidateEntity candidateEntity =
      CandidateEntity.builder().id(CANDIDATE_ID).name(CANDIDATE_NAME).email(CANDIDATE_EMAIL)
          .phone(CANDIDATE_PHONE).description(CANDIDATE_DESCRIPTION).referer(CANDIDATE_REFERRER)
          .languageSkill(CANDIDATE_LANG_SKILL).build();

  private final PositionEntity positionEntity =
      PositionEntity.builder().id(POSITION_ID).name(POSITION_NAME).build();

  private final ChannelEntity channelEntity =
      ChannelEntity.builder().id(CHANNEL_ID).name(CHANNEL_NAME).build();

  private final ApplicationEntity applicationEntity =
      ApplicationEntity.builder().id(APPLICATION_ID).creationDate(APPLICATION_CREATION_DATE)
          .candidateEntity(candidateEntity).positionEntity(positionEntity)
          .channelEntity(channelEntity).build();

  private final StateHistoryDTO
      stateHistoryDTO = StateHistoryDTO.builder().stateDTO(new StateDTO(1L, "newstate")).build();

  @Mock
  private ConverterService converterService;

  @Mock
  private StatesHistoryService statesHistoryService;

  @Mock
  private ApplicationsRepository applicationsRepository;

  @InjectMocks
  private ApplicationsServiceImpl applicationsService;

  @Test(expected = IllegalArgumentException.class)
  public void getApplicationDtoByIdShouldThrowIllegalArgumentExceptionWhenApplicationIdIsNull() {
    // Given

    // When
    applicationsService.getApplicationDtoById(null);

    // Then
  }

  @Test
  public void getApplicationDtoByIdShouldReturnNullWhenApplicationDtoNotExists() {
    // Given
    given(applicationsRepository.findOne(APPLICATION_ID)).willReturn(null);

    // When
    ApplicationDTO returnedApplicationDto = applicationsService.getApplicationDtoById(APPLICATION_ID);
    assertThat(returnedApplicationDto, is(nullValue()));

    // Then
    then(applicationsRepository).should().findOne(APPLICATION_ID);
    verifyZeroInteractions(converterService);
  }

  @Test
  public void getApplicationDtoByIdShouldReturnExistingApplicationDtoWhenApplicationEntityExists() {
    // Given
    given(applicationsRepository.findOne(APPLICATION_ID)).willReturn(applicationEntity);
    given(converterService.convert(applicationEntity, ApplicationDTO.class)).willReturn(applicationDTO);

    // When
    ApplicationDTO returnedApplicationDto = applicationsService.getApplicationDtoById(APPLICATION_ID);
    assertThat(returnedApplicationDto, notNullValue());
    assertThat(returnedApplicationDto, equalTo(applicationDTO));

    // Then
    then(applicationsRepository).should().findOne(APPLICATION_ID);
    then(converterService).should().convert(applicationEntity, ApplicationDTO.class);
  }

  @Test
  public void saveOrUpdateShouldSaveAProperApplicationDTO() {
    // Given
    given(converterService.convert(applicationDTO, ApplicationEntity.class))
        .willReturn(applicationEntity);
    given(applicationsRepository.saveAndFlush(applicationEntity)).willReturn(this.applicationEntity);
    given(converterService.convert(applicationEntity, ApplicationDTO.class)).willReturn(applicationDTO);

    // When
    ApplicationDTO result = applicationsService.saveOrUpdate(applicationDTO);

    // Then
    assertThat(result, notNullValue());
    assertThat(result, equalTo(applicationDTO));

    then(converterService).should().convert(applicationDTO, ApplicationEntity.class);
    then(applicationsRepository).should().saveAndFlush(applicationEntity);
    then(converterService).should().convert(applicationEntity, ApplicationDTO.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveOrUpdateWithApplicationDTONullShouldThrowIllegalArgumentException() {
    // Given

    // When
    applicationsService.saveOrUpdate(null);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveOrUpdateWithApplicationDTOsCandidateIDNullShouldThrowIllegalArgumentException() {
    // Given
    final ApplicationDTO applicationDTO =
        ApplicationDTO.builder().id(APPLICATION_ID).creationDate(new Date()).candidateId(null)
            .positionId(POSITION_ID).channelId(CHANNEL_ID).build();

    // When
    applicationsService.saveOrUpdate(applicationDTO);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveOrUpdateWithApplicationDTOsPositionIDNullShouldThrowIllegalArgumentException() {
    // Given
    final ApplicationDTO applicationDTO =
        ApplicationDTO.builder().id(APPLICATION_ID).creationDate(new Date())
            .candidateId(CANDIDATE_ID).positionId(null).channelId(CHANNEL_ID).build();

    // When
    applicationsService.saveOrUpdate(applicationDTO);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveOrUpdateWithApplicationDTOsChannelIDNullShouldThrowIllegalArgumentException() {
    // Given
    final ApplicationDTO applicationDTO =
        ApplicationDTO.builder().id(APPLICATION_ID).creationDate(new Date())
            .candidateId(CANDIDATE_ID).positionId(POSITION_ID).channelId(null).build();

    // When
    applicationsService.saveOrUpdate(applicationDTO);

    // Then
  }

  @Test
  public void saveApplicationShouldSaveAProperApplicationDTOAndStateDTO() {
    // Given
    final StateHistoryDTO stateHistoryDTO =
        StateHistoryDTO.builder().channel(new ChannelDTO(8L, null)).candidateId(1L)
            .position(new PositionDTO(1L, null)).description("sss")
            .stateDTO(new StateDTO(1L, "newstate")).build();

    given(converterService.convert(applicationDTO, ApplicationEntity.class)).willReturn(applicationEntity);
    given(applicationsRepository.saveAndFlush(applicationEntity)).willReturn(this.applicationEntity);
    given(converterService.convert(applicationEntity, ApplicationDTO.class)).willReturn(applicationDTO);

    // When
    Long result = applicationsService.saveApplication(applicationDTO, stateHistoryDTO);

    // Then
    assertThat(result, notNullValue());
    assertThat(result, equalTo(APPLICATION_ID));
    assertThat(stateHistoryDTO.getApplicationDTO(), equalTo(applicationDTO));

    then(converterService).should().convert(applicationDTO, ApplicationEntity.class);
    then(applicationsRepository).should().saveAndFlush(applicationEntity);
    then(converterService).should().convert(applicationEntity, ApplicationDTO.class);
    then(statesHistoryService).should().saveStateHistory(stateHistoryDTO);
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveApplicationWithApplicationDTONullShouldThrowIllegalArgumentException() {
    // Given

    // When
    applicationsService.saveApplication(null, stateHistoryDTO);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveApplicationWithStateDTONullShouldThrowIllegalArgumentException() {
    // Given

    // When
    applicationsService.saveApplication(applicationDTO, null);

    // Then
  }
}