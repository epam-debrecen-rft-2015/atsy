package com.epam.rft.atsy.persistence.repositories;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ChannelRepositoryIT extends AbstractRepositoryIT {

  private static final Long CHANNEL_ID_FACEBOOK = 4L;
  private static final String CHANNEL_NAME_FACEBOOK = "facebook";
  private static final String CHANNEL_NAME_NON_EXISTENT = "Olympiad";

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
        ChannelEntity.builder().id(CHANNEL_ID_FACEBOOK).name(CHANNEL_NAME_FACEBOOK).build();

    // When
    ChannelEntity actualChannelEntity = channelRepository.findByName(CHANNEL_NAME_FACEBOOK);

    // Then
    assertThat(actualChannelEntity, notNullValue());
    assertThat(actualChannelEntity, equalTo(expectedChannelEntity));
  }
}
