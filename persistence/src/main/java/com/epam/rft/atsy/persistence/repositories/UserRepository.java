package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUserNameAndUserPassword(String userName, String userPassword);

    UserEntity findByUserName(String userName);

}
