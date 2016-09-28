package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.repositories.PositionRepository;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.exception.DuplicatePositionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PositionServiceImpl
    extends AbstractLogicallyDeletableService<PositionDTO, PositionEntity>
    implements PositionService {

  private PositionRepository positionRepository;

  @Autowired
  public PositionServiceImpl(PositionRepository positionRepository,
                             ConverterService converterService) {
    super(PositionDTO.class, positionRepository, converterService);
    this.positionRepository = positionRepository;
  }

  @Transactional(readOnly = true)
  @Override
  public List<PositionDTO> getPositionsById(List<Long> ids) {
    Assert.notNull(ids);

    List<PositionEntity> positionEntities = positionRepository.findAll(ids);
    List<PositionDTO> emptyList = Collections.emptyList();

    return positionEntities.isEmpty() ? emptyList
        : converterService.convert(positionEntities, PositionDTO.class);
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
  public Long saveOrUpdate(PositionDTO position) {
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

    return this.positionRepository.saveAndFlush(positionEntity).getId();
  }
}
