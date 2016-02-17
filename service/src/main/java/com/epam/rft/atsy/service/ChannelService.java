package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.ChannelDTO;

import java.util.Collection;

public interface ChannelService {

    Collection<ChannelDTO> getAllChannels();

    void saveOrUpdate(ChannelDTO channel);
}
