package com.epam.rft.atsy.persistence.dao.impl;

import com.epam.rft.atsy.persistence.dao.UserDAO;
import com.epam.rft.atsy.persistence.entities.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by mates on 10/22/2015.
 */
@Repository
public class UserDAOImpl extends GenericDAOImpl<UserEntity, Long> implements UserDAO{
    private static final String UNAME="userName";
    private static final String PWD="userPassword";

    public UserEntity login(String username, String password) {

        CriteriaBuilder criteriaBuilder=this.entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query=criteriaBuilder.createQuery(UserEntity.class);

        Root<UserEntity> userEntityRoot = query.from(UserEntity.class);
        query=query.where(criteriaBuilder.and(criteriaBuilder.equal(userEntityRoot.get(UNAME), username),
                criteriaBuilder.equal(userEntityRoot.get(PWD), password)));

        TypedQuery<UserEntity>userEntityTypedQuery= entityManager.createQuery(query);
        return userEntityTypedQuery.getSingleResult();

    }
}
