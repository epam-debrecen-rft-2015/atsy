package com.epam.rft.atsy.persistence.dao.impl;

import com.epam.rft.atsy.persistence.dao.CandidateDAO;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

/**
 * Created by mates on 2015. 11. 11..
 */
@Transactional(Transactional.TxType.REQUIRED)
@Repository
public class CandidateDAOImpl extends GenericDAOImpl<CandidateEntity, Long> implements CandidateDAO {

}
