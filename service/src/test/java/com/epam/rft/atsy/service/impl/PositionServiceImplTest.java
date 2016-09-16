package com.epam.rft.atsy.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyZeroInteractions;

import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.repositories.PositionRepository;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.exception.DuplicatePositionException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PositionServiceImplTest {

  private static final Long DEVELOPER_ID = 1L;
  private static final Long POSITION_ID_NON_EXISTENT = -1L;
  private static final String DEVELOPER_NAME = "Developer";
  private static final Long NON_EXISTENT_ID = 2L;
  private static final String POSITION_NAME_NON_EXISTENT = "Table tennis instructor";
  private static final List<PositionEntity> EMPTY_POSITION_ENTITY_LIST = Collections.emptyList();
  private static final List<PositionDTO> EMPTY_POSITION_DTO_LIST = Collections.emptyList();

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Mock
  private ConverterService converterService;

  @Mock
  private PositionRepository positionRepository;

  @InjectMocks
  private PositionServiceImpl positionService;

  private PositionEntity developerEntity;
  private PositionDTO developerDto;
  private List<PositionEntity> expectedPositionEntityList;
  private List<PositionDTO> expectedPositionDtoList;

  @Before
  public void setUp() {
    developerEntity = PositionEntity.builder().id(DEVELOPER_ID).name(DEVELOPER_NAME).build();

    developerDto = PositionDTO.builder().id(DEVELOPER_ID).name(DEVELOPER_NAME).build();

    expectedPositionEntityList = Arrays.asList(developerEntity, developerEntity, developerEntity);

    expectedPositionDtoList = Arrays.asList(developerDto, developerDto, developerDto);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPositionsByIdShouldThrowIllegalArgumentsExceptionWhenTheListOfIdsIsNull() {
    // When
    positionService.getPositionsById(null);
  }

  @Test
  public void getPositionsByIdShouldReturnEmptyListWhenNoPositionsFoundWithTheGivenIds() {
    // Given
    List<Long> ids = Arrays.asList(NON_EXISTENT_ID);
    given(positionRepository.findAll(ids)).willReturn(EMPTY_POSITION_ENTITY_LIST);

    // When
    List<PositionDTO> positionDTOs = positionService.getPositionsById(ids);

    // Then
    assertTrue(positionDTOs.isEmpty());
  }

  @Test
  public void getPositionsByIdShouldReturnAListOfPositionsWhenThereArePositionsWithTheGivenId() {
    // Given
    List<Long> ids = Arrays.asList(DEVELOPER_ID, DEVELOPER_ID, DEVELOPER_ID);
    given(positionRepository.findAll(ids)).willReturn(expectedPositionEntityList);
    given(converterService.convert(expectedPositionEntityList, PositionDTO.class))
        .willReturn(expectedPositionDtoList);

    // When
    List<PositionDTO> actualPositionDtoList = positionService.getPositionsById(ids);

    // Then
    assertEquals(actualPositionDtoList, expectedPositionDtoList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPositionDtoByIdShouldThrowIllegalArgumentExceptionWhenPositionIdIsNull() {
    // Given

    // When
    positionService.getPositionDtoById(null);

    // Then
  }

  @Test
  public void getPositionDtoByIdShouldReturnNullPositionDtoWhenPositionIdIsNonExistent() {
    // Given
    given(positionRepository.findOne(POSITION_ID_NON_EXISTENT)).willReturn(null);

    // When
    PositionDTO positionDTO = positionService.getPositionDtoById(POSITION_ID_NON_EXISTENT);

    // Then
    assertThat(positionDTO, nullValue());

    then(positionRepository).should().findOne(POSITION_ID_NON_EXISTENT);
    verifyZeroInteractions(converterService);
  }

  @Test
  public void getPositionDtoByIdShouldReturnExistingPositionDtoWhenPositionIdIsExisting() {
    // Given
    given(positionRepository.findOne(DEVELOPER_ID)).willReturn(developerEntity);
    given(converterService.convert(developerEntity, PositionDTO.class)).willReturn(developerDto);

    // When
    PositionDTO positionDTO = positionService.getPositionDtoById(DEVELOPER_ID);

    // Then
    assertThat(positionDTO, notNullValue());
    assertThat(positionDTO, equalTo(developerDto));

    then(positionRepository).should().findOne(DEVELOPER_ID);
    then(converterService).should().convert(developerEntity, PositionDTO.class);
  }

  @Test
  public void getAllPositionsShouldReturnEmptyListWhenThereAreNoPositions() {
    // Given
    given(positionRepository.findAll()).willReturn(EMPTY_POSITION_ENTITY_LIST);
    given(converterService.convert(EMPTY_POSITION_ENTITY_LIST, PositionDTO.class))
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

    List<PositionDTO> expected = Arrays.asList(developerDto);

    given(positionRepository.findAll()).willReturn(positions);
    given(converterService.convert(positions, PositionDTO.class)).willReturn(expected);

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
    given(converterService.convert(expectedPositionEntityList, PositionDTO.class))
        .willReturn(expectedPositionDtoList);

    // When
    Collection<PositionDTO> result = positionService.getAllPositions();

    // Then
    assertEquals(result, expectedPositionDtoList);

    then(positionRepository).should(times(1)).findAll();
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPositionDtoByNameShouldThrowIllegalArgumentExceptionWhenPositionNameIsNull() {
    // Given

    // When
    positionService.getPositionDtoByName(null);

    // Then
  }

  @Test
  public void getPositionDtoByNameShouldReturnNullPositionDtoWhenPositionNameIsNonExistent() {
    // Given
    given(positionRepository.findByName(POSITION_NAME_NON_EXISTENT)).willReturn(null);

    // When
    PositionDTO positionDTO = positionService.getPositionDtoByName(POSITION_NAME_NON_EXISTENT);

    // Then
    assertThat(positionDTO, nullValue());

    then(positionRepository).should().findByName(POSITION_NAME_NON_EXISTENT);
    verifyZeroInteractions(converterService);
  }

  @Test
  public void getPositionDtoByNameShouldReturnExistingPositionDtoWhenPositionNameIsExisting() {
    // Given
    given(positionRepository.findByName(DEVELOPER_NAME)).willReturn(developerEntity);
    given(converterService.convert(developerEntity, PositionDTO.class)).willReturn(developerDto);

    // When
    PositionDTO positionDTO = positionService.getPositionDtoByName(DEVELOPER_NAME);

    // Then
    assertThat(positionDTO, notNullValue());
    assertThat(positionDTO, equalTo(developerDto));

    then(positionRepository).should().findByName(DEVELOPER_NAME);
    then(converterService).should().convert(developerEntity, PositionDTO.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveOrUpdateShouldThrowIAEWhenNullPassed() {
    // When
    positionService.saveOrUpdate(null);
  }

  @Test
  public void saveOrUpdateShouldThrowDREAfterCatchingConstraintViolationException()
      throws DuplicatePositionException {
    // Given
    given(converterService.convert(developerDto, PositionEntity.class)).willReturn(developerEntity);

    given(positionRepository.saveAndFlush(developerEntity))
        .willThrow(ConstraintViolationException.class);

    expectedException.expect(DuplicatePositionException.class);
    expectedException.expectMessage(CoreMatchers.endsWith(DEVELOPER_NAME));
    expectedException.expectCause(Matchers.isA(ConstraintViolationException.class));

    // When
    positionService.saveOrUpdate(developerDto);
  }

  @Test
  public void saveOrUpdateShouldThrowDREAfterCatchingDataIntegrityViolationException()
      throws DuplicatePositionException {
    // Given
    given(converterService.convert(developerDto, PositionEntity.class)).willReturn(developerEntity);

    given(positionRepository.saveAndFlush(developerEntity))
        .willThrow(DataIntegrityViolationException.class);

    expectedException.expect(DuplicatePositionException.class);
    expectedException.expectMessage(CoreMatchers.endsWith(DEVELOPER_NAME));
    expectedException.expectCause(Matchers.isA(DataIntegrityViolationException.class));

    // When
    positionService.saveOrUpdate(developerDto);
  }

  @Test
  public void saveOrUpdateShouldSaveAProperPositionDTO() {
    // Given
    given(converterService.convert(developerDto, PositionEntity.class)).willReturn(developerEntity);

    given(positionRepository.saveAndFlush(developerEntity)).willReturn(developerEntity);

    // When
    positionService.saveOrUpdate(developerDto);

    // Then
    then(positionRepository).should(times(1)).saveAndFlush(developerEntity);
  }
}
