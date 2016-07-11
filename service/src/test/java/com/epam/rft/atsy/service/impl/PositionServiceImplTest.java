package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.repositories.PositionRepository;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class PositionServiceImplTest {
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PositionRepository positionRepository;

    @InjectMocks
    private PositionServiceImpl positionService;

    private static final Type POSITIONDTO_LIST_TYPE = new TypeToken<List<PositionDTO>>() {}.getType();

    private static final Long DEVELOPER_ID = 1L;
    private static final String DEVELOPER_NAME = "Developer";

    private static final List<PositionEntity> EMPTY_POSITION_ENTITY_LIST = Collections.emptyList();
    private static final Collection<PositionDTO> EMPTY_POSITION_DTO_LIST = Collections.emptyList();

    private PositionEntity developerEntity;
    private PositionDTO developerDto;

    private List<PositionEntity> expectedPositionEntityList;
    private Collection<PositionDTO> expectedPositionDtoList;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        developerEntity = PositionEntity.builder().id(DEVELOPER_ID).name(DEVELOPER_NAME).build();

        developerDto = PositionDTO.builder().id(DEVELOPER_ID).name(DEVELOPER_NAME).build();

        expectedPositionEntityList = Arrays.asList(developerEntity, developerEntity, developerEntity);

        expectedPositionDtoList = Arrays.asList(developerDto, developerDto, developerDto);
    }

    @Test
    public void getAllPositionsShouldReturnEmptyListWhenThereAreNoPositions() {
        // Given
        given(positionRepository.findAll()).willReturn(EMPTY_POSITION_ENTITY_LIST);
        given(modelMapper.map(EMPTY_POSITION_ENTITY_LIST, POSITIONDTO_LIST_TYPE))
                .willReturn(EMPTY_POSITION_DTO_LIST);

        // When
        Collection<PositionDTO> positions = positionService.getAllPositions();

        // Then
        assertThat(positions, notNullValue());
        assertThat(positions.isEmpty(), is(true));

        then(positionRepository).should(times(1)).findAll();
    }

    @Test
    public void getAllPositionsShouldReturnSingleElementListWhenThereIsOnePosition() {
        // Given
        List<PositionEntity> positions = Arrays.asList(developerEntity);

        Collection<PositionDTO> expected = Arrays.asList(developerDto);

        given(positionRepository.findAll()).willReturn(positions);
        given(modelMapper.map(positions, POSITIONDTO_LIST_TYPE)).willReturn(expected);

        // When
        Collection<PositionDTO> result = positionService.getAllPositions();

        // Then
        assertEquals(result, expected);

        then(positionRepository).should(times(1)).findAll();
    }

    @Test
    public void getAllPositionsShouldReturnThreeElementListWhenThereAreThreePositions() {
        // Given
        given(positionRepository.findAll()).willReturn(expectedPositionEntityList);
        given(modelMapper.map(expectedPositionEntityList, POSITIONDTO_LIST_TYPE))
                .willReturn(expectedPositionDtoList);

        // When
        Collection<PositionDTO> result = positionService.getAllPositions();

        // Then
        assertEquals(result, expectedPositionDtoList);

        then(positionRepository).should(times(1)).findAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveOrUpdateShouldThrowIAEWhenNullPassed() {
        // When
        positionService.saveOrUpdate(null);
    }

    @Test
    public void saveOrUpdateShouldThrowDREAfterCatchingConstraintViolationException()
            throws DuplicateRecordException {
        // Given
        given(modelMapper.map(developerDto, PositionEntity.class)).willReturn(developerEntity);

        given(positionRepository.save(developerEntity)).willThrow(ConstraintViolationException.class);

        expectedException.expect(DuplicateRecordException.class);
        expectedException.expectMessage(CoreMatchers.endsWith(DEVELOPER_NAME));
        expectedException.expectCause(Matchers.isA(ConstraintViolationException.class));

        // When
        positionService.saveOrUpdate(developerDto);
    }

    @Test
    public void saveOrUpdateShouldThrowDREAfterCatchingDataIntegrityViolationException()
            throws DuplicateRecordException {
        // Given
        given(modelMapper.map(developerDto, PositionEntity.class)).willReturn(developerEntity);

        given(positionRepository.save(developerEntity)).willThrow(DataIntegrityViolationException.class);

        expectedException.expect(DuplicateRecordException.class);
        expectedException.expectMessage(CoreMatchers.endsWith(DEVELOPER_NAME));
        expectedException.expectCause(Matchers.isA(DataIntegrityViolationException.class));

        // When
        positionService.saveOrUpdate(developerDto);
    }

    @Test
    public void saveOrUpdateShouldSaveAProperPositionDTO() {
        // Given
        given(modelMapper.map(developerDto, PositionEntity.class)).willReturn(developerEntity);

        given(positionRepository.save(developerEntity)).willReturn(developerEntity);

        // When
        positionService.saveOrUpdate(developerDto);

        // Then
        then(positionRepository).should(times(1)).save(developerEntity);
    }
}