package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordLengthValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;

/**
 * Represent the validation rule which demands the length of the new password to be longer than a
 * certain threshold.
 */
public class PasswordLengthValidator implements PasswordValidationRule {
  private static final int PASSWORD_MIN_LENGTH = 6;

  /**
   * Checks whether the given object satisfies this validation rule.
   * @param passwordChangeDTO the object to be validated
   */
  @Override
  public void validate(PasswordChangeDTO passwordChangeDTO)
      throws PasswordLengthValidationException {
    if (!(passwordChangeDTO.getNewPassword().length() >= PASSWORD_MIN_LENGTH)) {
      throw new PasswordLengthValidationException();
    }
  }
}
