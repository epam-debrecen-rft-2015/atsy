package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.ChannelEntity;

/**
 * Repository that allows operations with channels in database.
 */
public interface ChannelRepository extends LogicallyDeletableRepository<ChannelEntity, Long> {

  /**
   * Returns the {@code ChannelEntity} that contains the {@code channelName}.
   *
   * @param channelName the name of the channel
   * @return the {@code ChannelEntity} that contains the {@code channelName}
   */
  ChannelEntity findByName(String channelName);
}
