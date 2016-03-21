package com.epam.rft.atsy.web.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.web.passwordchange.validation.PasswordValidationRule;

public class PasswordContainsRule implements PasswordValidationRule {

    public static final String MESSAGE_KEY="Az új jelszónak tartalmaznia kell legalább 1 betűt, számot és speciális karaktert!";

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        return containsLetters(passwordChangeDTO) && containsNumbers(passwordChangeDTO) && containsSpecial(passwordChangeDTO);
    }

    private boolean containsLetters(PasswordChangeDTO passwordChangeDTO){
        return passwordChangeDTO.getNewPassword().matches(".*[a-zA-Z]+.*");
    }

    private boolean containsNumbers(PasswordChangeDTO passwordChangeDTO) {
        return passwordChangeDTO.getNewPassword().matches(".*[0-9]+.*");
    }

    private boolean containsSpecial(PasswordChangeDTO passwordChangeDTO) {
        return passwordChangeDTO.getNewPassword().matches(".*[!@#$%^&_.,;:-]+.*");
    }

    @Override
    public String getErrorMessage() {
        return MESSAGE_KEY;
    }
}
