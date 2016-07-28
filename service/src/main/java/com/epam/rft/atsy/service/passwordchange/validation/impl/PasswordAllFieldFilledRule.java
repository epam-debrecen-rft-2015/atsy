package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import org.apache.commons.lang3.StringUtils;

public class PasswordAllFieldFilledRule implements PasswordValidationRule {
  private static final String MESSAGE_KEY = "passwordchange.validation.allfieldfilled";

  @Override
  public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
    String newPassword = passwordChangeDTO.getNewPassword();
    String oldPassword = passwordChangeDTO.getOldPassword();
    String newPasswordConfirm = passwordChangeDTO.getNewPasswordConfirm();

    return StringUtils.isNotBlank(newPassword) &&
        StringUtils.isNotBlank(oldPassword) &&
        StringUtils.isNotBlank(newPasswordConfirm);
  }

  @Override
  public String getErrorMessageKey() {
    return MESSAGE_KEY;
  }
}
