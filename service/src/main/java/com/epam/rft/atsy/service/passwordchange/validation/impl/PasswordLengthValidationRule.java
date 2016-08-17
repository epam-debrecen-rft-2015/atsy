package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;

/**
 * Represent the validation rule which demands the length of the new password to be longer than a
 * certain threshold.
 */
public class PasswordLengthValidationRule implements PasswordValidationRule {
  private static final int PASSWORD_MIN_LENGTH = 6;
  private static final String MESSAGE_KEY = "passwordchange.validation.length";

  /**
   * Checks whether the given object satisfies this validation rule.
   * @param passwordChangeDTO the object to be validated
   * @return true if the length of the new password is greater than a certain threshold
   */
  @Override
  public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
    return passwordChangeDTO.getNewPassword().length() >= PASSWORD_MIN_LENGTH;
  }

  @Override
  public String getErrorMessageKey() {
    return MESSAGE_KEY;
  }
}
