package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.entities.StateEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.StatesRepository;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateViewDTO;
import org.junit.Before;
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
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * Created by Gabor_Ivanyi-Nagy on 7/11/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class StatesServiceImplTest {

    private static final Long FIRST_ID = 1L;
    private static final Long SECOND_ID = 2L;
    private static final Long THIRD_ID = 3L;
    private static final Long FIVE_SECONDS = 5L;
    private static final Type STATE_VIEW_DTO_LIST_TYPE = new TypeToken<List<StateViewDTO>>() {}.getType();

    private static final String FIRST_POSITION_ENTITY_NAME = "First position";
    private static final String SECOND_POSITION_ENTITY_NAME = "Second position";
    private static final String THIRD_POSITION_ENTITY_NAME = "Third position";

    private static final String FIRST_STATE_TYPE_NAME = "First state";
    private static final String SECOND_STATE_TYPE_NAME = "Second state";
    private static final String THIRD_STATE_TYPE_NAME = "Third state";

    private static final String DATE_FORMAT_CONSTANT = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_CONSTANT);

    private static final ApplicationEntity APPLICATION_ENTITY_WITHOUT_STATES = ApplicationEntity.builder().id(FIRST_ID).build();
    private static final CandidateEntity CANDIDATE_ENTITY_WITHOUT_APPLICATIONS = CandidateEntity.builder().id(FIRST_ID).build();
    private static final List<ApplicationEntity> EMPTY_APPLICATION_ENTITY_LIST = Collections.emptyList();
    private static final List<StateEntity> EMPTY_STATE_ENTITY_LIST = Collections.emptyList();
    private static final List<StateViewDTO> EMPTY_STATE_VIEW_DTO_LIST = Collections.emptyList();

    private Date presentDate;
    private CandidateEntity firstCandidateEntity;
    private PositionEntity firstPositionEntity;
    private PositionEntity secondPositionEntity;
    private PositionEntity thirdPositionEntity;

    private ApplicationEntity firstApplicationEntity;
    private ApplicationEntity secondApplicationEntity;
    private ApplicationEntity thirdApplicationEntity;

    private StateEntity firstStateEntity;
    private StateEntity secondStateEntity;
    private StateEntity thirdStateEntity;
    private StateViewDTO stateViewDTO;
    private StateDTO stateDTO;

    private List<ApplicationEntity> applicationEntityListWithSingleElement;
    private List<ApplicationEntity> applicationEntityListWithThreeElements;
    private List<StateEntity> stateEntityListWithSingleElement;
    private List<StateEntity> stateEntityListWithThreeElements;
    private List<StateViewDTO> stateViewDTOListWithSingleElement;
    private List<StateViewDTO> stateViewDTOListWithThreeElements;


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


    @Before
    public void setUp() {
        presentDate = currentDateMinusSeconds(FIVE_SECONDS);
        firstCandidateEntity = CandidateEntity.builder().id(FIRST_ID).build();

        firstPositionEntity = PositionEntity.builder().id(FIRST_ID).name(FIRST_POSITION_ENTITY_NAME).build();
        secondPositionEntity = PositionEntity.builder().id(SECOND_ID).name(SECOND_POSITION_ENTITY_NAME).build();
        thirdPositionEntity = PositionEntity.builder().id(THIRD_ID).name(THIRD_POSITION_ENTITY_NAME).build();

        firstApplicationEntity = ApplicationEntity.builder().id(FIRST_ID).candidateEntity(firstCandidateEntity).positionEntity(firstPositionEntity).creationDate(presentDate).build();
        secondApplicationEntity = ApplicationEntity.builder().id(SECOND_ID).candidateEntity(firstCandidateEntity).positionEntity(secondPositionEntity).creationDate(presentDate).build();
        thirdApplicationEntity = ApplicationEntity.builder().id(THIRD_ID).candidateEntity(firstCandidateEntity).positionEntity(thirdPositionEntity).creationDate(presentDate).build();

        firstStateEntity = StateEntity.builder().id(FIRST_ID).applicationEntity(firstApplicationEntity).stateType(FIRST_STATE_TYPE_NAME).creationDate(presentDate).build();
        secondStateEntity = StateEntity.builder().id(SECOND_ID).applicationEntity(secondApplicationEntity).stateType(SECOND_STATE_TYPE_NAME).creationDate(presentDate).build();
        thirdStateEntity = StateEntity.builder().id(THIRD_ID).applicationEntity(thirdApplicationEntity).stateType(THIRD_STATE_TYPE_NAME).creationDate(presentDate).build();

        stateViewDTO = StateViewDTO.builder().id(FIRST_ID).build();
        stateDTO = StateDTO.builder().id(FIRST_ID).build();

        applicationEntityListWithSingleElement = Arrays.asList(firstApplicationEntity);
        applicationEntityListWithThreeElements = Arrays.asList(firstApplicationEntity, secondApplicationEntity, thirdApplicationEntity);

        stateEntityListWithSingleElement = Arrays.asList(firstStateEntity);
        stateEntityListWithThreeElements = Arrays.asList(firstStateEntity, secondStateEntity, thirdStateEntity);

        stateViewDTOListWithSingleElement = Arrays.asList(stateViewDTO);
        stateViewDTOListWithThreeElements = Arrays.asList(stateViewDTO, stateViewDTO, stateViewDTO);
    }

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
        given(statesRepository.findByApplicationEntityOrderByStateIndexDesc(APPLICATION_ENTITY_WITHOUT_STATES)).willReturn(EMPTY_STATE_ENTITY_LIST);
        given(modelMapper.map(EMPTY_STATE_ENTITY_LIST, STATE_VIEW_DTO_LIST_TYPE)).willReturn(EMPTY_STATE_VIEW_DTO_LIST);

        // When
        List<StateViewDTO> stateViewDTOList = statesService.getStatesByApplicationId(FIRST_ID);

        // Then
        assertThat(stateViewDTOList, notNullValue());
        assertThat(stateViewDTOList.isEmpty(), is(true));

        then(applicationsRepository).should().findOne(FIRST_ID);
        then(statesRepository).should().findByApplicationEntityOrderByStateIndexDesc(APPLICATION_ENTITY_WITHOUT_STATES);
    }

    @Test
    public void getStatesByApplicationIdShouldReturnAListWithSingleElement() {
        // Given
        given(applicationsRepository.findOne(FIRST_ID)).willReturn(firstApplicationEntity);
        given(statesRepository.findByApplicationEntityOrderByStateIndexDesc(firstApplicationEntity)).willReturn(stateEntityListWithSingleElement);
        given(modelMapper.map(stateEntityListWithSingleElement, STATE_VIEW_DTO_LIST_TYPE)).willReturn(stateViewDTOListWithSingleElement);

        // When
        List<StateViewDTO> stateViewDTOList = statesService.getStatesByApplicationId(FIRST_ID);

        // Then
        assertThat(stateViewDTOList, notNullValue());
        assertThat(stateViewDTOList.isEmpty(), is(false));
        assertThat(stateViewDTOList, equalTo(stateViewDTOListWithSingleElement));
        assertStateViewDtoListByCreationDateAsString(stateViewDTOList);

        then(applicationsRepository).should().findOne(FIRST_ID);
        then(statesRepository).should().findByApplicationEntityOrderByStateIndexDesc(firstApplicationEntity);
    }

    @Test
    public void getStatesByApplicationIdShouldReturnAListWithThreeElement() {
        // Given
        given(applicationsRepository.findOne(FIRST_ID)).willReturn(firstApplicationEntity);
        given(statesRepository.findByApplicationEntityOrderByStateIndexDesc(firstApplicationEntity)).willReturn(stateEntityListWithThreeElements);
        given(modelMapper.map(stateEntityListWithThreeElements, STATE_VIEW_DTO_LIST_TYPE)).willReturn(stateViewDTOListWithThreeElements);

        // When
        List<StateViewDTO> stateViewDTOList = statesService.getStatesByApplicationId(FIRST_ID);

        // Then
        assertThat(stateViewDTOList, notNullValue());
        assertThat(stateViewDTOList.isEmpty(), is(false));
        assertThat(stateViewDTOList, equalTo(stateViewDTOListWithThreeElements));
        assertStateViewDtoListByCreationDateAsString(stateViewDTOList);

        then(applicationsRepository).should().findOne(FIRST_ID);
        then(statesRepository).should().findByApplicationEntityOrderByStateIndexDesc(firstApplicationEntity);
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
        given(modelMapper.map(stateDTO, StateEntity.class)).willReturn(firstStateEntity);
        given(applicationsRepository.findOne(SECOND_ID)).willReturn(secondApplicationEntity);
        given(statesRepository.save(firstStateEntity)).willReturn(firstStateEntity);

        // When
        Long resultId = statesService.saveState(stateDTO, SECOND_ID);

        // Then
        assertStateEntityWhenSavingStateEntity(firstStateEntity, getExpectedSavedStateEntity());
        assertThat(resultId, notNullValue());
        assertThat(resultId, equalTo(FIRST_ID));

        then(applicationsRepository).should().findOne(SECOND_ID);
        then(statesRepository).should().save(firstStateEntity);
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
        given(applicationsRepository.findByCandidateEntity(CANDIDATE_ENTITY_WITHOUT_APPLICATIONS)).willReturn(EMPTY_APPLICATION_ENTITY_LIST);

        // When
        Collection<CandidateApplicationDTO> candidateApplicationDTOCollection = statesService.getCandidateApplicationsByCandidateId(FIRST_ID);

        // Then
        assertThat(candidateApplicationDTOCollection, notNullValue());
        assertThat(candidateApplicationDTOCollection.isEmpty(), is(true));

        then(candidateRepository).should().findOne(FIRST_ID);
        then(applicationsRepository).should().findByCandidateEntity(CANDIDATE_ENTITY_WITHOUT_APPLICATIONS);
    }

    @Test
    public void getCandidateApplicationsByCandidateIdShouldReturnAListWithSingleElement() {
        // Given
        given(candidateRepository.findOne(FIRST_ID)).willReturn(firstCandidateEntity);
        given(applicationsRepository.findByCandidateEntity(firstCandidateEntity)).willReturn(applicationEntityListWithSingleElement);
        given(statesRepository.findTopByApplicationEntityOrderByStateIndexDesc(firstApplicationEntity)).willReturn(firstStateEntity);

        // When
        Collection<CandidateApplicationDTO> candidateApplicationDTOCollection = statesService.getCandidateApplicationsByCandidateId(FIRST_ID);

        // Then
        assertCandidateApplicationDTOCollection(candidateApplicationDTOCollection, getExpectedCandidateApplicationDTOCollectionWithSingleElement());

        then(candidateRepository).should().findOne(FIRST_ID);
        then(applicationsRepository).should().findByCandidateEntity(firstCandidateEntity);
        then(statesRepository).should().findTopByApplicationEntityOrderByStateIndexDesc(firstApplicationEntity);
    }

    @Test
    public void getCandidateApplicationsByCandidateShouldReturnAListWithThreeElements() {
        // Given
        given(candidateRepository.findOne(FIRST_ID)).willReturn(firstCandidateEntity);
        given(applicationsRepository.findByCandidateEntity(firstCandidateEntity)).willReturn(applicationEntityListWithThreeElements);
        given(statesRepository.findTopByApplicationEntityOrderByStateIndexDesc(firstApplicationEntity)).willReturn(firstStateEntity);
        given(statesRepository.findTopByApplicationEntityOrderByStateIndexDesc(secondApplicationEntity)).willReturn(secondStateEntity);
        given(statesRepository.findTopByApplicationEntityOrderByStateIndexDesc(thirdApplicationEntity)).willReturn(thirdStateEntity);

        // When
        Collection<CandidateApplicationDTO> candidateApplicationDTOCollection = statesService.getCandidateApplicationsByCandidateId(FIRST_ID);

        // Then
        assertCandidateApplicationDTOCollection(candidateApplicationDTOCollection, getExpectedCandidateApplicationDTOCollectionWithThreeElements());

        then(candidateRepository).should().findOne(FIRST_ID);
        then(applicationsRepository).should().findByCandidateEntity(firstCandidateEntity);
        then(statesRepository).should().findTopByApplicationEntityOrderByStateIndexDesc(firstApplicationEntity);
        then(statesRepository).should().findTopByApplicationEntityOrderByStateIndexDesc(secondApplicationEntity);
        then(statesRepository).should().findTopByApplicationEntityOrderByStateIndexDesc(thirdApplicationEntity);
    }

    private void assertStateViewDtoListByCreationDateAsString(List<StateViewDTO> stateViewDTOList) {
        for (StateViewDTO stateViewDTO : stateViewDTOList) {
            assertThat(stateViewDTO.getCreationDate(), equalTo(SIMPLE_DATE_FORMAT.format(presentDate)));
        }
    }

    private void assertCandidateApplicationDTOCollection(Collection<CandidateApplicationDTO> candidateApplicationDTOCollection, Collection<CandidateApplicationDTO> expectedCollection) {
        assertThat(candidateApplicationDTOCollection, notNullValue());
        assertThat(candidateApplicationDTOCollection.isEmpty(), is(false));
        assertThat(candidateApplicationDTOCollection, equalTo(expectedCollection));
    }

    private void assertStateEntityWhenSavingStateEntity(StateEntity stateEntity, StateEntity expectedSavedStateEntity) {
        assertThat(stateEntity, notNullValue());
        assertThat(stateEntity.getApplicationEntity(), equalTo(expectedSavedStateEntity.getApplicationEntity()));
        assertThat(stateEntity.getCreationDate(), greaterThanOrEqualTo(expectedSavedStateEntity.getCreationDate()));
    }

    private StateEntity getExpectedSavedStateEntity() {
        return StateEntity.builder().id(FIRST_ID).applicationEntity(secondApplicationEntity).stateType(FIRST_STATE_TYPE_NAME).creationDate(presentDate).build();
    }

    private Collection<CandidateApplicationDTO> getExpectedCandidateApplicationDTOCollectionWithSingleElement() {
        return Arrays.asList(
                CandidateApplicationDTO.builder().applicationId(firstApplicationEntity.getId())
                        .stateType(firstStateEntity.getStateType()).positionName(firstApplicationEntity.getPositionEntity().getName()).lastStateId(firstStateEntity.getId())
                        .creationDate(SIMPLE_DATE_FORMAT.format(firstApplicationEntity.getCreationDate())).modificationDate(SIMPLE_DATE_FORMAT.format(firstStateEntity.getCreationDate())).build()
        );
    }

    private Collection<CandidateApplicationDTO> getExpectedCandidateApplicationDTOCollectionWithThreeElements() {
        return Arrays.asList(
                CandidateApplicationDTO.builder().applicationId(firstApplicationEntity.getId())
                        .stateType(firstStateEntity.getStateType()).positionName(firstApplicationEntity.getPositionEntity().getName()).lastStateId(firstStateEntity.getId())
                        .creationDate(SIMPLE_DATE_FORMAT.format(firstApplicationEntity.getCreationDate())).modificationDate(SIMPLE_DATE_FORMAT.format(firstStateEntity.getCreationDate())).build(),

                CandidateApplicationDTO.builder().applicationId(secondApplicationEntity.getId())
                        .stateType(secondStateEntity.getStateType()).positionName(secondApplicationEntity.getPositionEntity().getName()).lastStateId(secondStateEntity.getId())
                        .creationDate(SIMPLE_DATE_FORMAT.format(secondApplicationEntity.getCreationDate())).modificationDate(SIMPLE_DATE_FORMAT.format(secondStateEntity.getCreationDate())).build(),

                CandidateApplicationDTO.builder().applicationId(thirdApplicationEntity.getId())
                        .stateType(thirdStateEntity.getStateType()).positionName(thirdApplicationEntity.getPositionEntity().getName()).lastStateId(thirdStateEntity.getId())
                        .creationDate(SIMPLE_DATE_FORMAT.format(thirdApplicationEntity.getCreationDate())).modificationDate(SIMPLE_DATE_FORMAT.format(thirdStateEntity.getCreationDate())).build()
        );
    }

    private Date currentDateMinusSeconds(Long seconds) {
        return Date.from(ZonedDateTime.now().minusSeconds(seconds).toInstant());
    }
}