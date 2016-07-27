package com.epam.rft.atsy.service.exception;

/**
 * Exception thrown when there is no logged in user,
 * but we try to get details about it.
 */
public class UserNotLoggedInException extends Exception {

  /**
   * Constructs a new instance of {@code UserNotLoggedInException}.
   */
  public UserNotLoggedInException() {
  }

  /**
   * Constructs a new instance of {@code UserNotLoggedInException}
   * with the specified message.
   * @param message the message shown when exception occurs
   */
  public UserNotLoggedInException(String message) {
    super(message);
  }
}
