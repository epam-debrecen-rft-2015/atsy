package com.epam.rft.atsy.persistence.dao;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;

/**
 * Created by mates on 2015. 11. 11..
 */
public interface CandidateDAO extends GenericDAO<CandidateEntity, Long> {

    CandidateEntity load(Long id);
}
