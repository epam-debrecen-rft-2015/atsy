package com.epam.rft.atsy.web.passwordchange.validation;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.web.exception.PasswordValidationException;

public interface PasswordValidator {
    boolean validate(PasswordChangeDTO passwordChangeDTO) throws PasswordValidationException;
}
