package com.epam.rft.atsy.persistence.dao.impl;

import com.epam.rft.atsy.persistence.dao.ApplicationDAO;
import com.epam.rft.atsy.persistence.entities.states.NewStateEntity;
import com.epam.rft.atsy.persistence.entities.states.StateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public class ApplicationDAOImpl extends GenericDAOImpl<StateEntity, Long> implements ApplicationDAO {

    @PersistenceContext
    protected EntityManager entityManager;


    @Override
    @Transactional
    public Collection<StateEntity> loadByCandidateId(Long candidateId) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<NewStateEntity> cq = cb.createQuery(NewStateEntity.class);
        Root<NewStateEntity> rootEntry = cq.from(NewStateEntity.class);
        CriteriaQuery criteriaQuery=cq.where(cb.equal(rootEntry.get("candidateId"),candidateId));
        TypedQuery<StateEntity> typedQuery=entityManager.createQuery(criteriaQuery);

        return  typedQuery.getResultList();
    }


}
