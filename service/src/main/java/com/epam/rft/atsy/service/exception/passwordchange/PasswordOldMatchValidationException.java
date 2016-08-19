package com.epam.rft.atsy.service.exception.passwordchange;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PasswordOldMatchValidationException extends PasswordValidationException {

  public PasswordOldMatchValidationException(String message) {
    super(message);
  }
}
