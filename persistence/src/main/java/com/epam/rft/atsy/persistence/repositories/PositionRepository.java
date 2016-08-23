package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository that allows operations with positions in database.
 */
public interface PositionRepository extends JpaRepository<PositionEntity, Long> {

  PositionEntity findByName(String positionName);
}
