package com.epam.rft.atsy.web.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.web.passwordchange.validation.PasswordValidationRule;

public class PasswordAllFieldFilledRule implements PasswordValidationRule {

    public static final String MESSAGE_KEY="Az összes mező kitöltése kötelező!";

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        if(!passwordChangeDTO.getNewPassword().isEmpty() &&
           !passwordChangeDTO.getNewPasswordConfirm().isEmpty() &&
           !passwordChangeDTO.getOldPassword().isEmpty())
            return true;
        return false;
    }

    @Override
    public String getErrorMessage() {
        return MESSAGE_KEY;
    }
}
