package com.epam.rft.atsy.service.exception.passwordchange;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PasswordValidationException extends Exception {

  public PasswordValidationException(String message) {
    super(message);
  }
}
