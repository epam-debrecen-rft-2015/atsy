package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordAllFieldFilledValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidator;

import org.apache.commons.lang3.StringUtils;

public class PasswordAllFieldFilledRule implements PasswordValidator {

  @Override
  public void validate(PasswordChangeDTO passwordChangeDTO) throws PasswordAllFieldFilledValidationException {
    String newPassword = passwordChangeDTO.getNewPassword();
    String oldPassword = passwordChangeDTO.getOldPassword();
    String newPasswordConfirm = passwordChangeDTO.getNewPasswordConfirm();

    if (!(StringUtils.isNotBlank(newPassword) && StringUtils.isNotBlank(oldPassword) && StringUtils
        .isNotBlank(newPasswordConfirm))) {
      throw new PasswordAllFieldFilledValidationException();
    }
  }
}
