package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository that allows operations with users in database.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  /**
   * Returns the user by username and password.
   * @param userName the user's name
   * @param userPassword the user's password
   * @return the user
   */
  UserEntity findByUserNameAndUserPassword(String userName, String userPassword);

  /**
   * Returns the user by username.
   * @param userName the user's name
   * @return the user
   */
  UserEntity findByUserName(String userName);

  String findCvPathByUserName(String userName);

}
