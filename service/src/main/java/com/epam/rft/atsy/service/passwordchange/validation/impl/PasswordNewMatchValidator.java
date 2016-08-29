package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordNewMatchValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import org.springframework.util.Assert;

/**
 * Represent the validation rule which demands the new password and the conformation of that
 * password to match with each other.
 */
public class PasswordNewMatchValidator implements PasswordValidationRule {

  /**
   * Checks whether the given object satisfies this validation rule.
   * @param passwordChangeDTO the object to be validated
   */
  @Override
  public void validate(PasswordChangeDTO passwordChangeDTO)
      throws PasswordNewMatchValidationException {
    Assert.notNull(passwordChangeDTO);
    Assert.notNull(passwordChangeDTO.getNewPassword());
    Assert.notNull(passwordChangeDTO.getNewPasswordConfirm());
    if (!(passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getNewPasswordConfirm()))) {
      throw new PasswordNewMatchValidationException();
    }
  }
}
