package com.epam.rft.atsy.service.exception.passwordchange;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PasswordNewMatchValidationException extends PasswordValidationException {

  public PasswordNewMatchValidationException(String message) {
    super(message);
  }
}
