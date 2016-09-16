package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.LogicallyDeletableEntity;
import com.epam.rft.atsy.persistence.repositories.LogicallyDeletableRepository;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.LogicallyDeletableService;
import com.epam.rft.atsy.service.domain.LogicallyDeletableDTO;
import com.epam.rft.atsy.service.exception.ObjectNotFoundException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

public class LogicallyDeletableServiceImpl<D extends LogicallyDeletableDTO, E extends LogicallyDeletableEntity>
    implements LogicallyDeletableService<D> {

  private final Class<D> dtoTypeParameterClass;
  private LogicallyDeletableRepository logicallyDeletableRepository;
  protected ConverterService converterService;

  public LogicallyDeletableServiceImpl(Class<D> dtoTypeParameterClass,
                                       LogicallyDeletableRepository logicallyDeletableRepository,
                                       ConverterService converterService) {
    this.dtoTypeParameterClass = dtoTypeParameterClass;
    this.logicallyDeletableRepository = logicallyDeletableRepository;
    this.converterService = converterService;
  }

  @Transactional(readOnly = true)
  @Override
  public List<D> getAllNonDeletedDto() {
    List<D> deletableEntityList = this.logicallyDeletableRepository.findAllByDeletedFalse();
    return this.converterService.convert(deletableEntityList, dtoTypeParameterClass);
  }

  @Transactional
  @Override
  public void deleteDtoLogicallyById(Long id) throws ObjectNotFoundException {
    Assert.notNull(id);

    E entity = (E) this.logicallyDeletableRepository.findOne(id);
    if (entity == null) {
      throw new ObjectNotFoundException();
    }

    entity.setDeleted(true);
    this.logicallyDeletableRepository.saveAndFlush(entity);
  }
}
