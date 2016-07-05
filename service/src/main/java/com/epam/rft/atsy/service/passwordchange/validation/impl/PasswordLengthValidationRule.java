package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;

public class PasswordLengthValidationRule implements PasswordValidationRule {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final String MESSAGE_KEY = "passwordchange.validation.length";

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        return passwordChangeDTO.getNewPassword().length() >= PASSWORD_MIN_LENGTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorMessageKey() {
        return MESSAGE_KEY;
    }
}
