package com.epam.rft.atsy.web.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.web.passwordchange.validation.PasswordValidationRule;

public class PasswordNewMatchValidationRule implements PasswordValidationRule {

    public static final String MESSAGE_KEY="A új jelszavak nem egyeznek!";

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        return passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getNewPasswordConfirm());
    }

    @Override
    public String getErrorMessage() {
        return MESSAGE_KEY;
    }
}
