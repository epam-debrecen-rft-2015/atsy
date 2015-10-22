package com.epam.rft.atsy.persistence.dao;

import com.epam.rft.atsy.persistence.entities.UserEntity;

/**
 * Created by mates on 10/22/2015.
 */
public interface UserDAO extends GenericDAO<UserEntity, Long>{

    UserEntity login(String username, String password);
}
