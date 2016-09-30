package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordAllFieldFilledValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import org.apache.commons.lang3.StringUtils;

/**
 * Represent the validation rule which demands all fields to be filled.
 */
public class PasswordAllFieldFilledRule implements PasswordValidationRule {

  /**
   * Checks whether the given object satisfies this validation rule.
   * @param passwordChangeDTO the object to be validated
   * @return true if every field of the given object is filled with data
   */
  @Override
  public void validate(PasswordChangeDTO passwordChangeDTO)
      throws PasswordAllFieldFilledValidationException {
    String newPassword = passwordChangeDTO.getNewPassword();
    String oldPassword = passwordChangeDTO.getOldPassword();
    String newPasswordConfirm = passwordChangeDTO.getNewPasswordConfirm();

    if (!(StringUtils.isNotBlank(newPassword) && StringUtils.isNotBlank(oldPassword) && StringUtils
        .isNotBlank(newPasswordConfirm))) {
      throw new PasswordAllFieldFilledValidationException();
    }
  }
}
