package com.epam.rft.atsy.service.exception;

/**
 * Exception thrown when a user is not found in the database.
 */
public class UserNotFoundException extends Exception {

  /**
   * Constructs a new instance of {@code UserNotFoundException}.
   */
  public UserNotFoundException() {
  }

  /**
   * Constructs a new instance of {@code UserNotFoundException}, with a specified cause.
   * @param cause a {@code Throwable} object which contains the class and detail message of cause
   */
  public UserNotFoundException(Throwable cause) {
    super(cause);
  }
}
