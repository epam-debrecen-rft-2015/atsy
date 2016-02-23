package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.states.StateEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApplicationRepository extends CrudRepository<StateEntity, Long> {

    List<StateEntity> findByCandidateId(Long candidateId);
}
