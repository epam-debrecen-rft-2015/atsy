package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.StateFlowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateFlowRepository extends JpaRepository<StateFlowEntity, Long> {
}
