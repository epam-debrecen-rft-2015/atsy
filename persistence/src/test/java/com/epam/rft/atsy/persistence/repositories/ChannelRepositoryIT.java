package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.ChannelEntity;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChannelRepositoryIT extends AbstractRepositoryIT {

  private static final Long CHANNEL_ID_FACEBOOK = 4L;
  private static final String CHANNEL_NAME_FACEBOOK = "facebook";
  private static final String CHANNEL_NAME_NON_EXISTENT = "Olympiad";
  private static final String CHANNEL_NAME_WITH_TRUE_DELETED_FIELD = "street";

  @Autowired
  private ChannelRepository channelRepository;

  @Test
  public void findByNameShouldReturnNullWhenChannelNameIsNull() {
    // Given

    // When
    ChannelEntity actualChannelEntity = channelRepository.findByName(null);

    // Then
    assertThat(actualChannelEntity, nullValue());
  }

  @Test
  public void findByNameShouldReturnNullWhenChannelNameIsNonExistent() {
    // Given

    // When
    ChannelEntity actualChannelEntity = channelRepository.findByName(CHANNEL_NAME_NON_EXISTENT);

    // Then
    assertThat(actualChannelEntity, nullValue());
  }

  @Test
  public void findByNameShouldReturnExistingChannelEntityWhenChannelNameIsExisting() {
    // Given
    ChannelEntity expectedChannelEntity =

        ChannelEntity.builder().id(CHANNEL_ID_FACEBOOK).name(CHANNEL_NAME_FACEBOOK).deleted(false)
            .build();

    // When
    ChannelEntity actualChannelEntity = channelRepository.findByName(CHANNEL_NAME_FACEBOOK);

    // Then
    assertThat(actualChannelEntity, notNullValue());
    assertThat(actualChannelEntity, equalTo(expectedChannelEntity));
  }

  @Test

  public void findAllByDeletedFalseShouldNotContainAnEntityThatHasDeletedFieldWithTrueValue() {
    // Given

    ChannelEntity
        channelEntityWithTrueDeletedField =
        this.channelRepository.findByName(CHANNEL_NAME_WITH_TRUE_DELETED_FIELD);

    // When
    List<ChannelEntity> actualChannelEntityList = this.channelRepository.findAllByDeletedFalse();

    // Then
    assertThat(actualChannelEntityList, notNullValue());
    assertFalse(actualChannelEntityList.isEmpty());
    assertFalse(actualChannelEntityList.contains(channelEntityWithTrueDeletedField));
  }

  @Test

  public void findAllByDeletedFalseShouldReturnWithOnlyNonDeletedChannelEntities() {
    // Given

    // When
    List<ChannelEntity> actualChannelEntityList = this.channelRepository.findAllByDeletedFalse();

    // Then
    assertThat(actualChannelEntityList, notNullValue());
    assertThat(actualChannelEntityList.isEmpty(), is(false));
    assertNonDeletedChannelEntityList(actualChannelEntityList);
  }

  private void assertNonDeletedChannelEntityList(List<ChannelEntity> channelEntityList) {
    if (channelEntityList.stream().anyMatch(c -> c.isDeleted())) {
      Assert.fail();
    }
  }
}
