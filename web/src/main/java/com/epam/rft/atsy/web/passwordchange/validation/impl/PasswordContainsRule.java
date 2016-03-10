package com.epam.rft.atsy.web.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.web.passwordchange.validation.PasswordValidationRule;

public class PasswordContainsRule implements PasswordValidationRule {

    public static final String MESSAGE_KEY="Az új jelszónak tartalmaznia kell legalább 1 betűt, számot és speciális karaktert!";

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        return passwordChangeDTO.getNewPassword().matches("^(?=.{6,}$)(?=.[a-zA-Z])(?=.[0-9])(?=.[!@#$%^&_.,;:-]).*$");
    }

    @Override
    public String getErrorMessage() {
        return MESSAGE_KEY;
    }
}
