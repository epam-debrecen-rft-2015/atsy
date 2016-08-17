package com.epam.rft.atsy.service.exception.passwordchange;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PasswordAllFieldFilledValidationException extends PasswordValidationException {

  public PasswordAllFieldFilledValidationException(String message) {
    super(message);
  }
}
