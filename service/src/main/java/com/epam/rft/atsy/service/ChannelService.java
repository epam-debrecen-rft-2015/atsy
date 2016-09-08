package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.exception.ChannelNotFoundException;

import java.util.Collection;

/**
 * Service that operates with channels in the database layer and in the view layer.
 */
public interface ChannelService {

  /**
   * Returns the channel with the specified id.
   *
   * @param channelId the id of the searched channel
   * @return the channel
   */
  ChannelDTO getChannelDtoById(Long channelId);

  /**
   * Returns a collection of channels.
   *
   * @return the collection of channels
   */
  Collection<ChannelDTO> getAllChannels();

  /**
   * Returns the channel with the specified name.
   *
   * @param channelName the name of the searched channel
   * @return the channel
   */
  ChannelDTO getChannelDtoByName(String channelName);

  /**
   * Saves a channel to the database.
   *
   * @param channel the channel
   */
  void saveOrUpdate(ChannelDTO channel);

  /**
   * Setups the deleted field to true in the channel in the database.
   *
   * @param channelId is the id of the channel
   */
  void deleteChannelDtoLogicallyById(Long channelId) throws ChannelNotFoundException;
}
