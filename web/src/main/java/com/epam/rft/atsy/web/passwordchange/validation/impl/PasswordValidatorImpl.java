package com.epam.rft.atsy.web.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.web.exception.PasswordValidationException;
import com.epam.rft.atsy.web.passwordchange.validation.PasswordValidationRule;
import com.epam.rft.atsy.web.passwordchange.validation.PasswordValidator;
import com.google.common.collect.Lists;

import java.util.List;

public class PasswordValidatorImpl implements PasswordValidator{

    List<PasswordValidationRule> passwordValidationRules;

    public PasswordValidatorImpl() {
        passwordValidationRules = Lists.newArrayList();
        passwordValidationRules.add(new PasswordAllFieldFilledRule());
        passwordValidationRules.add(new PasswordLengthValidationRule());
        passwordValidationRules.add(new PasswordOldPasswordMatchesRule());
        passwordValidationRules.add(new PasswordContainsRule());
        passwordValidationRules.add(new PasswordNewMatchValidationRule());
    }

    @Override
    public boolean validate(PasswordChangeDTO passwordChangeDTO) throws PasswordValidationException {
        for (PasswordValidationRule passwordValidationRule : passwordValidationRules) {
            if(!passwordValidationRule.isValid(passwordChangeDTO))
                throw new PasswordValidationException(passwordValidationRule.getErrorMessage());
        }
        return true;
    }
}