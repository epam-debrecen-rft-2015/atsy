package com.epam.rft.atsy.persistence.dao;

import com.epam.rft.atsy.persistence.entities.UserEntity;

public interface UserDAO extends GenericDAO<UserEntity, Long>{
    /**
     * A method for getting a UserEntity from the database.
     * Returns a UserEntity object represents the matching record of the database.
     * @param username is the name of the user
     * @param password is the password of the user
     * @return UserEntity object represents the matching record of the database
     */
    UserEntity login(String username, String password);
}
