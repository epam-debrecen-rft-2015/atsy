package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;

import java.util.Collection;

/**
 * Created by Ikantik.
 */
public interface ChannelService {

    Collection<ChannelDTO> getAllChannels();

    void saveOrUpdate(ChannelDTO channel);
}
