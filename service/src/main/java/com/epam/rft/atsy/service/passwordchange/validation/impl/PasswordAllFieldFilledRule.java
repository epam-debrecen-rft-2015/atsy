package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import org.apache.commons.lang3.StringUtils;

public class PasswordAllFieldFilledRule implements PasswordValidationRule {
    private static final String MESSAGE_KEY = "passwordchange.validation.allfieldfilled";

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        String newPassword = passwordChangeDTO.getNewPassword();

        return StringUtils.isNotBlank(newPassword) &&
                StringUtils.isNotBlank(newPassword) &&
                StringUtils.isNotBlank(newPassword);
    }

    @Override
    public String getErrorMessageKey() {
        return MESSAGE_KEY;
    }
}
