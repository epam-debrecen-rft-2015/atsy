package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.states.StateEntity;

import java.util.Collection;

public interface ApplicationRepository extends BaseRepository<StateEntity,Long> {

    Collection<StateEntity> findByCandidateId(Long candidateId);
}
