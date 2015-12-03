package com.epam.rft.atsy.persistence.dao;

import com.epam.rft.atsy.persistence.entities.states.StateEntity;

import java.util.Collection;

/**
 * Created by tothd on 2015. 12. 03..
 */
public interface ApplicationDAO extends GenericDAO<StateEntity, Long>{

    Collection<StateEntity> loadByCandidateId(Long candidateId);

}