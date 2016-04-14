package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.StateEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StatesRepository extends CrudRepository<StateEntity, Long> {

    List<StateEntity> findByApplicationEntityOrderByStateIndexDesc(ApplicationEntity applicationEntity);

    StateEntity findTopByApplicationEntityOrderByStateIndexDesc(ApplicationEntity applicationEntity);
}
