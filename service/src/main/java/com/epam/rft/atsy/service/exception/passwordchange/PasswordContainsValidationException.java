package com.epam.rft.atsy.service.exception.passwordchange;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PasswordContainsValidationException extends PasswordValidationException {

  public PasswordContainsValidationException(String message) {
    super(message);
  }
}
