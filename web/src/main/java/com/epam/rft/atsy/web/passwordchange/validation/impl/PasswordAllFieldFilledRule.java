package com.epam.rft.atsy.web.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.web.passwordchange.validation.PasswordValidationRule;
import org.apache.commons.lang3.StringUtils;

public class PasswordAllFieldFilledRule implements PasswordValidationRule {

    public static final String MESSAGE_KEY="Az összes mező kitöltése kötelező!";

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        return StringUtils.isNotBlank(passwordChangeDTO.getNewPassword()) &&
                StringUtils.isNotBlank(passwordChangeDTO.getNewPasswordConfirm()) &&
                StringUtils.isNotBlank(passwordChangeDTO.getOldPassword());
    }

    @Override
    public String getErrorMessage() {
        return MESSAGE_KEY;
    }
}
