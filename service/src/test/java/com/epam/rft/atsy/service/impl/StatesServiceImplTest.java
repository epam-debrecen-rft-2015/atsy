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
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.*;

/**
 * Created by Gabor_Ivanyi-Nagy on 7/11/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class StatesServiceImplTest {

    private static final Long FIRST_ID = 1L;
    private static final Integer AT_ONE_TIME = 1;
    private static final Integer THREE_TIMES = 3;
    private static final Type STATE_VIEW_DTO_LIST_TYPE = new TypeToken<List<StateViewDTO>>() {}.getType();

    private static final String POSITION_ENTITY_NAME = "Position";
    private static final String STATE_TYPE_NAME = "State type";
    private static final String DATE_FORMAT_CONSTANT = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_CONSTANT);

    private static final ApplicationEntity APPLICATION_ENTITY_WITHOUT_STATES = ApplicationEntity.builder().id(FIRST_ID).build();
    private static final CandidateEntity CANDIDATE_ENTITY_WITHOUT_APPLICATIONS = CandidateEntity.builder().id(FIRST_ID).build();
    private static final List<ApplicationEntity> EMPTY_APPLICATION_ENTITY_LIST = Collections.emptyList();
    private static final List<StateEntity> EMPTY_STATE_ENTITY_LIST = Collections.emptyList();
    private static final List<StateViewDTO> EMPTY_STATE_VIEW_DTO_LIST = Collections.emptyList();


    private Date now;
    private ApplicationEntity applicationEntity;
    private CandidateEntity candidateEntity;
    private PositionEntity positionEntity;

    private StateEntity stateEntity;
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
        now = new Date();
        candidateEntity = CandidateEntity.builder().id(FIRST_ID).build();
        positionEntity = PositionEntity.builder().id(FIRST_ID).name(POSITION_ENTITY_NAME).build();
        applicationEntity = ApplicationEntity.builder().id(FIRST_ID).candidateEntity(candidateEntity).positionEntity(positionEntity).creationDate(now).build();

        stateEntity = StateEntity.builder().id(FIRST_ID).applicationEntity(applicationEntity).stateType(STATE_TYPE_NAME).creationDate(now).build();
        stateViewDTO = StateViewDTO.builder().id(FIRST_ID).build();
        stateDTO = StateDTO.builder().id(FIRST_ID).build();

        applicationEntityListWithSingleElement = Arrays.asList(applicationEntity);
        applicationEntityListWithThreeElements = Arrays.asList(applicationEntity, applicationEntity, applicationEntity);

        stateEntityListWithSingleElement = Arrays.asList(stateEntity);
        stateEntityListWithThreeElements = Arrays.asList(stateEntity, stateEntity, stateEntity);

        stateViewDTOListWithSingleElement = Arrays.asList(stateViewDTO);
        stateViewDTOListWithThreeElements = Arrays.asList(stateViewDTO, stateViewDTO, stateViewDTO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStatesByApplicationIdShouldThrowIllegalArgumentExceptionWhenApplicationIdIsNull() {
        // Given
        given(applicationsRepository.findOne(null)).willThrow(IllegalArgumentException.class);

        // When
        statesService.getStatesByApplicationId(null);

        // Then
    }


    @Test(expected = IllegalArgumentException.class)
    public void getStatesByApplicationIdShouldThrowIllegalArgumentExceptionWhenApplicationIsNull() {
        // Given
        given(applicationsRepository.findOne(FIRST_ID)).willReturn(null);
        given(statesRepository.findByApplicationEntityOrderByStateIndexDesc(null)).willThrow(IllegalArgumentException.class);

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

        then(applicationsRepository).should(times(AT_ONE_TIME)).findOne(FIRST_ID);
        then(statesRepository).should(times(AT_ONE_TIME)).findByApplicationEntityOrderByStateIndexDesc(APPLICATION_ENTITY_WITHOUT_STATES);
    }

    @Test
    public void getStatesByApplicationIdShouldReturnAListWithSingleElement() {
        // Given
        given(applicationsRepository.findOne(FIRST_ID)).willReturn(applicationEntity);
        given(statesRepository.findByApplicationEntityOrderByStateIndexDesc(applicationEntity)).willReturn(stateEntityListWithSingleElement);
        given(modelMapper.map(stateEntityListWithSingleElement, STATE_VIEW_DTO_LIST_TYPE)).willReturn(stateViewDTOListWithSingleElement);

        // When
        List<StateViewDTO> stateViewDTOList = statesService.getStatesByApplicationId(FIRST_ID);

        // Then
        assertThat(stateViewDTOList, notNullValue());
        assertThat(stateViewDTOList.isEmpty(), is(false));
        assertThat(stateViewDTOList, equalTo(stateViewDTOListWithSingleElement));
        assertStateViewDtoListByCreationDateAsString(stateViewDTOList);

        then(applicationsRepository).should(times(AT_ONE_TIME)).findOne(FIRST_ID);
        then(statesRepository).should(times(AT_ONE_TIME)).findByApplicationEntityOrderByStateIndexDesc(applicationEntity);
    }

    @Test
    public void getStatesByApplicationIdShouldReturnAListWithThreeElement() {
        // Given
        given(applicationsRepository.findOne(FIRST_ID)).willReturn(applicationEntity);
        given(statesRepository.findByApplicationEntityOrderByStateIndexDesc(applicationEntity)).willReturn(stateEntityListWithThreeElements);
        given(modelMapper.map(stateEntityListWithThreeElements, STATE_VIEW_DTO_LIST_TYPE)).willReturn(stateViewDTOListWithThreeElements);

        // When
        List<StateViewDTO> stateViewDTOList = statesService.getStatesByApplicationId(FIRST_ID);

        // Then
        assertThat(stateViewDTOList, notNullValue());
        assertThat(stateViewDTOList.isEmpty(), is(false));
        assertThat(stateViewDTOList, equalTo(stateViewDTOListWithThreeElements));
        assertStateViewDtoListByCreationDateAsString(stateViewDTOList);

        then(applicationsRepository).should(times(AT_ONE_TIME)).findOne(FIRST_ID);
        then(statesRepository).should(times(AT_ONE_TIME)).findByApplicationEntityOrderByStateIndexDesc(applicationEntity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveStateShouldThrowIllegalArgumentExceptionWhenApplicationIdIsNull() {
        // Given
        given(applicationsRepository.findOne(null)).willThrow(IllegalArgumentException.class);

        // When
        statesService.saveState(stateDTO, null);

        // Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveStateShouldThrowIllegalArgumentExceptionWhenStateIsNull() {
        // Given
        given(statesRepository.save(stateEntity)).willThrow(IllegalArgumentException.class);

        // When
        statesService.saveState(null, FIRST_ID);

        // Then
    }

    @Test
    public void saveStateShouldBeSuccessSaving() {
        // Given
        given(modelMapper.map(stateDTO, StateEntity.class)).willReturn(stateEntity);
        given(applicationsRepository.findOne(FIRST_ID)).willReturn(applicationEntity);
        given(statesRepository.save(stateEntity)).willReturn(stateEntity);

        // When
        Long expectedId = statesService.saveState(stateDTO, FIRST_ID);

        // Then
        assertThat(stateEntity, notNullValue());
        assertThat(stateEntity.getCreationDate(), greaterThanOrEqualTo(now));
        assertThat(stateEntity.getApplicationEntity(), equalTo(applicationEntity));
        assertThat(stateEntity.getId(), equalTo(expectedId));

        then(applicationsRepository).should((times(AT_ONE_TIME))).findOne(FIRST_ID);
        then(statesRepository).should(times(AT_ONE_TIME)).save(stateEntity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCandidateApplicationsByCandidateIdShouldThrowIllegalArgumentExceptionWhenCandidateIdIsNull() {
        // Given
        given(candidateRepository.findOne(null)).willThrow(IllegalArgumentException.class);

        // When
        statesService.getCandidateApplicationsByCandidateId(null);

        // Then
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCandidateApplicationsByCandidateIdShouldThrowIllegalArgumentExceptionWhenCandidateEntityIsNull() {
        // Given
        given(candidateRepository.findOne(FIRST_ID)).willReturn(null);
        given(applicationsRepository.findByCandidateEntity(null)).willThrow(IllegalArgumentException.class);

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

        then(candidateRepository).should(times(AT_ONE_TIME)).findOne(FIRST_ID);
        then(applicationsRepository).should(times(AT_ONE_TIME)).findByCandidateEntity(CANDIDATE_ENTITY_WITHOUT_APPLICATIONS);
    }

    @Test
    public void getCandidateApplicationsByCandidateIdShouldReturnAListWithSingleElement() {
        // Given
        given(candidateRepository.findOne(FIRST_ID)).willReturn(candidateEntity);
        given(applicationsRepository.findByCandidateEntity(candidateEntity)).willReturn(applicationEntityListWithSingleElement);
        given(statesRepository.findTopByApplicationEntityOrderByStateIndexDesc(applicationEntity)).willReturn(stateEntity);

        // When
        Collection<CandidateApplicationDTO> candidateApplicationDTOCollection = statesService.getCandidateApplicationsByCandidateId(FIRST_ID);

        // Then
        assertCandidateApplicationDTOCollection(candidateApplicationDTOCollection, getExpectedCandidateApplicationDTOListFromApplicationEntityListAndStateEntity(applicationEntityListWithSingleElement, stateEntity));

        then(candidateRepository).should(times(AT_ONE_TIME)).findOne(FIRST_ID);
        then(applicationsRepository).should(times(AT_ONE_TIME)).findByCandidateEntity(candidateEntity);
        then(statesRepository).should(times(AT_ONE_TIME)).findTopByApplicationEntityOrderByStateIndexDesc(applicationEntity);
    }

    @Test
    public void getCandidateApplicationsByCandidateShouldReturnAListWithThreeElements() {
        // Given
        given(candidateRepository.findOne(FIRST_ID)).willReturn(candidateEntity);
        given(applicationsRepository.findByCandidateEntity(candidateEntity)).willReturn(applicationEntityListWithThreeElements);
        given(statesRepository.findTopByApplicationEntityOrderByStateIndexDesc(applicationEntity)).willReturn(stateEntity);

        // When
        Collection<CandidateApplicationDTO> candidateApplicationDTOCollection = statesService.getCandidateApplicationsByCandidateId(FIRST_ID);

        // Then
        assertCandidateApplicationDTOCollection(candidateApplicationDTOCollection, getExpectedCandidateApplicationDTOListFromApplicationEntityListAndStateEntity(applicationEntityListWithThreeElements, stateEntity));


        then(candidateRepository).should(times(AT_ONE_TIME)).findOne(FIRST_ID);
        then(applicationsRepository).should(times(AT_ONE_TIME)).findByCandidateEntity(candidateEntity);
        then(statesRepository).should(times(THREE_TIMES)).findTopByApplicationEntityOrderByStateIndexDesc(applicationEntity);
    }

    private void assertStateViewDtoListByCreationDateAsString(List<StateViewDTO> stateViewDTOList) {
        for (StateViewDTO stateViewDTO : stateViewDTOList) {
            assertThat(stateViewDTO.getCreationDate(), equalTo(SIMPLE_DATE_FORMAT.format(now)));
        }
    }

    private void assertCandidateApplicationDTOCollection(Collection<CandidateApplicationDTO> candidateApplicationDTOCollection, Collection<CandidateApplicationDTO> expectedCollection) {
        assertThat(candidateApplicationDTOCollection, notNullValue());
        assertThat(candidateApplicationDTOCollection.isEmpty(), is(false));
        assertThat(candidateApplicationDTOCollection, equalTo(expectedCollection));
    }

    private List<CandidateApplicationDTO> getExpectedCandidateApplicationDTOListFromApplicationEntityListAndStateEntity(List<ApplicationEntity> applicationEntityList, StateEntity stateEntity) {
        List<CandidateApplicationDTO> candidateApplicationDTOList = new LinkedList<>();

        for (ApplicationEntity applicationEntity : applicationEntityList) {
            CandidateApplicationDTO candidateApplicationDTO = CandidateApplicationDTO.builder()
                    .applicationId(applicationEntity.getId())
                    .stateType(stateEntity.getStateType())
                    .positionName(applicationEntity.getPositionEntity().getName())
                    .lastStateId(stateEntity.getId())
                    .creationDate(SIMPLE_DATE_FORMAT.format(applicationEntity.getCreationDate()))
                    .modificationDate(SIMPLE_DATE_FORMAT.format(stateEntity.getCreationDate()))
                    .build();
            candidateApplicationDTOList.add(candidateApplicationDTO);
        }
        return candidateApplicationDTOList;
    }
}