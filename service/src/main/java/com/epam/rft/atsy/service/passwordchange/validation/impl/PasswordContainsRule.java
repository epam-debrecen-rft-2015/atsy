package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;

public class PasswordContainsRule implements PasswordValidationRule {

    public static final String MESSAGE_KEY="passwordchange.validation.contains";

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        return containsLetters(passwordChangeDTO.getNewPassword()) &&
                containsNumbers(passwordChangeDTO.getNewPassword()) &&
                containsSpecial(passwordChangeDTO.getNewPassword());
    }

    private boolean containsLetters(String password){
        return password.matches(".*[a-zA-Z]+.*");
    }

    private boolean containsNumbers(String password) {
        return password.matches(".*[0-9]+.*");
    }

    private boolean containsSpecial(String password) {
        return password.matches(".*[!@#$%^&_.,;:-]+.*");
    }

    @Override
    public String getErrorMessage() {
        return MESSAGE_KEY;
    }
}
