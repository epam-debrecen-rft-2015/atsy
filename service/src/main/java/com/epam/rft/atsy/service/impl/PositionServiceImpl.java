package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.repositories.PositionRepository;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PositionServiceImpl implements PositionService {

  private final static Type POSITIONDTO_LIST_TYPE = new TypeToken<List<PositionDTO>>() {
  }.getType();
  @Resource
  private ModelMapper modelMapper;
  @Autowired
  private PositionRepository positionRepository;

  @Override
  public Collection<PositionDTO> getAllPositions() {
    List<PositionEntity> positionEntities = positionRepository.findAll();
    return modelMapper.map(positionEntities, POSITIONDTO_LIST_TYPE);
  }

  @Override
  public void saveOrUpdate(PositionDTO position) {
    Assert.notNull(position);
    PositionEntity entity = modelMapper.map(position, PositionEntity.class);
    try {
      positionRepository.save(entity);
    } catch (ConstraintViolationException | DataIntegrityViolationException ex) {
      log.error("Save to repository failed.", ex);

      String positionName = position.getName();

      throw new DuplicateRecordException(positionName,
          "Duplication occurred when saving position: " + positionName, ex);
    }
  }

}
