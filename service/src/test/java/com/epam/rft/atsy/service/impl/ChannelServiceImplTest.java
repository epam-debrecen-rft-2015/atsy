package com.epam.rft.atsy.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.persistence.repositories.ChannelRepository;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.exception.DuplicateChannelException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
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
public class ChannelServiceImplTest {
  private static final Long CHANNEL_ID_FACEBOOK = 1L;
  private static final String CHANNEL_NAME_FACEBOOK = "facebook";
  private static final Long CHANNEL_ID_DTK = 2L;
  private static final String CHANNEL_NAME_DTK = "dtk";
  private static final Long CHANNEL_ID_LINKEDIN = 3L;
  private static final Long CHANNDEL_ID_NON_EXISTENT = -1L;
  private static final String CHANNEL_NAME_LINKEDIN = "linkedin hírdetés";
  private static final String CHANNEL_NAME_NON_EXISTENT = "Olympiad";
  private static final List<ChannelEntity> EMPTY_CHANNEL_ENTITY_LIST = Collections.emptyList();
  private static final List<ChannelDTO> EMPTY_CHANNEL_DTO_LIST = Collections.emptyList();


  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Mock
  private ConverterService converterService;

  @Mock
  private ChannelRepository channelRepository;

  @InjectMocks
  private ChannelServiceImpl channelService;

  private ChannelDTO facebookDto;
  private ChannelEntity facebookEntity;
  private ChannelDTO dtkDto;
  private ChannelEntity dtkEntity;
  private ChannelDTO linkedinDto;
  private ChannelEntity linkedinEntity;
  private List<ChannelEntity> expectedChannelEntityList;
  private List<ChannelDTO> expectedChannelDTOList;

