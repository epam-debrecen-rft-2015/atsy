package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.PasswordValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * Implementing class of the {@link PasswordValidator} interface. Doesn't provide additional
 * functionality.
 */
public class PasswordValidatorImpl implements PasswordValidator {

  private Collection<PasswordValidationRule> passwordValidationRules;

  /**
   * Creates a new PasswordValidatorImpl object with the given validation rules. After the object is
   * created, there is no way to change the applied validation rules.
   * @param passwordValidationRules contains all the validation rules to be applied when validating
   */
  @Autowired
  public PasswordValidatorImpl(Collection<PasswordValidationRule> passwordValidationRules) {
    this.passwordValidationRules = passwordValidationRules;
  }


  @Override
  public boolean validate(PasswordChangeDTO passwordChangeDTO) throws PasswordValidationException {
    for (PasswordValidationRule passwordValidationRule : passwordValidationRules) {
      if (!passwordValidationRule.isValid(passwordChangeDTO)) {
        throw new PasswordValidationException(passwordValidationRule.getErrorMessageKey());
      }
    }

    return true;
  }
}
