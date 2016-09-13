package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.ChannelEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository that allows operations with channels in database.
 */
public interface ChannelRepository extends JpaRepository<ChannelEntity, Long> {

  /**
   * Returns the {@code ChannelEntity} that contains the {@code channelName}.
   *
   * @param channelName the name of the channel
   * @return the {@code ChannelEntity} that contains the {@code channelName}
   */
  ChannelEntity findByName(String channelName);

  /**
   * Returns the list of ChannelEntities where the deleted fields are null or false.
   *
   * @return the list of ChannelEntities where the deleted fields are null or false
   */
  List<ChannelEntity> findAllNonDeletedChannelEntity();
}
