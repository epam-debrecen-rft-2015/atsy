package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.repositories.PositionRepository;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.exception.ChannelNotFoundException;
import com.epam.rft.atsy.service.exception.DuplicateChannelException;
import com.epam.rft.atsy.service.exception.DuplicatePositionException;
import com.epam.rft.atsy.service.exception.PositionNotFoundException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PositionServiceImpl implements PositionService {

  @Autowired
  private ConverterService converterService;

  @Autowired
  private PositionRepository positionRepository;

  @Transactional(readOnly = true)
  @Override
  public List<PositionDTO> getPositionsById(List<Long> ids) {
    Assert.notNull(ids);

    List<PositionEntity> positionEntities = positionRepository.findAll(ids);
    List<PositionDTO> emptyList = Collections.emptyList();

    return positionEntities.isEmpty() ? emptyList : converterService.convert(positionEntities, PositionDTO.class);
  }

  @Transactional(readOnly = true)
  @Override
  public PositionDTO getPositionDtoById(Long positionId) {
    Assert.notNull(positionId);
    PositionEntity positionEntity = positionRepository.findOne(positionId);

    if (positionEntity != null) {
      return converterService.convert(positionEntity, PositionDTO.class);
    }
    return null;
  }

  @Transactional(readOnly = true)
  @Override
  public Collection<PositionDTO> getAllNonDeletedPositionDto() {
    List<PositionEntity> positionEntities = positionRepository.findAllNonDeletedPositionEntity();
    return converterService.convert(positionEntities, PositionDTO.class);
  }

  @Transactional(readOnly = true)
  @Override
  public PositionDTO getPositionDtoByName(String positionName) {
    Assert.notNull(positionName);
    PositionEntity positionEntity = positionRepository.findByName(positionName);

    if (positionEntity != null) {
      return converterService.convert(positionEntity, PositionDTO.class);
    }
    return null;
  }

  @Transactional
  @Override
  public void saveOrUpdate(PositionDTO position) {
    Assert.notNull(position);
    Assert.notNull(position.getName());

    PositionEntity positionEntity = this.positionRepository.findByName(position.getName());

    if (positionEntity == null) {
      positionEntity = this.converterService.convert(position, PositionEntity.class);
    } else if (positionEntity.isDeleted() != null && positionEntity.isDeleted()) {
      positionEntity.setDeleted(false);
    } else {
      throw new DuplicatePositionException(position.getName());
    }

    this.positionRepository.saveAndFlush(positionEntity);
  }

  @Transactional
  @Override
  public void deletePositionDtoLogicallyById(Long positionId) throws PositionNotFoundException {
    Assert.notNull(positionId);

    PositionEntity positionEntity = this.positionRepository.findOne(positionId);
    if (positionEntity == null) {
      throw new PositionNotFoundException();
    }

    positionEntity.setDeleted(true);
    this.positionRepository.saveAndFlush(positionEntity);
  }
}