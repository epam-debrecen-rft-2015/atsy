package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.StateFlowEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository that allows operations with the state flow entities in database.
 */
public interface StateFlowRepository extends JpaRepository<StateFlowEntity, Long> {

  /**
   * Returns the list of StateFlowEntities with the given FromStateEntity.
   * @param statesEntity the FromStateEntity
   * @return the list of StateFlowEntities with the given FromStateEntity
   */
  List<StateFlowEntity> findByFromStateEntity(StatesEntity statesEntity);
}
