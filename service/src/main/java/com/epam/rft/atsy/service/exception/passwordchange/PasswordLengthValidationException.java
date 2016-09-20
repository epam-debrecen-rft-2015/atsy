package com.epam.rft.atsy.service.exception.passwordchange;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PasswordLengthValidationException extends PasswordValidationException {

  public PasswordLengthValidationException(String message) {
    super(message);
  }
}
