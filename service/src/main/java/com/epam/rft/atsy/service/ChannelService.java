package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.ChannelDTO;

import java.util.Collection;

/**
 * Service that operates with channels in the database layer and in the view layer.
 */
public interface ChannelService {

  /**
   * Returns a collection of channels.
   * @return the collection of channels
   */
  Collection<ChannelDTO> getAllChannels();

  /**
   * Saves a channel to the database.
   * @param channel the channel
   */
  void saveOrUpdate(ChannelDTO channel);
}
