package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.PositionEntity;

/**
 * Repository that allows operations with positions in database.
 */
public interface PositionRepository extends LogicallyDeletableRepository<PositionEntity, Long> {

  /**
   * Returns the {@code PositionEntity} that contains the {@code positionName}.
   *
   * @param positionName the name of the position
   * @return the {@code PositionEntity} that contains the {@code positionName}
   */
  PositionEntity findByName(String positionName);
}
