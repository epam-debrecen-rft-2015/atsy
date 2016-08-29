package com.epam.rft.atsy.service.passwordchange.validation;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordValidationException;

/**
 * Represents a single password validation rule.
 */
public interface PasswordValidationRule {


  /**
   * Checks if the new password in the given DTO satisfies the current validation rule. If it does,
   * nothing happens, otherwise a {@link PasswordValidationException} is thrown.
   * @param passwordChangeDTO the DTO object which contains the password information
   * @throws PasswordValidationException when the password in the given object does not satisfy the
   * current validation rule.
   */
  void validate(PasswordChangeDTO passwordChangeDTO) throws PasswordValidationException;
}
