package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidator;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class PasswordValidatorImpl implements PasswordValidator {

  private Collection<PasswordValidator> passwordValidators;

  @Autowired
  public PasswordValidatorImpl(Collection<PasswordValidator> passwordValidators) {
    this.passwordValidators = passwordValidators;
  }


  @Override
  public void validate(PasswordChangeDTO passwordChangeDTO) throws PasswordValidationException {
    for (PasswordValidator passwordValidator : passwordValidators) {
      passwordValidator.validate(passwordChangeDTO);
    }
  }
}
