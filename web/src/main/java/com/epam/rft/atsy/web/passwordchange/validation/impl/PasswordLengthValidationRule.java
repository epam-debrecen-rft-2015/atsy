package com.epam.rft.atsy.web.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.web.passwordchange.validation.PasswordValidationRule;

public class PasswordLengthValidationRule implements PasswordValidationRule {

    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final String MESSAGE_KEY="Az új jelszó hossza legalább 6 karakter kell legyen!";

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        return passwordChangeDTO.getNewPassword().length()>= PASSWORD_MIN_LENGTH;
    }

    @Override
    public String getErrorMessage() {
        return MESSAGE_KEY;
    }
}
