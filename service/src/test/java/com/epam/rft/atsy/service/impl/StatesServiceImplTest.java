package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.StateEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.StatesRepository;
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
import java.time.LocalDate;
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

    private static final long FIRST_ID = 1L;
    private static final Type STATEVIEWDTO_LIST_TYPE = new TypeToken<List<StateViewDTO>>() {}.getType();

    private static final ApplicationEntity EMPTY_APPLICATION_ENTITY = new ApplicationEntity();
    private static final List<StateEntity> EMPTY_STATE_ENTITY_LIST = Collections.emptyList();
    private static final List<StateViewDTO> EMPTY_STATE_VIEW_DTO_LIST = Collections.emptyList();

    private static final ApplicationEntity APPLICATION_ENTITY = ApplicationEntity.builder().id(FIRST_ID).build();
    private static final StateEntity STATE_ENTITY = StateEntity.builder().creationDate(new Date()).build();
    private static final StateViewDTO STATE_VIEW_DTO = StateViewDTO.builder().creationDate(new SimpleDateFormat().format(STATE_ENTITY.getCreationDate())).build();
    private static final List<StateEntity> STATE_ENTITY_LIST_WITH_ONE_ELEMENT = Arrays.asList(STATE_ENTITY);
    private static final List<StateViewDTO> STATE_VIEW_DTO_LIST_WITH_ONE_ELEMENT = Arrays.asList(STATE_VIEW_DTO);


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
        given(modelMapper.map(EMPTY_STATE_ENTITY_LIST, STATEVIEWDTO_LIST_TYPE)).willReturn(EMPTY_STATE_VIEW_DTO_LIST);

        // When
        List<StateViewDTO> stateViewDTOList = statesService.getStatesByApplicationId(FIRST_ID);

        // Then
        assertThat(stateViewDTOList, notNullValue());
        assertThat(stateViewDTOList.isEmpty(), is(true));

        then(applicationsRepository).should(times(1)).findOne(FIRST_ID);
        then(statesRepository).should(times(1)).findByApplicationEntityOrderByStateIndexDesc(EMPTY_APPLICATION_ENTITY);
    }


    @Test
    public void getStatesByApplicationIdShouldReturnAListWithOneElement() {
        // Given
        given(applicationsRepository.findOne(FIRST_ID)).willReturn(APPLICATION_ENTITY);
        given(statesRepository.findByApplicationEntityOrderByStateIndexDesc(APPLICATION_ENTITY)).willReturn(STATE_ENTITY_LIST_WITH_ONE_ELEMENT);
        given(modelMapper.map(STATE_ENTITY_LIST_WITH_ONE_ELEMENT, STATEVIEWDTO_LIST_TYPE)).willReturn(STATE_VIEW_DTO_LIST_WITH_ONE_ELEMENT);

        // When
        List<StateViewDTO> stateViewDTOList = statesService.getStatesByApplicationId(FIRST_ID);

        // Then
        assertEquals(stateViewDTOList, STATE_VIEW_DTO_LIST_WITH_ONE_ELEMENT);

        then(applicationsRepository).should(times(1)).findOne(FIRST_ID);
        then(statesRepository).should(times(1)).findByApplicationEntityOrderByStateIndexDesc(APPLICATION_ENTITY);
    }

}
