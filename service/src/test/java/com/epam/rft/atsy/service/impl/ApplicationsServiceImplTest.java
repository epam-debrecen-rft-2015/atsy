package com.epam.rft.atsy.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.StatesHistoryRepository;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationsServiceImplTest {

  private static final Long APPLICATION_ID = 1L;
  private static final Date APPLICATION_CREATION_DATE = new Date();

  private static final Long CANDIDATE_ID = 1L;
  private static final Long NON_EXISTENT_CANDIDATE_ID = 1L;
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

  private static final int PAGE_NUMBER_ZERO = 0;
  private static final int PAGE_SIZE_TEN = 10;
  private static final int PAGE_SIZE_TWO = 2;
  private static final Pageable
      DEFAULT_PAGE_REQUEST =
      new PageRequest(PAGE_NUMBER_ZERO, PAGE_SIZE_TEN);
  private static final Pageable
      ZERO_TWO_PAGE_REQUEST =
      new PageRequest(PAGE_NUMBER_ZERO, PAGE_SIZE_TWO);

  private final ApplicationDTO applicationDTO =
      ApplicationDTO.builder().id(APPLICATION_ID).creationDate(APPLICATION_CREATION_DATE)
          .candidateId(CANDIDATE_ID)
          .positionId(POSITION_ID).channelId(CHANNEL_ID).build();

  private final List<ApplicationDTO> applicationDTOs = Arrays.asList(applicationDTO);

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

  private final List<ApplicationEntity> applicationEntities = Arrays.asList(applicationEntity);

  private final StateHistoryDTO
      stateHistoryDTO = StateHistoryDTO.builder().stateDTO(new StateDTO(1L, "newstate")).build();

  @Mock
  private ConverterService converterService;

  @Mock
  private StatesHistoryService statesHistoryService;

  @Mock
  private ApplicationsRepository applicationsRepository;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private StatesHistoryRepository statesHistoryRepository;

  @InjectMocks
  private ApplicationsServiceImpl applicationsService;

  @Test(expected = IllegalArgumentException.class)
  public void getApplicationsByCandidateDTOShouldThrowIllegalArgumentExceptionWhenCandidateDTOIsNull() {
    // Given

    // When
    applicationsService.getApplicationsByCandidateDTO(null);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void pageGetApplicationsByCandidateDTOShouldThrowIllegalArgumentExceptionWhenCandidateDTOIsNull() {
    // Given

    // When
    applicationsService.getApplicationsByCandidateDTO(null, PAGE_NUMBER_ZERO, PAGE_SIZE_TEN);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void getApplicationsByCandidateDTOShouldThrowIllegalArgumentExceptionWhenCandidateDTOsIdIsNull() {
    // Given
    CandidateDTO candidateDTO = CandidateDTO.builder().id(null).build();

    // When
    applicationsService.getApplicationsByCandidateDTO(candidateDTO);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void pageGetApplicationsByCandidateDTOShouldThrowIllegalArgumentExceptionWhenCandidateDTOsIdIsNull() {
    // Given
    CandidateDTO candidateDTO = CandidateDTO.builder().id(null).build();

    // When
    applicationsService
        .getApplicationsByCandidateDTO(candidateDTO, PAGE_NUMBER_ZERO, PAGE_SIZE_TEN);

    // Then
  }

  @Test
  public void getApplicationByCandidateDTOShouldReturnEmptyListWhenCandidateIdIsNotFound() {
    // Given
    CandidateDTO candidateDTO = CandidateDTO.builder().id(NON_EXISTENT_CANDIDATE_ID).build();
    given(candidateRepository.findOne(NON_EXISTENT_CANDIDATE_ID)).willReturn(null);
    given(applicationsRepository.findByCandidateEntity(null)).willReturn(Collections.emptyList());

    // When
    List<ApplicationDTO> result = applicationsService.getApplicationsByCandidateDTO(candidateDTO);

    // Then
    assertTrue(result.isEmpty());
  }

  @Test
  public void pageGetApplicationByCandidateDTOShouldReturnEmptyListWhenCandidateIdIsNotFound() {
    // Given
    CandidateDTO candidateDTO = CandidateDTO.builder().id(NON_EXISTENT_CANDIDATE_ID).build();
    given(candidateRepository.findOne(NON_EXISTENT_CANDIDATE_ID)).willReturn(null);
    given(applicationsRepository.findByCandidateEntity(null, DEFAULT_PAGE_REQUEST))
        .willReturn(new PageImpl<ApplicationEntity>(Collections.emptyList()));

    // When
    List<ApplicationDTO>
        result =
        applicationsService
            .getApplicationsByCandidateDTO(candidateDTO, PAGE_NUMBER_ZERO, PAGE_SIZE_TEN);

    // Then
    assertTrue(result.isEmpty());
  }

  @Test
  public void getApplicationByCandidateDTOShouldReturnOneElementListOfApplicationDTOWhenCandidateIdExists() {
    // Given
    CandidateDTO candidateDTO = CandidateDTO.builder().id(CANDIDATE_ID).build();
    List<ApplicationEntity> applicationEntities = Collections.singletonList(applicationEntity);
    List<ApplicationDTO> applicationDTOs = Collections.singletonList(applicationDTO);

    given(candidateRepository.findOne(CANDIDATE_ID)).willReturn(candidateEntity);
    given(applicationsRepository.findByCandidateEntity(candidateEntity))
        .willReturn(applicationEntities);
    given(converterService.convert(applicationEntities, ApplicationDTO.class))
        .willReturn(applicationDTOs);

    // When
    List<ApplicationDTO> result = applicationsService.getApplicationsByCandidateDTO(candidateDTO);

    // Then
    assertThat(result, equalTo(applicationDTOs));
  }

  @Test
  public void pageGetApplicationByCandidateDTOShouldReturnOneElementListOfApplicationDTOWhenCandidateIdExists() {
    // Given
    CandidateDTO candidateDTO = CandidateDTO.builder().id(CANDIDATE_ID).build();
    List<ApplicationEntity> applicationEntities = Collections.singletonList(applicationEntity);
    List<ApplicationDTO> applicationDTOs = Collections.singletonList(applicationDTO);

    given(candidateRepository.findOne(CANDIDATE_ID)).willReturn(candidateEntity);
    given(applicationsRepository.findByCandidateEntity(candidateEntity, DEFAULT_PAGE_REQUEST))
        .willReturn(new PageImpl<ApplicationEntity>(applicationEntities));
    given(converterService.convert(applicationEntities, ApplicationDTO.class))
        .willReturn(applicationDTOs);

    // When
    List<ApplicationDTO>
        result =
        applicationsService
            .getApplicationsByCandidateDTO(candidateDTO, PAGE_NUMBER_ZERO, PAGE_SIZE_TEN);

    // Then
    assertThat(result, equalTo(applicationDTOs));
  }

  @Test
  public void getApplicationByCandidateDTOShouldReturnThreeElementListOfApplicationDTOWhenCandidateIdExists() {
    // Given
    CandidateDTO candidateDTO = CandidateDTO.builder().id(CANDIDATE_ID).build();
    List<ApplicationEntity>
        applicationEntities =
        Arrays.asList(applicationEntity, applicationEntity, applicationEntity);
    List<ApplicationDTO>
        applicationDTOs =
        Arrays.asList(applicationDTO, applicationDTO, applicationDTO);

    given(candidateRepository.findOne(CANDIDATE_ID)).willReturn(candidateEntity);
    given(applicationsRepository.findByCandidateEntity(candidateEntity))
        .willReturn(applicationEntities);
    given(converterService.convert(applicationEntities, ApplicationDTO.class))
        .willReturn(applicationDTOs);

    // When
    List<ApplicationDTO> result = applicationsService.getApplicationsByCandidateDTO(candidateDTO);

    // Then
    assertThat(result, equalTo(applicationDTOs));
  }

  @Test
  public void pageGetApplicationByCandidateDTOShouldReturnThreeElementListOfApplicationDTOWhenCandidateIdExists() {
    // Given
    CandidateDTO candidateDTO = CandidateDTO.builder().id(CANDIDATE_ID).build();
    List<ApplicationEntity>
        applicationEntities =
        Arrays.asList(applicationEntity, applicationEntity, applicationEntity);
    List<ApplicationDTO>
        applicationDTOs =
        Arrays.asList(applicationDTO, applicationDTO, applicationDTO);

    given(candidateRepository.findOne(CANDIDATE_ID)).willReturn(candidateEntity);
    given(applicationsRepository.findByCandidateEntity(candidateEntity, DEFAULT_PAGE_REQUEST))
        .willReturn(new PageImpl<ApplicationEntity>(applicationEntities));
    given(converterService.convert(applicationEntities, ApplicationDTO.class))
        .willReturn(applicationDTOs);

    // When
    List<ApplicationDTO>
        result =
        applicationsService
            .getApplicationsByCandidateDTO(candidateDTO, PAGE_NUMBER_ZERO, PAGE_SIZE_TEN);

    // Then
    assertThat(result, equalTo(applicationDTOs));
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteApplicationsByCandidateDTOShouldThrowIllegalArgumentExceptionWhenCandidateDTOIsNull() {
    // When
    applicationsService.deleteApplicationsByCandidateDTO(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteApplicationsByCandidateDTOShouldThrowIllegalArgumentExceptionWhenCandidateDTOsIdIsNull() {
    CandidateDTO candidateDTO = CandidateDTO.builder().id(null).build();

    // When
    applicationsService.deleteApplicationsByCandidateDTO(candidateDTO);
  }

  @Test
  public void deleteApplicationsByCandidateDTOShouldDeleteApplicationsOfTheGivenCandidateDTO() {
    // Given
    CandidateDTO candidateDTO = CandidateDTO.builder().id(1L).build();

    given(applicationsService.getApplicationsByCandidateDTO(candidateDTO))
        .willReturn(applicationDTOs);
    given(converterService.convert(applicationDTOs, ApplicationEntity.class))
        .willReturn(applicationEntities);
    given(converterService.convert(applicationEntity, ApplicationDTO.class))
        .willReturn(applicationDTO);

    // When
    applicationsService.deleteApplicationsByCandidateDTO(candidateDTO);

    // Then
    verify(statesHistoryService, atLeastOnce()).deleteStateHistoriesByApplication(applicationDTO);
    verify(applicationsRepository, atLeastOnce()).delete(applicationEntity);
  }

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
    ApplicationDTO
        returnedApplicationDto =
        applicationsService.getApplicationDtoById(APPLICATION_ID);
    assertThat(returnedApplicationDto, is(nullValue()));

    // Then
    then(applicationsRepository).should().findOne(APPLICATION_ID);
    verifyZeroInteractions(converterService);
  }

  @Test
  public void getApplicationDtoByIdShouldReturnExistingApplicationDtoWhenApplicationEntityExists() {
    // Given
    given(applicationsRepository.findOne(APPLICATION_ID)).willReturn(applicationEntity);
    given(converterService.convert(applicationEntity, ApplicationDTO.class))
        .willReturn(applicationDTO);

    // When
    ApplicationDTO
        returnedApplicationDto =
        applicationsService.getApplicationDtoById(APPLICATION_ID);
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

    given(applicationsRepository.saveAndFlush(applicationEntity))
        .willReturn(this.applicationEntity);
    given(converterService.convert(applicationEntity, ApplicationDTO.class))
        .willReturn(applicationDTO);

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

    given(converterService.convert(applicationDTO, ApplicationEntity.class))
        .willReturn(applicationEntity);
    given(applicationsRepository.saveAndFlush(applicationEntity))
        .willReturn(this.applicationEntity);
    given(converterService.convert(applicationEntity, ApplicationDTO.class))
        .willReturn(applicationDTO);

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