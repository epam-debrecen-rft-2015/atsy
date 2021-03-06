package com.epam.rft.atsy.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import com.epam.rft.atsy.persistence.entities.StatesHistoryEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.StatesHistoryRepository;
import com.epam.rft.atsy.persistence.repositories.StatesRepository;
import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class StatesHistoryServiceImplTest {

  private static final Long FIRST_ID = 1L;
  private static final Long SECOND_ID = 2L;
  private static final Long THIRD_ID = 3L;

  private static final String FIRST_POSITION_ENTITY_NAME = "First position";
  private static final String SECOND_POSITION_ENTITY_NAME = "Second position";
  private static final String THIRD_POSITION_ENTITY_NAME = "Third position";

  private static final String FIRST_STATE_TYPE_NAME = "First state";
  private static final String SECOND_STATE_TYPE_NAME = "Second state";
  private static final String THIRD_STATE_TYPE_NAME = "Third state";

  private static final ApplicationEntity APPLICATION_ENTITY_WITHOUT_STATE_HISTORY =
      ApplicationEntity.builder().id(FIRST_ID).deleted(false).build();

  private static final ApplicationEntity deletedApplicationEntity =
      ApplicationEntity.builder().id(FIRST_ID).deleted(true).build();

  private static final List<StatesHistoryEntity>
      EMPTY_STATE_HISTORY_ENTITY_LIST =
      Collections.emptyList();


  private static final List<StateHistoryDTO>
      EMPTY_STATE_HISTORY_VIEW_DTO_LIST =
      Collections.emptyList();

  private final Date futureDate = currentDateMinusSeconds(0L);
  private final Date presentDate = currentDateMinusSeconds(5L);
  private final Date pastDate = currentDateMinusSeconds(10L);

  private final CandidateEntity
      firstCandidateEntity =
      CandidateEntity.builder().id(FIRST_ID).build();

  private final List<ApplicationDTO>
      SINGLE_ELEMENT_APPLICATION_DTO_LIST =
      Collections.singletonList(
          ApplicationDTO.builder().id(FIRST_ID).candidateId(FIRST_ID).positionId(FIRST_ID)
              .creationDate(futureDate).build());

  private final List<ApplicationDTO>
      THREE_ELEMENT_APPLICATION_DTO_LIST =
      Arrays.asList(ApplicationDTO.builder().id(FIRST_ID).candidateId(FIRST_ID).positionId(FIRST_ID)
              .creationDate(futureDate).build(),
          ApplicationDTO.builder().id(SECOND_ID).candidateId(SECOND_ID).positionId(SECOND_ID)
              .creationDate(futureDate).build(),
          ApplicationDTO.builder().id(THIRD_ID).candidateId(THIRD_ID).positionId(THIRD_ID)
              .creationDate(futureDate).build());

  private final PositionEntity firstPositionEntity =
      PositionEntity.builder().id(FIRST_ID).name(FIRST_POSITION_ENTITY_NAME).build();

  private final PositionEntity secondPositionEntity =
      PositionEntity.builder().id(SECOND_ID).name(SECOND_POSITION_ENTITY_NAME).build();

  private final PositionEntity thirdPositionEntity =
      PositionEntity.builder().id(THIRD_ID).name(THIRD_POSITION_ENTITY_NAME).build();

  private final ApplicationEntity firstApplicationEntity =
      ApplicationEntity.builder().id(FIRST_ID).candidateEntity(firstCandidateEntity)
          .positionEntity(firstPositionEntity).creationDate(futureDate).deleted(false).build();

  private final ApplicationEntity secondApplicationEntity =
      ApplicationEntity.builder().id(SECOND_ID).candidateEntity(firstCandidateEntity)
          .positionEntity(secondPositionEntity).creationDate(presentDate).deleted(false).build();

  private final ApplicationEntity thirdApplicationEntity =
      ApplicationEntity.builder().id(THIRD_ID).candidateEntity(firstCandidateEntity)
          .positionEntity(thirdPositionEntity).creationDate(pastDate).deleted(false).build();

  private final StatesEntity
      firstStateEntity =
      StatesEntity.builder().id(FIRST_ID).name(FIRST_STATE_TYPE_NAME).build();

  private final StatesEntity secondStateEntity =
      StatesEntity.builder().id(SECOND_ID).name(SECOND_STATE_TYPE_NAME).build();

  private final StatesEntity
      thirdStateEntity =
      StatesEntity.builder().id(THIRD_ID).name(THIRD_STATE_TYPE_NAME).build();

  private final StatesHistoryEntity firstStatesHistoryEntity =
      StatesHistoryEntity.builder().id(FIRST_ID).applicationEntity(firstApplicationEntity)
          .statesEntity(firstStateEntity).creationDate(futureDate).build();

  private final StatesHistoryEntity secondStatesHistoryEntity =
      StatesHistoryEntity.builder().id(SECOND_ID).applicationEntity(secondApplicationEntity)
          .statesEntity(secondStateEntity).creationDate(presentDate)
          .build();

  private final StatesHistoryEntity thirdStatesHistoryEntity =
      StatesHistoryEntity.builder().id(THIRD_ID).applicationEntity(thirdApplicationEntity)
          .statesEntity(thirdStateEntity)
          .creationDate(pastDate).build();

  private final StateHistoryDTO
      stateHistoryDTOWithCreationDate =
      StateHistoryDTO.builder().id(FIRST_ID).creationDate(presentDate).build();

  private final StateDTO
      firstStateDTO =
      StateDTO.builder().id(FIRST_ID).name(FIRST_STATE_TYPE_NAME).build();

  private final StateHistoryDTO
      firstStateHistoryDtoWithStateDto =
      StateHistoryDTO.builder().id(FIRST_ID).stateDTO(firstStateDTO).build();

  private final List<StatesHistoryEntity> statesHistoryEntityListWithSingleElement =
      Collections.singletonList(firstStatesHistoryEntity);

  private final List<StatesHistoryEntity> statesHistoryEntityListWithThreeElements =
      Arrays.asList(firstStatesHistoryEntity, secondStatesHistoryEntity, thirdStatesHistoryEntity);

  private final List<StateHistoryDTO> stateViewHistoryDTOListWithSingleElement =
      Collections.singletonList(stateHistoryDTOWithCreationDate);

  private final List<StateHistoryDTO> stateViewHistoryDTOListWithThreeElements =
      Arrays.asList(stateHistoryDTOWithCreationDate, stateHistoryDTOWithCreationDate,
          stateHistoryDTOWithCreationDate);

  @Mock
  private ConverterService converterService;

  @Mock
  private ApplicationsService applicationsService;

  @Mock
  private StatesHistoryRepository statesHistoryRepository;

  @Mock
  private ApplicationsRepository applicationsRepository;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private StatesRepository statesRepository;

  @InjectMocks
  private StatesHistoryServiceImpl statesHistoryService;

  @Test(expected = IllegalArgumentException.class)
  public void getStatesByApplicationIdShouldThrowIllegalArgumentExceptionWhenApplicationIdIsNull() {
    // Given

    // When
    statesHistoryService.getStateHistoriesByApplicationId(null);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void getStatesByApplicationIdShouldThrowIllegalArgumentExceptionWhenApplicationIsNull() {
    // Given
    given(applicationsRepository.findOne(FIRST_ID)).willReturn(null);

    // When
    statesHistoryService.getStateHistoriesByApplicationId(FIRST_ID);

    // Then
  }

  @Test
  public void getStatesByApplicationIdShouldReturnAnEmptyList() {
    // Given
    given(applicationsRepository.findOne(FIRST_ID)).willReturn(
        APPLICATION_ENTITY_WITHOUT_STATE_HISTORY);
    given(statesHistoryRepository
        .findByApplicationEntityOrderByCreationDateDesc(APPLICATION_ENTITY_WITHOUT_STATE_HISTORY))
        .willReturn(EMPTY_STATE_HISTORY_ENTITY_LIST);

    given(converterService.convert(EMPTY_STATE_HISTORY_ENTITY_LIST, StateHistoryDTO.class))
        .willReturn(EMPTY_STATE_HISTORY_VIEW_DTO_LIST);

    // When
    List<StateHistoryDTO>
        stateHistoryDTOList =
        statesHistoryService.getStateHistoriesByApplicationId(FIRST_ID);

    // Then
    assertThat(stateHistoryDTOList, notNullValue());
    assertThat(stateHistoryDTOList.isEmpty(), is(true));

    then(applicationsRepository).should().findOne(FIRST_ID);
    then(statesHistoryRepository).should()
        .findByApplicationEntityOrderByCreationDateDesc(APPLICATION_ENTITY_WITHOUT_STATE_HISTORY);
  }

  @Test
  public void getStatesByApplicationIdShouldReturnAnEmptyListForDeletedApplication() {
    //Given
    given(applicationsRepository.findOne(FIRST_ID)).willReturn(deletedApplicationEntity);

    //When
    List<StateHistoryDTO> stateHistory = statesHistoryService.getStateHistoriesByApplicationId(FIRST_ID);

    //Then
    assertThat(stateHistory, notNullValue());
    assertThat(stateHistory.isEmpty(), is(true));
  }
  @Test
  public void getStatesByApplicationIdShouldReturnAListWithSingleElement() {
    // Given
    given(applicationsRepository.findOne(FIRST_ID)).willReturn(firstApplicationEntity);
    given(statesHistoryRepository
        .findByApplicationEntityOrderByCreationDateDesc(firstApplicationEntity))
        .willReturn(statesHistoryEntityListWithSingleElement);
    given(converterService

        .convert(statesHistoryEntityListWithSingleElement, StateHistoryDTO.class))
        .willReturn(stateViewHistoryDTOListWithSingleElement);

    // When
    List<StateHistoryDTO>
        stateHistoryDTOList =
        statesHistoryService.getStateHistoriesByApplicationId(FIRST_ID);

    // Then
    assertThat(stateHistoryDTOList, notNullValue());
    assertThat(stateHistoryDTOList.isEmpty(), is(false));
    assertThat(stateHistoryDTOList, equalTo(stateViewHistoryDTOListWithSingleElement));

    then(applicationsRepository).should().findOne(FIRST_ID);
    then(statesHistoryRepository).should()
        .findByApplicationEntityOrderByCreationDateDesc(firstApplicationEntity);
  }

  @Test
  public void getStatesByApplicationIdShouldReturnAListWithThreeElement() {
    // Given
    given(applicationsRepository.findOne(FIRST_ID)).willReturn(firstApplicationEntity);
    given(statesHistoryRepository
        .findByApplicationEntityOrderByCreationDateDesc(firstApplicationEntity))
        .willReturn(statesHistoryEntityListWithThreeElements);
    given(converterService

        .convert(statesHistoryEntityListWithThreeElements, StateHistoryDTO.class))
        .willReturn(stateViewHistoryDTOListWithThreeElements);

    // When
    List<StateHistoryDTO>
        stateHistoryDTOList =
        statesHistoryService.getStateHistoriesByApplicationId(FIRST_ID);

    // Then
    assertThat(stateHistoryDTOList, notNullValue());
    assertThat(stateHistoryDTOList.isEmpty(), is(false));
    assertThat(stateHistoryDTOList, equalTo(stateViewHistoryDTOListWithThreeElements));

    then(applicationsRepository).should().findOne(FIRST_ID);
    then(statesHistoryRepository).should()
        .findByApplicationEntityOrderByCreationDateDesc(firstApplicationEntity);
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteStateHistoriesByApplicationShouldThrowIllegalArgumentExceptionWhenTheGivenApplicationIsNull() {
    // When
    statesHistoryService.deleteStateHistoriesByApplication(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteStateHistoriesByApplicationShouldThrowIllegalArgumentExceptionWhenTheGivenApplicationsIdIsNull() {
    // When
    ApplicationDTO applicationDTO = ApplicationDTO.builder().id(null).build();

    statesHistoryService.deleteStateHistoriesByApplication(applicationDTO);
  }

  @Test
  public void deleteStateHistoriesByApplicationShouldDeleteTheCorrespondingStateHistories() {
    // Given
    ApplicationDTO applicationDTO = ApplicationDTO.builder().id(FIRST_ID).build();
    given(applicationsRepository.findOne(applicationDTO.getId()))
        .willReturn(firstApplicationEntity);
    given(statesHistoryRepository
        .findByApplicationEntityOrderByCreationDateDesc(firstApplicationEntity))
        .willReturn(statesHistoryEntityListWithSingleElement);

    // When
    statesHistoryService.deleteStateHistoriesByApplication(applicationDTO);

    // Then
    verify(statesHistoryRepository, atLeastOnce()).delete(statesHistoryEntityListWithSingleElement);
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveStateShouldThrowIllegalArgumentExceptionWhenApplicationIdIsNull() {
    // Given

    // When
    statesHistoryService.saveStateHistory(null);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveStateHistoryShouldThrowIllegalArgumentExceptionWhenStateDtoIsNull() {
    // Given
    StateHistoryDTO stateHistoryDTO = StateHistoryDTO.builder().stateDTO(null).build();

    // When
    statesHistoryService.saveStateHistory(stateHistoryDTO);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveStateHistoryShouldThrowIllegalArgumentExceptionWhenStateDtoIdIsNull() {
    // Given
    StateDTO stateDTO = StateDTO.builder().id(null).build();
    StateHistoryDTO stateHistoryDTO = StateHistoryDTO.builder().stateDTO(stateDTO).build();

    // When
    statesHistoryService.saveStateHistory(stateHistoryDTO);

    //Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveStateHistoryShouldThrowIllegalArgumentExceptionWhenStateEntityNotExists() {
    // Given
    given(statesRepository.findOne(FIRST_ID)).willReturn(null);

    // When
    statesHistoryService.saveStateHistory(firstStateHistoryDtoWithStateDto);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveStateHistoryShouldThrowIllegalArgumentExceptionWhenApplicationDtoIsNotValid() {
    // Given
    given(statesRepository.findOne(FIRST_ID)).willReturn(firstStateEntity);
    given(converterService.convert(firstStateHistoryDtoWithStateDto, StatesHistoryEntity.class))
        .willReturn(firstStatesHistoryEntity);
    given(applicationsService.saveOrUpdate(firstStateHistoryDtoWithStateDto.getApplicationDTO()))
        .willThrow(IllegalArgumentException.class);

    // When
    statesHistoryService.saveStateHistory(firstStateHistoryDtoWithStateDto);

    // Then
  }

  @Test
  public void saveStateHistoryShouldSaveWhenAllParamIsRightAndCreationDateIsNull() {
    // Given
    firstStateHistoryDtoWithStateDto.setCreationDate(null);
    given(statesRepository.findOne(FIRST_ID)).willReturn(firstStateEntity);
    given(converterService.convert(firstStateHistoryDtoWithStateDto, StatesHistoryEntity.class))
        .willReturn(firstStatesHistoryEntity);
    given(statesHistoryRepository.saveAndFlush(firstStatesHistoryEntity))
        .willReturn(firstStatesHistoryEntity);

    // When
    Long result = statesHistoryService.saveStateHistory(firstStateHistoryDtoWithStateDto);

    // Then
    assertThat(result, notNullValue());
    assertThat(result, equalTo(FIRST_ID));
    assertThat(firstStatesHistoryEntity.getCreationDate(),
        greaterThan(currentDateMinusSeconds(5L)));

    then(statesRepository).should().findOne(FIRST_ID);
    then(converterService).should()
        .convert(firstStateHistoryDtoWithStateDto, StatesHistoryEntity.class);
    then(statesHistoryRepository).should().saveAndFlush(firstStatesHistoryEntity);
    then(applicationsService).should()
        .saveOrUpdate(firstStateHistoryDtoWithStateDto.getApplicationDTO());
  }

  @Test
  public void saveStateHistoryShouldSaveWhenAllParamIsRightAndCreationDateIsExisting() {
    // Given
    firstStateHistoryDtoWithStateDto.setCreationDate(presentDate);
    given(statesRepository.findOne(FIRST_ID)).willReturn(firstStateEntity);
    given(converterService.convert(firstStateHistoryDtoWithStateDto, StatesHistoryEntity.class))
        .willReturn(firstStatesHistoryEntity);

    given(statesHistoryRepository.saveAndFlush(firstStatesHistoryEntity))
        .willReturn(firstStatesHistoryEntity);
    // When
    Long result = statesHistoryService.saveStateHistory(firstStateHistoryDtoWithStateDto);

    // Then
    assertThat(result, notNullValue());
    assertThat(result, equalTo(FIRST_ID));
    assertThat(firstStatesHistoryEntity.getCreationDate(), equalTo(presentDate));

    then(statesRepository).should().findOne(FIRST_ID);
    then(converterService).should()
        .convert(firstStateHistoryDtoWithStateDto, StatesHistoryEntity.class);
    then(statesHistoryRepository).should().saveAndFlush(firstStatesHistoryEntity);
    then(applicationsService).should()
        .saveOrUpdate(firstStateHistoryDtoWithStateDto.getApplicationDTO());
  }

  private Date currentDateMinusSeconds(Long seconds) {
    return Date.from(ZonedDateTime.now().minusSeconds(seconds).toInstant());
  }
}