package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.persistence.repositories.ChannelRepository;
import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.exception.DuplicateChannelException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChannelServiceImpl implements ChannelService {

  @Autowired
  private ConverterService converterService;

  @Autowired
  private ChannelRepository channelRepository;

  @Transactional(readOnly = true)
  @Override
  public Collection<ChannelDTO> getAllChannels() {
    List<ChannelEntity> ChannelEntities = channelRepository.findAll();
    return converterService.convert(ChannelEntities, ChannelDTO.class);
  }

  @Transactional
  @Override
  public void saveOrUpdate(ChannelDTO channel) {
    Assert.notNull(channel);
    Assert.notNull(channel.getName());

    ChannelEntity entity = converterService.convert(channel, ChannelEntity.class);
    try {
      channelRepository.saveAndFlush(entity);
    } catch (ConstraintViolationException | DataIntegrityViolationException ex) {
      log.error("Save to repository failed.", ex);

      String channelName = channel.getName();

      throw new DuplicateChannelException(channelName, ex);
    }
  }
}
