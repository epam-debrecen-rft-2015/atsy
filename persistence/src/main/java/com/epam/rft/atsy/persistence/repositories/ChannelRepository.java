package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository that allows operations with channels in database.
 */
public interface ChannelRepository extends JpaRepository<ChannelEntity, Long> {

  ChannelEntity findByName(String channelName);

  @Query
  List<ChannelEntity> findAll();
}
