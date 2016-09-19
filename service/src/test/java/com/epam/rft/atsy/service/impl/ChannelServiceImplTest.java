package com.epam.rft.atsy.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.persistence.repositories.ChannelRepository;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.exception.ChannelNotFoundException;
import com.epam.rft.atsy.service.exception.DuplicateChannelException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
  private static final Long CHANNEL_ID_NON_EXISTENT = -1L;
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
  private ChannelDTO deletedFacebookDto;
  private ChannelEntity deletedFacebookEntity;
  private ChannelDTO dtkDto;
  private ChannelEntity dtkEntity;
  private ChannelDTO linkedinDto;
  private ChannelEntity linkedinEntity;
  private List<ChannelEntity> expectedChannelEntityList;
  private List<ChannelDTO> expectedChannelDTOList;

  @Before
  public void init() {
    facebookDto =
        ChannelDTO.builder().id(CHANNEL_ID_FACEBOOK).name(CHANNEL_NAME_FACEBOOK).deleted(false)
            .build();
    facebookEntity =
        ChannelEntity.builder().id(CHANNEL_ID_FACEBOOK).name(CHANNEL_NAME_FACEBOOK).deleted(false)
            .build();
    deletedFacebookDto =
        ChannelDTO.builder().id(CHANNEL_ID_FACEBOOK).name(CHANNEL_NAME_FACEBOOK).deleted(true)
            .build();
    deletedFacebookEntity =
        ChannelEntity.builder().id(CHANNEL_ID_FACEBOOK).name(CHANNEL_NAME_FACEBOOK).deleted(true)
            .build();
    dtkDto = ChannelDTO.builder().id(CHANNEL_ID_DTK).name(CHANNEL_NAME_DTK).deleted(false).build();
    dtkEntity =
        ChannelEntity.builder().id(CHANNEL_ID_DTK).name(CHANNEL_NAME_DTK).deleted(false).build();
    linkedinDto =
        ChannelDTO.builder().id(CHANNEL_ID_LINKEDIN).name(CHANNEL_NAME_LINKEDIN).deleted(false)
            .build();
    linkedinEntity =
        ChannelEntity.builder().id(CHANNEL_ID_LINKEDIN).name(CHANNEL_NAME_LINKEDIN).deleted(false)
            .build();
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
    given(channelRepository.findOne(CHANNEL_ID_NON_EXISTENT)).willReturn(null);

    // When
    ChannelDTO channelDTO = channelService.getChannelDtoById(CHANNEL_ID_NON_EXISTENT);

    // Then
    assertThat(channelDTO, nullValue());

    then(channelRepository).should().findOne(CHANNEL_ID_NON_EXISTENT);
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
  public void getAllNonDeletedChannelDtoShouldReturnEmptyListWhenThereAreNoChannels() {
    // Given
    given(channelRepository.findAllNonDeletedChannelEntity()).willReturn(EMPTY_CHANNEL_ENTITY_LIST);
    given(converterService.convert(EMPTY_CHANNEL_ENTITY_LIST, ChannelDTO.class))
        .willReturn(EMPTY_CHANNEL_DTO_LIST);

    // When
    Collection<ChannelDTO> channels = channelService.getAllNonDeletedChannelDto();

    // Then
    assertThat(channels, notNullValue());
    assertTrue(channels.isEmpty());

    then(channelRepository).should().findAllNonDeletedChannelEntity();
    then(converterService).should().convert(EMPTY_CHANNEL_ENTITY_LIST, ChannelDTO.class);
  }

  @Test
  public void getAllNonDeletedChannelDtoShouldReturnSingleElementListWhenThereIsOneChannel() {
    // Given
    List<ChannelEntity> channels = Arrays.asList(facebookEntity);

    List<ChannelDTO> expected = Arrays.asList(facebookDto);

    given(channelRepository.findAllNonDeletedChannelEntity()).willReturn(channels);
    given(converterService.convert(channels, ChannelDTO.class)).willReturn(expected);

    // When
    Collection<ChannelDTO> result = channelService.getAllNonDeletedChannelDto();

    // Then
    assertEquals(result, expected);

    then(channelRepository).should().findAllNonDeletedChannelEntity();
    then(converterService).should().convert(channels, ChannelDTO.class);
  }

  @Test
  public void getAllNonDeletedChannelDtoShouldReturnThreeElementListWhenThereAreThreeChannels() {
    // Given
    given(channelRepository.findAllNonDeletedChannelEntity()).willReturn(expectedChannelEntityList);
    given(converterService.convert(expectedChannelEntityList, ChannelDTO.class))
        .willReturn(expectedChannelDTOList);

    // When
    Collection<ChannelDTO> result = channelService.getAllNonDeletedChannelDto();

    // Then
    assertEquals(result, expectedChannelDTOList);

    then(channelRepository).should().findAllNonDeletedChannelEntity();
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


  @Test(expected = IllegalArgumentException.class)
  public void saveOrUpdateShouldThrowIllegalArgumentExceptionWhenNullPassed() {
    // Given

    // When
    this.channelService.saveOrUpdate(null);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveOrUpdateShouldThrowIllegalArgumentExceptionWhenChannelNameIsNull() {
    // Given
    ChannelDTO channelDTO = ChannelDTO.builder().name(null).build();

    // When
    this.channelService.saveOrUpdate(channelDTO);

    // Then
  }

  @Test(expected = DuplicateChannelException.class)
  public void saveOrUpdateShouldThrowDuplicateChannelExceptionExceptionWhenChannelEntityExistsAndItsDeletedFieldIsFalse() {
    // Given
    given(this.channelRepository.findByName(CHANNEL_NAME_FACEBOOK)).willReturn(facebookEntity);

    // When
    this.channelService.saveOrUpdate(facebookDto);

    // Then
  }

  @Test
  public void saveOrUpdateShouldSaveValidChannelDtoWhenThatWasDeleted() {
    // Given
    ArgumentCaptor<ChannelEntity> channelEntityArgumentCaptor =
        ArgumentCaptor.forClass(ChannelEntity.class);
    given(this.channelRepository.findByName(CHANNEL_NAME_FACEBOOK))
        .willReturn(deletedFacebookEntity);

    // When
    this.channelService.saveOrUpdate(deletedFacebookDto);

    // Then
    verify(this.channelRepository).saveAndFlush(channelEntityArgumentCaptor.capture());
    assertThat(facebookEntity, equalTo(channelEntityArgumentCaptor.getValue()));

    then(this.channelRepository).should().findByName(CHANNEL_NAME_FACEBOOK);
    then(this.channelRepository).should().saveAndFlush(facebookEntity);
    verifyZeroInteractions(this.converterService);
  }

  @Test
  public void saveOrUpdateShouldSaveValidChannelDTO() {
    // Given
    given(this.channelRepository.findByName(CHANNEL_NAME_FACEBOOK)).willReturn(null);
    given(this.converterService.convert(facebookDto, ChannelEntity.class))
        .willReturn(facebookEntity);

    // When
    this.channelService.saveOrUpdate(facebookDto);

    // Then
    then(this.channelRepository).should().findByName(CHANNEL_NAME_FACEBOOK);
    then(this.channelRepository).should().saveAndFlush(facebookEntity);
    then(this.converterService).should().convert(facebookDto, ChannelEntity.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteChannelDtoLogicallyByIdShouldThrowIllegalArgumentExceptionWhenChannelIdIsNull()
      throws
      ChannelNotFoundException {
    // Given

    // When
    this.channelService.deleteChannelDtoLogicallyById(null);

    // Then
  }

  @Test(expected = ChannelNotFoundException.class)
  public void deleteChannelDtoLogicallyByIdShouldThrowChannelNotFoundExceptionWhenChannelEntityNotExists()
      throws ChannelNotFoundException {
    // Given
    given(this.channelRepository.findOne(CHANNEL_ID_NON_EXISTENT)).willReturn(null);

    // When
    this.channelService.deleteChannelDtoLogicallyById(CHANNEL_ID_NON_EXISTENT);

    // Then
  }

  @Test
  public void deleteChannelDtoLogicallyByIdShouldDeletedLogicallyWhenChannelEntityExists()
      throws ChannelNotFoundException {
    // Given
    ArgumentCaptor<ChannelEntity> channelEntityArgumentCaptor =
        ArgumentCaptor.forClass(ChannelEntity.class);
    given(this.channelRepository.findOne(CHANNEL_ID_FACEBOOK)).willReturn(facebookEntity);

    // When
    this.channelService.deleteChannelDtoLogicallyById(CHANNEL_ID_FACEBOOK);

    // Then
    verify(channelRepository).saveAndFlush(channelEntityArgumentCaptor.capture());
    assertThat(deletedFacebookEntity, equalTo(channelEntityArgumentCaptor.getValue()));

    then(channelRepository).should().findOne(CHANNEL_ID_FACEBOOK);
    then(channelRepository).should().saveAndFlush(deletedFacebookEntity);
  }

}
