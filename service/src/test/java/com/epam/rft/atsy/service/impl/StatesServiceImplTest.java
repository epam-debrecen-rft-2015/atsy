package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.StateEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.StatesRepository;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.*;

/**
 * Created by Gabor_Ivanyi-Nagy on 7/11/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class StatesServiceImplTest {

    private static final Long FIRST_ID = 1L;
    private static final Integer ONCE_RAN = 1;
    private static final Type STATE_VIEW_DTO_LIST_TYPE = new TypeToken<List<StateViewDTO>>() {}.getType();

    private Date now;
    private static final String DATE_FORMAT_CONSTANT = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_CONSTANT);

    private static final ApplicationEntity APPLICATION_ENTITY_WITHOUT_STATES = ApplicationEntity.builder().id(FIRST_ID).build();
    private static final List<StateEntity> EMPTY_STATE_ENTITY_LIST = Collections.emptyList();
    private static final List<StateViewDTO> EMPTY_STATE_VIEW_DTO_LIST = Collections.emptyList();

    private ApplicationEntity applicationEntity;
    private StateEntity stateEntity;
    private StateViewDTO stateViewDTO;
    private StateDTO stateDTO;

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
        applicationEntity = ApplicationEntity.builder().id(FIRST_ID).build();
        stateEntity = StateEntity.builder().id(FIRST_ID).applicationEntity(applicationEntity).creationDate(now).build();
        stateViewDTO = StateViewDTO.builder().id(FIRST_ID).build();
        stateDTO = StateDTO.builder().id(FIRST_ID).build();

        stateEntityListWithSingleElement = Arrays.asList(stateEntity);
        stateEntityListWithThreeElements = Arrays.asList(stateEntity, stateEntity, stateEntity);
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

        then(applicationsRepository).should(times(ONCE_RAN)).findOne(FIRST_ID);
        then(statesRepository).should(times(ONCE_RAN)).findByApplicationEntityOrderByStateIndexDesc(APPLICATION_ENTITY_WITHOUT_STATES);
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
        assertThat(stateViewDTOList, equalTo(stateViewDTOListWithSingleElement));
        assertStateViewDtoListByCreationDateAsString(stateViewDTOList);

        then(applicationsRepository).should(times(ONCE_RAN)).findOne(FIRST_ID);
        then(statesRepository).should(times(ONCE_RAN)).findByApplicationEntityOrderByStateIndexDesc(applicationEntity);
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
        assertThat(stateViewDTOList, equalTo(stateViewDTOListWithThreeElements));
        assertStateViewDtoListByCreationDateAsString(stateViewDTOList);

        then(applicationsRepository).should(times(ONCE_RAN)).findOne(FIRST_ID);
        then(statesRepository).should(times(ONCE_RAN)).findByApplicationEntityOrderByStateIndexDesc(applicationEntity);
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

        then(applicationsRepository).should((times(ONCE_RAN))).findOne(FIRST_ID);
        then(statesRepository).should(times(ONCE_RAN)).save(stateEntity);
    }

    private void assertStateViewDtoListByCreationDateAsString(List<StateViewDTO> stateViewDTOList) {
        for (StateViewDTO stateViewDTO : stateViewDTOList) {
            assertThat(stateViewDTO.getCreationDate(), equalTo(SIMPLE_DATE_FORMAT.format(now)));
        }
    }
}
