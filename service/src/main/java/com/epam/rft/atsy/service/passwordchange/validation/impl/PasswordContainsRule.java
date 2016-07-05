package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;

public class PasswordContainsRule implements PasswordValidationRule {
    private static final String MESSAGE_KEY = "passwordchange.validation.contains";

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        String newPassword = passwordChangeDTO.getNewPassword();

        return containsLetters(newPassword) &&
                containsNumbers(newPassword) &&
                containsSpecial(newPassword);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorMessageKey() {
        return MESSAGE_KEY;
    }
}
