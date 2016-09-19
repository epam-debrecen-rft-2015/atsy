package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.ChannelDTO;

/**
 * Service that operates with channels in the database layer and in the view layer.
 */
public interface ChannelService extends LogicallyDeletableService<ChannelDTO> {

  /**
   * Returns the channel with the specified id.
   *
   * @param channelId the id of the searched channel
   * @return the channel
   */
  ChannelDTO getChannelDtoById(Long channelId);

  /**
   * Returns the channel with the specified name.
   *
   * @param channelName the name of the searched channel
   * @return the channel
   */
  ChannelDTO getChannelDtoByName(String channelName);
}
