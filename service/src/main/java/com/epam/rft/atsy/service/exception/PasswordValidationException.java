package com.epam.rft.atsy.service.exception;

/**
 * Exception thrown when detecting a non valid password.
 */
public class PasswordValidationException extends Exception {
  private String messageKey;

  /**
   * Constructs a new instance of {@code PasswordValidationException}, with a specified message key.
   */
  public PasswordValidationException(String messageKey) {
    this.messageKey = messageKey;
  }

  /**
   * Returns the message key of the exception.
   * @return the message key
   */
  public String getMessageKey() {
    return messageKey;
  }

  /**
   * Returns the message of the exception.
   * @return the message
   */
  @Override
  public String getMessage() {
    return "Password validation failed. The message key: " + messageKey;
  }

}
