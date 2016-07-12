package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import org.springframework.stereotype.Component;

@Component
public class PasswordNewMatchValidationRule implements PasswordValidationRule {
    private static final String MESSAGE_KEY = "passwordchange.validation.newpasswordmatch";

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        return passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getNewPasswordConfirm());
    }

    @Override
    public String getErrorMessageKey() {
        return MESSAGE_KEY;
    }
}
