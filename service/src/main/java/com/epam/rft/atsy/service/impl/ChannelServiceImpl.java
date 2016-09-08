package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.persistence.repositories.ChannelRepository;
import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.exception.ChannelNotFoundException;
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
  public ChannelDTO getChannelDtoById(Long channelId) {
    Assert.notNull(channelId);
    ChannelEntity channelEntity = channelRepository.findOne(channelId);

    if (channelEntity != null) {
      return converterService.convert(channelEntity, ChannelDTO.class);
    }
    return null;
  }

  @Transactional(readOnly = true)
  @Override
  public Collection<ChannelDTO> getAllChannels() {
    List<ChannelEntity> ChannelEntities = channelRepository.findAll();
    return converterService.convert(ChannelEntities, ChannelDTO.class);
  }

  @Transactional(readOnly = true)
  @Override
  public ChannelDTO getChannelDtoByName(String channelName) {
    Assert.notNull(channelName);
    ChannelEntity channelEntity = channelRepository.findByName(channelName);

    if (channelEntity != null) {
      return converterService.convert(channelEntity, ChannelDTO.class);
    }
    return null;
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

  @Transactional
  @Override
  public void deleteChannelDtoLogicallyById(Long channelId) throws ChannelNotFoundException {
    Assert.notNull(channelId);

    ChannelEntity channelEntity = this.channelRepository.findOne(channelId);
    if (channelEntity == null) {
      throw new ChannelNotFoundException();
    }

    channelEntity.setDeleted(true);
    this.channelRepository.saveAndFlush(channelEntity);
  }
}
