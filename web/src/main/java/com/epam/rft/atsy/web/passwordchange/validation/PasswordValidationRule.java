package com.epam.rft.atsy.web.passwordchange.validation;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;

public interface PasswordValidationRule {

    boolean isValid(PasswordChangeDTO passwordChangeDTO);
    String getErrorMessage();
}
