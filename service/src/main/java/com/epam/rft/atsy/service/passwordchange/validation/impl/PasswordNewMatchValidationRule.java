package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import org.springframework.util.Assert;

/**
 * Represent the validation rule which demands the new password and the confirmation of the new
 * password to be the same.
 */
public class PasswordNewMatchValidationRule implements PasswordValidationRule {
  private static final String MESSAGE_KEY = "passwordchange.validation.newpasswordmatch";

  /**
   * Checks whether the given object satisfies this validation rule.
   * @param passwordChangeDTO the object to be validated
   * @return true if the new password and the confirmation of the new password are the same
   */
  @Override
  public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
    Assert.notNull(passwordChangeDTO);
    Assert.notNull(passwordChangeDTO.getNewPassword());
    Assert.notNull(passwordChangeDTO.getNewPasswordConfirm());
    return passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getNewPasswordConfirm());
  }

  @Override
  public String getErrorMessageKey() {
    return MESSAGE_KEY;
  }
}
