package com.epam.rft.atsy.service.exception.passwordchange;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PasswordUniqueValidationException extends PasswordValidationException {

  public PasswordUniqueValidationException(String message) {
    super(message);
  }
}
