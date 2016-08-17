package com.epam.rft.atsy.service.passwordchange.validation;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;

/**
 * Represents a single password validation rule.
 */
public interface PasswordValidationRule {

  /**
   * Checks if the given object corresponds to a certain validation rule.
   * @param passwordChangeDTO the object to be validated
   * @return true if the given object satisfies the validation rule, false otherwise
   */
  boolean isValid(PasswordChangeDTO passwordChangeDTO);

  /**
   * Gets the message key that corresponds to the rule violation.
   * @return the corresponding message key
   */
  String getErrorMessageKey();
}