  @Before
  public void init() {
    facebookDto = ChannelDTO.builder().id(CHANNEL_ID_FACEBOOK).name(CHANNEL_NAME_FACEBOOK).build();
    facebookEntity =
        ChannelEntity.builder().id(CHANNEL_ID_FACEBOOK).name(CHANNEL_NAME_FACEBOOK).build();
    dtkDto = ChannelDTO.builder().id(CHANNEL_ID_DTK).name(CHANNEL_NAME_DTK).build();
    dtkEntity = ChannelEntity.builder().id(CHANNEL_ID_DTK).name(CHANNEL_NAME_DTK).build();
    linkedinDto = ChannelDTO.builder().id(CHANNEL_ID_LINKEDIN).name(CHANNEL_NAME_LINKEDIN).build();
    linkedinEntity =
        ChannelEntity.builder().id(CHANNEL_ID_LINKEDIN).name(CHANNEL_NAME_LINKEDIN).build();
    expectedChannelDTOList = Arrays.asList(facebookDto, dtkDto, linkedinDto);
    expectedChannelEntityList = Arrays.asList(facebookEntity, dtkEntity, linkedinEntity);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getChannelDtoByIdShouldThrowIllegalArgumentExceptionWhenChannelIdIsNull() {
    // Given

    // When
    channelService.getChannelDtoById(null);

    // Then
  }

  @Test
  public void getChannelDtoByIdShouldReturnNullChannelDtoWhenChannelIdIsNonExistent() {
    // Given
    given(channelRepository.findOne(CHANNDEL_ID_NON_EXISTENT)).willReturn(null);

    // When
    ChannelDTO channelDTO = channelService.getChannelDtoById(CHANNDEL_ID_NON_EXISTENT);

    // Then
    assertThat(channelDTO, nullValue());

    then(channelRepository).should().findOne(CHANNDEL_ID_NON_EXISTENT);
    verifyZeroInteractions(converterService);
  }

  @Test
  public void getChannelDtoByIdShouldReturnExistingChannelDtoWhenChannelIdIsExisting() {
    // Given
    given(channelRepository.findOne(CHANNEL_ID_FACEBOOK)).willReturn(facebookEntity);
    given(converterService.convert(facebookEntity, ChannelDTO.class)).willReturn(facebookDto);

    // When
    ChannelDTO channelDTO = channelService.getChannelDtoById(CHANNEL_ID_FACEBOOK);

    // Then
    assertThat(channelDTO, notNullValue());
    assertThat(channelDTO, equalTo(facebookDto));

    then(channelRepository).should().findOne(CHANNEL_ID_FACEBOOK);
    then(converterService).should().convert(facebookEntity, ChannelDTO.class);
  }

  @Test
  public void getAllChannelsShouldReturnEmptyListWhenThereAreNoChannels() {
    // Given
    given(channelRepository.findAll()).willReturn(EMPTY_CHANNEL_ENTITY_LIST);
    given(converterService.convert(EMPTY_CHANNEL_ENTITY_LIST, ChannelDTO.class))
        .willReturn(EMPTY_CHANNEL_DTO_LIST);

    // When
    Collection<ChannelDTO> channels = channelService.getAllChannels();

    // Then
    assertThat(channels, notNullValue());
    assertTrue(channels.isEmpty());

    then(channelRepository).should().findAll();
    then(converterService).should().convert(EMPTY_CHANNEL_ENTITY_LIST, ChannelDTO.class);
  }


  @Test
  public void getAllChannelsShouldReturnSingleElementListWhenThereIsOneChannel() {
    // Given
    List<ChannelEntity> channels = Arrays.asList(facebookEntity);

    List<ChannelDTO> expected = Arrays.asList(facebookDto);

    given(channelRepository.findAll()).willReturn(channels);
    given(converterService.convert(channels, ChannelDTO.class)).willReturn(expected);

    // When
    Collection<ChannelDTO> result = channelService.getAllChannels();

    // Then
    assertEquals(result, expected);

    then(channelRepository).should().findAll();
    then(converterService).should().convert(channels, ChannelDTO.class);
  }

  @Test
  public void getAllChannelsShouldReturnThreeElementListWhenThereAreThreeChannels() {
    // Given
    given(channelRepository.findAll()).willReturn(expectedChannelEntityList);
    given(converterService.convert(expectedChannelEntityList, ChannelDTO.class))
        .willReturn(expectedChannelDTOList);

    // When
    Collection<ChannelDTO> result = channelService.getAllChannels();

    // Then
    assertEquals(result, expectedChannelDTOList);

    then(channelRepository).should().findAll();
    then(converterService).should().convert(expectedChannelEntityList, ChannelDTO.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getChannelDtoByNameShouldThrowIllegalArgumentExceptionWhenChannelNameIsNull() {
    // Given

    // When
    channelService.getChannelDtoByName(null);

    // Then
  }

  @Test
  public void getChannelDtoByNameShouldReturnNullChannelDtoWhenChannelNameIsNonExistent() {
    // Given
    given(channelRepository.findByName(CHANNEL_NAME_NON_EXISTENT)).willReturn(null);

    // When
    ChannelDTO channelDTO = channelService.getChannelDtoByName(CHANNEL_NAME_NON_EXISTENT);

    // Then
    assertThat(channelDTO, nullValue());

    then(channelRepository).should().findByName(CHANNEL_NAME_NON_EXISTENT);
    verifyZeroInteractions(converterService);
  }

  @Test
  public void getChannelDtoByNameShouldReturnExistingChannelDtoWhenChannelNameIsExisting() {
    // Given
    given(channelRepository.findByName(CHANNEL_NAME_FACEBOOK)).willReturn(facebookEntity);
    given(converterService.convert(facebookEntity, ChannelDTO.class)).willReturn(facebookDto);

    // When
    ChannelDTO channelDTO = channelService.getChannelDtoByName(CHANNEL_NAME_FACEBOOK);

    // Then
    assertThat(channelDTO, notNullValue());
    assertThat(channelDTO, equalTo(facebookDto));

    then(channelRepository).should().findByName(CHANNEL_NAME_FACEBOOK);
    then(converterService).should().convert(facebookEntity, ChannelDTO.class);
  }

  @Test
  public void saveOrUpdateShouldThrowDREAfterCatchingConstraintViolationException()
      throws DuplicateChannelException {
    // Given
    given(converterService.convert(facebookDto, ChannelEntity.class)).willReturn(facebookEntity);

    given(channelRepository.saveAndFlush(facebookEntity))
        .willThrow(ConstraintViolationException.class);

    expectedException.expect(DuplicateChannelException.class);
    expectedException.expectMessage(CoreMatchers.endsWith(CHANNEL_NAME_FACEBOOK));
    expectedException.expectCause(Matchers.isA(ConstraintViolationException.class));

    // When
    channelService.saveOrUpdate(facebookDto);
  }

  @Test
  public void saveOrUpdateShouldThrowDREAfterCatchingDataIntegrityViolationException()
      throws DuplicateChannelException {
    // Given
    given(converterService.convert(facebookDto, ChannelEntity.class)).willReturn(facebookEntity);

    given(channelRepository.saveAndFlush(facebookEntity))
        .willThrow(DataIntegrityViolationException.class);

    expectedException.expect(DuplicateChannelException.class);
    expectedException.expectMessage(CoreMatchers.endsWith(CHANNEL_NAME_FACEBOOK));
    expectedException.expectCause(Matchers.isA(DataIntegrityViolationException.class));

    // When
    channelService.saveOrUpdate(facebookDto);
  }

  @Test
  public void saveOrUpdateShouldSaveValidChannelDTO() {
    // Given
    given(converterService.convert(facebookDto, ChannelEntity.class)).willReturn(facebookEntity);

    given(channelRepository.saveAndFlush(facebookEntity)).willReturn(facebookEntity);

    // When
    channelService.saveOrUpdate(facebookDto);

    // Then
    then(channelRepository).should().saveAndFlush(facebookEntity);
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveOrUpdateShouldThrowIllegalArgumentExceptionWhenNullPassed() {
    // When
    channelService.saveOrUpdate(null);
  }


}
