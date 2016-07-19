package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.service.exception.UserNotFoundException;

/**
 * Service that operates with users in the database layer and in the view layer.
 */
public interface UserService {

  /**
   * Returns the user by his/her username.
   * @param username the user's username
   * @return the user
   */
  UserDTO findUserByName(String username);

  /**
   * Returns the user, if he/her exists in database.
   * @param user the given user
   * @return the existing user
   * @throws UserNotFoundException the user not found.
   */
  UserDTO login(UserDTO user) throws UserNotFoundException;

  /**
   * Saves the user to the database and returns his/her id.
   * @param userDTO the user
   * @return the id of user
   */
  Long saveOrUpdate(UserDTO userDTO);
}
