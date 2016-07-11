package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.StateEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.StatesRepository;
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
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.*;

/**
 * Created by Gabor_Ivanyi-Nagy on 7/11/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class StatesServiceImplTest {

    private static final Long FIRST_ID = 1L;
    private static final Type STATE_VIEW_DTO_LIST_TYPE = new TypeToken<List<StateViewDTO>>() {}.getType();
    private static final Date NOW = new Date();
    private static final String DATE_FORMAT_CONSTANT = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_CONSTANT);

    private static final ApplicationEntity EMPTY_APPLICATION_ENTITY = new ApplicationEntity();
    private static final List<StateEntity> EMPTY_STATE_ENTITY_LIST = Collections.emptyList();
    private static final List<StateViewDTO> EMPTY_STATE_VIEW_DTO_LIST = Collections.emptyList();

    private static final ApplicationEntity APPLICATION_ENTITY = ApplicationEntity.builder().id(FIRST_ID).build();
    private static final StateEntity STATE_ENTITY = StateEntity.builder().id(FIRST_ID).creationDate(NOW).build();
    private static final StateViewDTO STATE_VIEW_DTO = StateViewDTO.builder().id(FIRST_ID).creationDate(SIMPLE_DATE_FORMAT.format(NOW)).build();

    private static final List<StateEntity> STATE_ENTITY_LIST_WITH_SINGLE_ELEMENT = Arrays.asList(STATE_ENTITY);
    private static final List<StateEntity> STATE_ENTITY_LIST_WITH_THREE_ELEMENTS = Arrays.asList(STATE_ENTITY, STATE_ENTITY, STATE_ENTITY);
    private static final List<StateViewDTO> STATE_VIEW_DTO_LIST_WITH_SINGLE_ELEMENT = Arrays.asList(STATE_VIEW_DTO);
    private static final List<StateViewDTO> STATE_VIEW_DTO_LIST_WITH_THREE_ELEMENTS = Arrays.asList(STATE_VIEW_DTO, STATE_VIEW_DTO, STATE_VIEW_DTO);


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
    public void getStatesByApplicationIdShouldThrowIEAWhenApplicationIdIsNull() {
        // Given

        // When
        statesService.getStatesByApplicationId(null);

        // Then
    }


    @Test(expected = IllegalArgumentException.class)
    public void getStatesByApplicationIdShouldThrowIEAWhenApplicationIsNull() {
        // Given
        given(applicationsRepository.findOne(FIRST_ID)).willReturn(null);

        // When
        statesService.getStatesByApplicationId(FIRST_ID);

        // Then
    }

    @Test
    public void getStatesByApplicationIdShouldReturnAnEmptyList() {
        // Given
        given(applicationsRepository.findOne(FIRST_ID)).willReturn(EMPTY_APPLICATION_ENTITY);
        given(statesRepository.findByApplicationEntityOrderByStateIndexDesc(EMPTY_APPLICATION_ENTITY)).willReturn(EMPTY_STATE_ENTITY_LIST);
        given(modelMapper.map(EMPTY_STATE_ENTITY_LIST, STATE_VIEW_DTO_LIST_TYPE)).willReturn(EMPTY_STATE_VIEW_DTO_LIST);

        // When
        List<StateViewDTO> stateViewDTOList = statesService.getStatesByApplicationId(FIRST_ID);

        // Then
        assertThat(stateViewDTOList, notNullValue());
        assertThat(stateViewDTOList.isEmpty(), is(true));

        then(applicationsRepository).should(times(1)).findOne(FIRST_ID);
        then(statesRepository).should(times(1)).findByApplicationEntityOrderByStateIndexDesc(EMPTY_APPLICATION_ENTITY);
    }

    @Test
    public void getStatesByApplicationIdShouldReturnAListWithSingleElement() {
        // Given
        given(applicationsRepository.findOne(FIRST_ID)).willReturn(APPLICATION_ENTITY);
        given(statesRepository.findByApplicationEntityOrderByStateIndexDesc(APPLICATION_ENTITY)).willReturn(STATE_ENTITY_LIST_WITH_SINGLE_ELEMENT);
        given(modelMapper.map(STATE_ENTITY_LIST_WITH_SINGLE_ELEMENT, STATE_VIEW_DTO_LIST_TYPE)).willReturn(STATE_VIEW_DTO_LIST_WITH_SINGLE_ELEMENT);

        // When
        List<StateViewDTO> stateViewDTOList = statesService.getStatesByApplicationId(FIRST_ID);

        // Then
        assertThat(stateViewDTOList, equalTo(STATE_VIEW_DTO_LIST_WITH_SINGLE_ELEMENT));

        then(applicationsRepository).should(times(1)).findOne(FIRST_ID);
        then(statesRepository).should(times(1)).findByApplicationEntityOrderByStateIndexDesc(APPLICATION_ENTITY);
    }

    @Test
    public void getStatesByApplicationIdShouldReturnAListWithThreeElement() {
        // Given
        given(applicationsRepository.findOne(FIRST_ID)).willReturn(APPLICATION_ENTITY);
        given(statesRepository.findByApplicationEntityOrderByStateIndexDesc(APPLICATION_ENTITY)).willReturn(STATE_ENTITY_LIST_WITH_THREE_ELEMENTS);
        given(modelMapper.map(STATE_ENTITY_LIST_WITH_THREE_ELEMENTS, STATE_VIEW_DTO_LIST_TYPE)).willReturn(STATE_VIEW_DTO_LIST_WITH_THREE_ELEMENTS);

        // When
        List<StateViewDTO> stateViewDTOList = statesService.getStatesByApplicationId(FIRST_ID);

        // Then
        assertThat(stateViewDTOList, equalTo(STATE_VIEW_DTO_LIST_WITH_THREE_ELEMENTS));

        then(applicationsRepository).should(times(1)).findOne(FIRST_ID);
        then(statesRepository).should(times(1)).findByApplicationEntityOrderByStateIndexDesc(APPLICATION_ENTITY);
    }
}
