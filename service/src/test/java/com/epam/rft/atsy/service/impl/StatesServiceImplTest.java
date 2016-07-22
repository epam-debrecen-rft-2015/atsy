package com.epam.rft.atsy.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.entities.StatesHistoryEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.StatesRepository;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateViewDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Gabor_Ivanyi-Nagy on 7/11/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class StatesServiceImplTest {

  private static final Long FIRST_ID = 1L;
  private static final Long SECOND_ID = 2L;
  private static final Long THIRD_ID = 3L;
  private static final Long FIVE_SECONDS = 5L;
  private static final Type STATE_VIEW_DTO_LIST_TYPE = new TypeToken<List<StateViewDTO>>() {
  }.getType();

  private static final String FIRST_POSITION_ENTITY_NAME = "First position";
  private static final String SECOND_POSITION_ENTITY_NAME = "Second position";
  private static final String THIRD_POSITION_ENTITY_NAME = "Third position";

  private static final String FIRST_STATE_TYPE_NAME = "First state";
  private static final String SECOND_STATE_TYPE_NAME = "Second state";
  private static final String THIRD_STATE_TYPE_NAME = "Third state";

  private static final String DATE_FORMAT_CONSTANT = "yyyy-MM-dd HH:mm:ss";
  private static final SimpleDateFormat
      SIMPLE_DATE_FORMAT =
      new SimpleDateFormat(DATE_FORMAT_CONSTANT);

  private static final ApplicationEntity
      APPLICATION_ENTITY_WITHOUT_STATES =
      ApplicationEntity.builder().id(FIRST_ID).build();
  private static final CandidateEntity
      CANDIDATE_ENTITY_WITHOUT_APPLICATIONS =
      CandidateEntity.builder().id(FIRST_ID).build();
  private static final List<ApplicationEntity>
      EMPTY_APPLICATION_ENTITY_LIST =
      Collections.emptyList();
  private static final List<StatesHistoryEntity> EMPTY_STATE_ENTITY_LIST = Collections.emptyList();
  private static final List<StateViewDTO> EMPTY_STATE_VIEW_DTO_LIST = Collections.emptyList();

  private final Date presentDate = currentDateMinusSeconds(FIVE_SECONDS);
  private final CandidateEntity
      firstCandidateEntity =
      CandidateEntity.builder().id(FIRST_ID).build();
  private final PositionEntity
      firstPositionEntity =
      PositionEntity.builder().id(FIRST_ID).name(FIRST_POSITION_ENTITY_NAME).build();
  private final PositionEntity
      secondPositionEntity =
      PositionEntity.builder().id(SECOND_ID).name(SECOND_POSITION_ENTITY_NAME).build();
  private final PositionEntity
      thirdPositionEntity =
      PositionEntity.builder().id(THIRD_ID).name(THIRD_POSITION_ENTITY_NAME).build();

  private final ApplicationEntity
      firstApplicationEntity =
      ApplicationEntity.builder().id(FIRST_ID).candidateEntity(firstCandidateEntity)
          .positionEntity(firstPositionEntity).creationDate(presentDate).build();
  ;
  private final ApplicationEntity
      secondApplicationEntity =
      ApplicationEntity.builder().id(SECOND_ID).candidateEntity(firstCandidateEntity)
          .positionEntity(secondPositionEntity).creationDate(presentDate).build();
  private final ApplicationEntity
      thirdApplicationEntity =
      ApplicationEntity.builder().id(THIRD_ID).candidateEntity(firstCandidateEntity)
          .positionEntity(thirdPositionEntity).creationDate(presentDate).build();

  private final StatesHistoryEntity
      firstStatesHistoryEntity =
      StatesHistoryEntity.builder().id(FIRST_ID).applicationEntity(firstApplicationEntity)
          .stateType(FIRST_STATE_TYPE_NAME).creationDate(presentDate).build();
  ;
  private final StatesHistoryEntity
      secondStatesHistoryEntity =
      StatesHistoryEntity.builder().id(SECOND_ID).applicationEntity(secondApplicationEntity)
          .stateType(SECOND_STATE_TYPE_NAME).creationDate(presentDate).build();
  private final StatesHistoryEntity
      thirdStatesHistoryEntity =
      StatesHistoryEntity.builder().id(THIRD_ID).applicationEntity(thirdApplicationEntity)
          .stateType(THIRD_STATE_TYPE_NAME).creationDate(presentDate).build();
  private final StatesHistoryEntity
      savedStatesHistoryEntity =
      StatesHistoryEntity.builder().id(FIRST_ID).applicationEntity(secondApplicationEntity)
          .stateType(FIRST_STATE_TYPE_NAME).creationDate(presentDate).build();
  private final StateViewDTO stateViewDTO = StateViewDTO.builder().id(FIRST_ID).build();
  private final StateDTO stateDTO = StateDTO.builder().id(FIRST_ID).build();

  private final List<ApplicationEntity>
      applicationEntityListWithSingleElement =
      Arrays.asList(firstApplicationEntity);
  ;
  private final List<ApplicationEntity>
      applicationEntityListWithThreeElements =
      Arrays.asList(firstApplicationEntity, secondApplicationEntity, thirdApplicationEntity);
  private final List<StatesHistoryEntity>
      statesHistoryEntityListWithSingleElement =
      Arrays.asList(firstStatesHistoryEntity);
  private final List<StatesHistoryEntity>
      statesHistoryEntityListWithThreeElements =
      Arrays.asList(firstStatesHistoryEntity, secondStatesHistoryEntity, thirdStatesHistoryEntity);
  private final List<StateViewDTO> stateViewDTOListWithSingleElement = Arrays.asList(stateViewDTO);
  private final List<StateViewDTO>
      stateViewDTOListWithThreeElements =
      Arrays.asList(stateViewDTO, stateViewDTO, stateViewDTO);


  private List<CandidateApplicationDTO>
      candidateApplicationDTOListWithSingleElement =
      Arrays.asList(
          CandidateApplicationDTO.builder().applicationId(FIRST_ID).stateType(FIRST_STATE_TYPE_NAME)
              .positionName(FIRST_POSITION_ENTITY_NAME).lastStateId(FIRST_ID)
              .creationDate(SIMPLE_DATE_FORMAT.format(presentDate))
              .modificationDate(SIMPLE_DATE_FORMAT.format(presentDate)).build()
      );

  private List<CandidateApplicationDTO>
      candidateApplicationDTOListWithThreeElements =
      Arrays.asList(
          CandidateApplicationDTO.builder().applicationId(FIRST_ID).stateType(FIRST_STATE_TYPE_NAME)
              .positionName(FIRST_POSITION_ENTITY_NAME).lastStateId(FIRST_ID)
              .creationDate(SIMPLE_DATE_FORMAT.format(presentDate))
              .modificationDate(SIMPLE_DATE_FORMAT.format(presentDate)).build(),

          CandidateApplicationDTO.builder().applicationId(SECOND_ID)
              .stateType(SECOND_STATE_TYPE_NAME).positionName(SECOND_POSITION_ENTITY_NAME)
              .lastStateId(SECOND_ID)
              .creationDate(SIMPLE_DATE_FORMAT.format(presentDate))
              .modificationDate(SIMPLE_DATE_FORMAT.format(presentDate)).build(),

          CandidateApplicationDTO.builder().applicationId(THIRD_ID).stateType(THIRD_STATE_TYPE_NAME)
              .positionName(THIRD_POSITION_ENTITY_NAME).lastStateId(THIRD_ID)
              .creationDate(SIMPLE_DATE_FORMAT.format(presentDate))
              .modificationDate(SIMPLE_DATE_FORMAT.format(presentDate)).build()
      );

  @Mock
  private ModelMapper modelMapper;

  @Mock
  private StatesRepository statesRepository;
  @Mock
  private ApplicationsRepository applicationsRepository;
  @Mock
  private CandidateRepository candidateRepository;

  @InjectMocks
  private StatesServiceImpl statesService;


  @Test(expected = IllegalArgumentException.class)
  public void getStatesByApplicationIdShouldThrowIllegalArgumentExceptionWhenApplicationIdIsNull() {
    // Given

    // When
    statesService.getStatesByApplicationId(null);

    // Then
  }


  @Test(expected = IllegalArgumentException.class)
  public void getStatesByApplicationIdShouldThrowIllegalArgumentExceptionWhenApplicationIsNull() {
    // Given
    given(applicationsRepository.findOne(FIRST_ID)).willReturn(null);

    // When
    statesService.getStatesByApplicationId(FIRST_ID);

    // Then
  }

  @Test
  public void getStatesByApplicationIdShouldReturnAnEmptyList() {
    // Given
    given(applicationsRepository.findOne(FIRST_ID)).willReturn(APPLICATION_ENTITY_WITHOUT_STATES);
    given(statesRepository
        .findByApplicationEntityOrderByStateIndexDesc(APPLICATION_ENTITY_WITHOUT_STATES))
        .willReturn(EMPTY_STATE_ENTITY_LIST);
    given(modelMapper.map(EMPTY_STATE_ENTITY_LIST, STATE_VIEW_DTO_LIST_TYPE))
        .willReturn(EMPTY_STATE_VIEW_DTO_LIST);

    // When
    List<StateViewDTO> stateViewDTOList = statesService.getStatesByApplicationId(FIRST_ID);

    // Then
    assertThat(stateViewDTOList, notNullValue());
    assertThat(stateViewDTOList.isEmpty(), is(true));

    then(applicationsRepository).should().findOne(FIRST_ID);
    then(statesRepository).should()
        .findByApplicationEntityOrderByStateIndexDesc(APPLICATION_ENTITY_WITHOUT_STATES);
  }

  @Test
  public void getStatesByApplicationIdShouldReturnAListWithSingleElement() {
    // Given
    given(applicationsRepository.findOne(FIRST_ID)).willReturn(firstApplicationEntity);
    given(statesRepository.findByApplicationEntityOrderByStateIndexDesc(firstApplicationEntity))
        .willReturn(statesHistoryEntityListWithSingleElement);
    given(modelMapper.map(statesHistoryEntityListWithSingleElement, STATE_VIEW_DTO_LIST_TYPE))
        .willReturn(stateViewDTOListWithSingleElement);

    // When
    List<StateViewDTO> stateViewDTOList = statesService.getStatesByApplicationId(FIRST_ID);

    // Then
    assertThat(stateViewDTOList, notNullValue());
    assertThat(stateViewDTOList.isEmpty(), is(false));
    assertThat(stateViewDTOList, equalTo(stateViewDTOListWithSingleElement));
    assertStateViewDtoListByCreationDateAsString(stateViewDTOList);

    then(applicationsRepository).should().findOne(FIRST_ID);
    then(statesRepository).should()
        .findByApplicationEntityOrderByStateIndexDesc(firstApplicationEntity);
  }

  @Test
  public void getStatesByApplicationIdShouldReturnAListWithThreeElement() {
    // Given
    given(applicationsRepository.findOne(FIRST_ID)).willReturn(firstApplicationEntity);
    given(statesRepository.findByApplicationEntityOrderByStateIndexDesc(firstApplicationEntity))
        .willReturn(statesHistoryEntityListWithThreeElements);
    given(modelMapper.map(statesHistoryEntityListWithThreeElements, STATE_VIEW_DTO_LIST_TYPE))
        .willReturn(stateViewDTOListWithThreeElements);

    // When
    List<StateViewDTO> stateViewDTOList = statesService.getStatesByApplicationId(FIRST_ID);

    // Then
    assertThat(stateViewDTOList, notNullValue());
    assertThat(stateViewDTOList.isEmpty(), is(false));
    assertThat(stateViewDTOList, equalTo(stateViewDTOListWithThreeElements));
    assertStateViewDtoListByCreationDateAsString(stateViewDTOList);

    then(applicationsRepository).should().findOne(FIRST_ID);
    then(statesRepository).should()
        .findByApplicationEntityOrderByStateIndexDesc(firstApplicationEntity);
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveStateShouldThrowIllegalArgumentExceptionWhenApplicationIdIsNull() {
    // Given

    // When
    statesService.saveState(stateDTO, null);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveStateShouldThrowIllegalArgumentExceptionWhenStateIsNull() {
    // Given

    // When
    statesService.saveState(null, FIRST_ID);

    // Then
  }

  @Test
  public void saveStateShouldBeSuccessSaving() {
    // Given
    given(modelMapper.map(stateDTO, StatesHistoryEntity.class)).willReturn(firstStatesHistoryEntity);
    given(applicationsRepository.findOne(SECOND_ID)).willReturn(secondApplicationEntity);
    given(statesRepository.save(firstStatesHistoryEntity)).willReturn(firstStatesHistoryEntity);

    // When
    Long resultId = statesService.saveState(stateDTO, SECOND_ID);

    // Then
    assertStateEntityWhenSavingStateEntity(firstStatesHistoryEntity, savedStatesHistoryEntity);
    assertThat(resultId, notNullValue());
    assertThat(resultId, equalTo(FIRST_ID));

    then(applicationsRepository).should().findOne(SECOND_ID);
    then(statesRepository).should().save(firstStatesHistoryEntity);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCandidateApplicationsByCandidateIdShouldThrowIllegalArgumentExceptionWhenCandidateIdIsNull() {
    // Given

    // When
    statesService.getCandidateApplicationsByCandidateId(null);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCandidateApplicationsByCandidateIdShouldThrowIllegalArgumentExceptionWhenCandidateEntityIsNull() {
    // Given
    given(candidateRepository.findOne(FIRST_ID)).willReturn(null);

    // When
    statesService.getCandidateApplicationsByCandidateId(FIRST_ID);

    // Then
  }

  @Test
  public void getCandidateApplicationsByCandidateIdShouldReturnAnEmptyList() {
    // Given
    given(candidateRepository.findOne(FIRST_ID)).willReturn(CANDIDATE_ENTITY_WITHOUT_APPLICATIONS);
    given(applicationsRepository.findByCandidateEntity(CANDIDATE_ENTITY_WITHOUT_APPLICATIONS))
        .willReturn(EMPTY_APPLICATION_ENTITY_LIST);

    // When
    Collection<CandidateApplicationDTO>
        candidateApplicationDTOCollection =
        statesService.getCandidateApplicationsByCandidateId(FIRST_ID);

    // Then
    assertThat(candidateApplicationDTOCollection, notNullValue());
    assertThat(candidateApplicationDTOCollection.isEmpty(), is(true));

    then(candidateRepository).should().findOne(FIRST_ID);
    then(applicationsRepository).should()
        .findByCandidateEntity(CANDIDATE_ENTITY_WITHOUT_APPLICATIONS);
  }

  @Test
  public void getCandidateApplicationsByCandidateIdShouldReturnAListWithSingleElement() {
    // Given
    given(candidateRepository.findOne(FIRST_ID)).willReturn(firstCandidateEntity);
    given(applicationsRepository.findByCandidateEntity(firstCandidateEntity))
        .willReturn(applicationEntityListWithSingleElement);
    given(statesRepository.findTopByApplicationEntityOrderByStateIndexDesc(firstApplicationEntity))
        .willReturn(firstStatesHistoryEntity);

    // When
    Collection<CandidateApplicationDTO>
        candidateApplicationDTOCollection =
        statesService.getCandidateApplicationsByCandidateId(FIRST_ID);

    // Then
    assertCandidateApplicationDTOCollection(candidateApplicationDTOCollection,
        candidateApplicationDTOListWithSingleElement);

    then(candidateRepository).should().findOne(FIRST_ID);
    then(applicationsRepository).should().findByCandidateEntity(firstCandidateEntity);
    then(statesRepository).should()
        .findTopByApplicationEntityOrderByStateIndexDesc(firstApplicationEntity);
  }

  @Test
  public void getCandidateApplicationsByCandidateShouldReturnAListWithThreeElements() {
    // Given
    given(candidateRepository.findOne(FIRST_ID)).willReturn(firstCandidateEntity);
    given(applicationsRepository.findByCandidateEntity(firstCandidateEntity))
        .willReturn(applicationEntityListWithThreeElements);
    given(statesRepository.findTopByApplicationEntityOrderByStateIndexDesc(firstApplicationEntity))
        .willReturn(firstStatesHistoryEntity);
    given(statesRepository.findTopByApplicationEntityOrderByStateIndexDesc(secondApplicationEntity))
        .willReturn(secondStatesHistoryEntity);
    given(statesRepository.findTopByApplicationEntityOrderByStateIndexDesc(thirdApplicationEntity))
        .willReturn(thirdStatesHistoryEntity);

    // When
    Collection<CandidateApplicationDTO>
        candidateApplicationDTOCollection =
        statesService.getCandidateApplicationsByCandidateId(FIRST_ID);

    // Then
    assertCandidateApplicationDTOCollection(candidateApplicationDTOCollection,
        candidateApplicationDTOListWithThreeElements);

    then(candidateRepository).should().findOne(FIRST_ID);
    then(applicationsRepository).should().findByCandidateEntity(firstCandidateEntity);
    then(statesRepository).should()
        .findTopByApplicationEntityOrderByStateIndexDesc(firstApplicationEntity);
    then(statesRepository).should()
        .findTopByApplicationEntityOrderByStateIndexDesc(secondApplicationEntity);
    then(statesRepository).should()
        .findTopByApplicationEntityOrderByStateIndexDesc(thirdApplicationEntity);
  }

  private void assertStateViewDtoListByCreationDateAsString(List<StateViewDTO> stateViewDTOList) {
    for (StateViewDTO stateViewDTO : stateViewDTOList) {
      assertThat(stateViewDTO.getCreationDate(), equalTo(SIMPLE_DATE_FORMAT.format(presentDate)));
    }
  }

  private void assertCandidateApplicationDTOCollection(
      Collection<CandidateApplicationDTO> candidateApplicationDTOCollection,
      Collection<CandidateApplicationDTO> expectedCollection) {
    assertThat(candidateApplicationDTOCollection, notNullValue());
    assertThat(candidateApplicationDTOCollection.isEmpty(), is(false));
    assertThat(candidateApplicationDTOCollection, equalTo(expectedCollection));
  }

  private void assertStateEntityWhenSavingStateEntity(StatesHistoryEntity statesHistoryEntity,
                                                      StatesHistoryEntity expectedSavedStatesHistoryEntity) {
    assertThat(statesHistoryEntity, notNullValue());
    assertThat(statesHistoryEntity.getApplicationEntity(),
        equalTo(expectedSavedStatesHistoryEntity.getApplicationEntity()));
    assertThat(statesHistoryEntity.getCreationDate(),
        greaterThanOrEqualTo(expectedSavedStatesHistoryEntity.getCreationDate()));
  }

  private Date currentDateMinusSeconds(Long seconds) {
    return Date.from(ZonedDateTime.now().minusSeconds(seconds).toInstant());
  }
}