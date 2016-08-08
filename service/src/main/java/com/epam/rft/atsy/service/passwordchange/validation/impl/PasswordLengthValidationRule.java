package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordLengthValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;

public class PasswordLengthValidationRule implements PasswordValidationRule {
  private static final int PASSWORD_MIN_LENGTH = 6;

  @Override
  public void validate(PasswordChangeDTO passwordChangeDTO) throws PasswordLengthValidationException {
    if (!(passwordChangeDTO.getNewPassword().length() >= PASSWORD_MIN_LENGTH)) {
      throw new PasswordLengthValidationException();
    }
  }
}
