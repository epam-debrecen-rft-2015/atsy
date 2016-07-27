package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.StateFlowEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateFlowRepository extends JpaRepository<StateFlowEntity, Long> {
  List<StateFlowEntity> findByFromStateEntity(StatesEntity statesEntity);
}
