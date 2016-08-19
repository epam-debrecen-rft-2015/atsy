package com.epam.rft.atsy.service.passwordchange.validation;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordValidationException;

/**
 * A functional interface which is used to do the validation of the user's password.
 */
public interface PasswordValidator {

  /**
   * Validates the given DTO object based on the previously applied validation rules. See: {@link
   * PasswordValidationRule}.
   * @param passwordChangeDTO the object to be validated
   * @throws PasswordValidationException if the given DTO object is not valid according to the
   * current validation rules
   */
  void validate(PasswordChangeDTO passwordChangeDTO) throws PasswordValidationException;
}
