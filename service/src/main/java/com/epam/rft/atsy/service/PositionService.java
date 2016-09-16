package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.exception.PositionNotFoundException;

import java.util.Collection;
import java.util.List;

/**
 * Service that operates with positions in the database layer and in the view layer.
 */
public interface PositionService {

  /**
   * Returns the position with the specified id.
   * @param ids the id of the searched position
   * @return the position
   */
  List<PositionDTO> getPositionsById(List<Long> ids);

  /**
   * Returns the position object with the given id.
   * @param positionId the id of position
   * @return the position object
   */
  PositionDTO getPositionDtoById(Long positionId);

  /**
   * Returns the list of PositionDTOs where the deleted fields are null or false.
   * @return the list of PositionDTOs where the deleted fields are null or false
   */
  Collection<PositionDTO> getAllNonDeletedPositionDto();

  /**
   * Returns the position object with the given name.
   * @param positionName the name of the position
   * @return the position object
   */
  PositionDTO getPositionDtoByName(String positionName);

  /**
   * Saves a position to the database.
   * @param position the position
   */
  void saveOrUpdate(PositionDTO position);

  /**
   * Setups the deleted field to true in the position in the database.
   * @param positionId is the id of the position
   * @throws {@code PositionNotFoundException} If the position by this @{code positionId} not found
   */
  void deletePositionDtoLogicallyById(Long positionId) throws PositionNotFoundException;
}
