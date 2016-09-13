package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.persistence.repositories.ChannelRepository;
import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.exception.ChannelNotFoundException;
import com.epam.rft.atsy.service.exception.DuplicateChannelException;

import org.springframework.beans.factory.annotation.Autowired;
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
  public Collection<ChannelDTO> getAllNonDeletedChannelDto() {
    List<ChannelEntity> ChannelEntities = channelRepository.findAllNonDeletedChannelEntity();
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

    ChannelEntity channelEntity = this.channelRepository.findByName(channel.getName());

    if (channelEntity == null) {
      channelEntity = this.converterService.convert(channel, ChannelEntity.class);
    } else if (channelEntity.isDeleted() != null && channelEntity.isDeleted()) {
      channelEntity.setDeleted(false);
    } else {
      throw new DuplicateChannelException(channel.getName());
    }

    this.channelRepository.saveAndFlush(channelEntity);
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
