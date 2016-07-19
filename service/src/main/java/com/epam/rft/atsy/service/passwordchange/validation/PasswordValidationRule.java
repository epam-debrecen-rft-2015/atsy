package com.epam.rft.atsy.service.passwordchange.validation;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;

public interface PasswordValidationRule {

  boolean isValid(PasswordChangeDTO passwordChangeDTO);

  /**
   * Gets the message key that corresponds to the rule violation.
   * @return the corresponding message key
   */
  String getErrorMessageKey();
}
