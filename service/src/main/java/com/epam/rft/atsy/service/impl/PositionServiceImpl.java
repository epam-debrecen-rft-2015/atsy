package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.repositories.PositionRepository;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.exception.DuplicatePositionException;
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
public class PositionServiceImpl implements PositionService {

  @Autowired
  private ConverterService converterService;

  @Autowired
  private PositionRepository positionRepository;

  @Transactional(readOnly = true)
  @Override
  public Collection<PositionDTO> getAllPositions() {
    List<PositionEntity> positionEntities = positionRepository.findAll();
    return converterService.convert(positionEntities, PositionDTO.class);
  }

  @Transactional
  @Override
  public void saveOrUpdate(PositionDTO position) {
    Assert.notNull(position);
    PositionEntity entity = converterService.convert(position, PositionEntity.class);
    try {
      positionRepository.saveAndFlush(entity);
    } catch (ConstraintViolationException | DataIntegrityViolationException ex) {
      log.error("Save to repository failed.", ex);

      String positionName = position.getName();

      throw new DuplicatePositionException(positionName, ex);
    }
  }
}
