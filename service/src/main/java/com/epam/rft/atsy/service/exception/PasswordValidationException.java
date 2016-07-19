package com.epam.rft.atsy.service.exception;

public class PasswordValidationException extends Exception {
  private String messageKey;

  public PasswordValidationException(String messageKey) {
    this.messageKey = messageKey;
  }

  public String getMessageKey() {
    return messageKey;
  }

  @Override
  public String getMessage() {
    return "Password validation failed. The message key: " + messageKey;
  }

}
