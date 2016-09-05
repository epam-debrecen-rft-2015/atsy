package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.PositionDTO;

import java.util.Collection;

/**
 * Service that operates with positions in the database layer and in the view layer.
 */
public interface PositionService {

  PositionDTO getPositionDtoById(Long positionId);

  /**
   * Returns a collection of positions.
   * @return the collection of positions
   */
  Collection<PositionDTO> getAllPositions();

  PositionDTO getPositionDtoByName(String positionName);

  /**
   * Saves a position to the database.
   * @param position the position
   */
  void saveOrUpdate(PositionDTO position);
}
