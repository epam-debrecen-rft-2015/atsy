package com.epam.rft.atsy.persistence.dao.impl;

import com.epam.rft.atsy.persistence.dao.CandidateDAO;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional(Transactional.TxType.REQUIRED)
@Repository
public class CandidateDAOImpl extends GenericDAOImpl<CandidateEntity, Long> implements CandidateDAO {

}
