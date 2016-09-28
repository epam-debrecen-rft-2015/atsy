package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.LogicallyDeletableDTO;
import com.epam.rft.atsy.service.exception.ObjectNotFoundException;

import java.util.List;

/**
 * Service that allows operations with logically deletable DTOs.
 * @param <D> the type of the DTO
 */
public interface LogicallyDeletableService<D extends LogicallyDeletableDTO> {

  /**
   * Returns the list of D where the deleted field is false.
   * @return the list of D where the deleted field is false
   */
  List<D> getAllNonDeletedDto();

  /**
   * Saves a object to the database.
   * @param dto the dto
   * @return the id of the saved entity
   */
  Long saveOrUpdate(D dto);

  /**
   * Setups the deleted field to true in the database.
   * @param id the id of the entity
   * @throws {@code ObjectNotFoundException} If the entity by this @{code id} not found
   */
  void deleteDtoLogicallyById(Long id) throws ObjectNotFoundException;
}
