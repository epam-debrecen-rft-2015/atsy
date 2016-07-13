package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidator;
import com.epam.rft.atsy.service.exception.PasswordValidationException;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

public class PasswordValidatorImpl implements PasswordValidator {

    private Collection<PasswordValidationRule> passwordValidationRules;

    @Autowired
    public PasswordValidatorImpl(Collection<PasswordValidationRule> passwordValidationRules) {
        this.passwordValidationRules = passwordValidationRules;
    }


    @Override
    public boolean validate(PasswordChangeDTO passwordChangeDTO) throws PasswordValidationException {
        for (PasswordValidationRule passwordValidationRule : passwordValidationRules) {
            if (!passwordValidationRule.isValid(passwordChangeDTO)) {
                throw new PasswordValidationException(passwordValidationRule.getErrorMessageKey());
            }
        }

        return true;
    }
}
