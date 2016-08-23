package com.epam.rft.atsy.persistence.repositories;


import com.epam.rft.atsy.persistence.entities.ChannelEntity;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChannelRepositoryIT extends AbstractRepositoryIT {

  private static final String CHANNEL_NAME_EXISTING = "facebook";
  private static final String CHANNEL_NAME_NON_EXISTENT = "Olympiad";
  private static final String CHANNEL_NAME_EXISTING_UPPER_CASE = CHANNEL_NAME_EXISTING.toUpperCase();

  @Autowired
  private ChannelRepository channelRepository;


  @Test
  public void findByNameShouldReturnNullChannelWhenChannelNameNotExisting() {
    // Given

    // When
    ChannelEntity channelEntity = channelRepository.findByName(CHANNEL_NAME_NON_EXISTENT);

    // Then
    assertThat(channelEntity, nullValue());
  }

  @Test
  public void findByNameShouldReturnExistingChannelWhenUpperCaseChannelNameExisting() {
    // Given

    // When
    ChannelEntity channelEntity = channelRepository.findByName(CHANNEL_NAME_EXISTING_UPPER_CASE);

    // Then
    assertThat(channelEntity, notNullValue());
    assertThat(channelEntity.getName(), equalTo(CHANNEL_NAME_EXISTING));
  }

  @Test
  public void findByNameShouldReturnExistingChannelWhenChannelNameExisting() {
    // Given

    // When
    ChannelEntity channelEntity = channelRepository.findByName(CHANNEL_NAME_EXISTING);

    // Then
    assertThat(channelEntity, notNullValue());
    assertThat(channelEntity.getName(), equalTo(CHANNEL_NAME_EXISTING));
  }
}
