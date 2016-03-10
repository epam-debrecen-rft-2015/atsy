package com.epam.rft.atsy.web.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.web.passwordchange.validation.PasswordValidationRule;

public class PasswordOldPasswordMatchesRule implements PasswordValidationRule {

    public static final String MESSAGE_KEY="A régi jelszó nem egyezik!";

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        return false;
    }

    @Override
    public String getErrorMessage() {
        return MESSAGE_KEY;
    }
}
