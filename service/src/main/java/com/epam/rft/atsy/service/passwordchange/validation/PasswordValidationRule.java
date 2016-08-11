package com.epam.rft.atsy.service.passwordchange.validation;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordValidationException;

public interface PasswordValidationRule {

  void validate(PasswordChangeDTO passwordChangeDTO) throws PasswordValidationException;
}
