package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.PositionEntity;
import org.springframework.data.repository.CrudRepository;

public interface PositionRepository extends CrudRepository<PositionEntity, Long> {
}
