package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordNewMatchValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidator;

import org.springframework.util.Assert;

public class PasswordNewMatchValidator implements PasswordValidator {

  @Override
  public void validate(PasswordChangeDTO passwordChangeDTO) throws PasswordNewMatchValidationException {
    Assert.notNull(passwordChangeDTO);
    Assert.notNull(passwordChangeDTO.getNewPassword());
    Assert.notNull(passwordChangeDTO.getNewPasswordConfirm());
    if (!(passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getNewPasswordConfirm()))) {
      throw new PasswordNewMatchValidationException();
    }
  }
}
