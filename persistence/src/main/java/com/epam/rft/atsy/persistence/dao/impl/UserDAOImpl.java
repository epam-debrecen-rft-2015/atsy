package com.epam.rft.atsy.persistence.dao.impl;

import com.epam.rft.atsy.persistence.dao.UserDAO;
import com.epam.rft.atsy.persistence.entities.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

/**
 * Created by mates on 10/22/2015.
 */
@Transactional(Transactional.TxType.REQUIRED)
@Repository
public class UserDAOImpl extends GenericDAOImpl<UserEntity, Long> implements UserDAO{
    /**
     * A constant value for username.
     */
    private static final String UNAME="userName";
    /**
     * A constant value for password.
     */
    private static final String PWD="userPassword";

    /**
     * A method for getting a UserEntity from the database.
     * Returns a UserEntity object represents the matching record of the database.
     * @param username is the name of the user
     * @param password is the password of the user
     * @return UserEntity object represents the matching record of the database
     */
    public UserEntity login(String username, String password) {

        CriteriaBuilder criteriaBuilder=this.entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query=criteriaBuilder.createQuery(UserEntity.class);

        Root<UserEntity> userEntityRoot = query.from(UserEntity.class);
        query=query.where(criteriaBuilder.and(criteriaBuilder.equal(userEntityRoot.get(UNAME), username),
                criteriaBuilder.equal(userEntityRoot.get(PWD), password)));

        TypedQuery<UserEntity> userEntityTypedQuery = entityManager.createQuery(query);
        return userEntityTypedQuery.getSingleResult();

    }
}
