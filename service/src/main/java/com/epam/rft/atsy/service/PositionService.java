package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.exception.ObjectNotFoundException;

import java.util.Collection;
import java.util.List;

/**
 * Service that operates with positions in the database layer and in the view layer.
 */
public interface PositionService extends LogicallyDeletableService<PositionDTO> {

  /**
   * Returns the position with the specified id.
   *
   * @param ids the id of the searched position
   * @return the position
   */
  List<PositionDTO> getPositionsById(List<Long> ids);

  /**
   * Returns the position object with the given id.
   *
   * @param positionId the id of position
   * @return the position object
   */
  PositionDTO getPositionDtoById(Long positionId);

  /**
   * Returns the position object with the given name.
   *
   * @param positionName the name of the position
   * @return the position object
   */
  PositionDTO getPositionDtoByName(String positionName);

  /**
   * Saves a position to the database.
   *
   * @param position the position
   */
  void saveOrUpdate(PositionDTO position);
}
