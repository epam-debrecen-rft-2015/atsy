package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidator;
import com.epam.rft.atsy.service.exception.PasswordValidationException;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PasswordValidatorImpl implements PasswordValidator {
    private final List<PasswordValidationRule> passwordValidationRules;

    @Autowired
    public PasswordValidatorImpl(PasswordUniqueRule passwordUniqueRule) {
        passwordValidationRules = Lists.newArrayList();

        passwordValidationRules.add(new PasswordAllFieldFilledRule());
        passwordValidationRules.add(new PasswordLengthValidationRule());
        passwordValidationRules.add(new PasswordOldPasswordMatchesRule());
        passwordValidationRules.add(new PasswordContainsRule());
        passwordValidationRules.add(new PasswordNewMatchValidationRule());

        passwordValidationRules.add(passwordUniqueRule);
    }

    @Override
    public boolean validate(PasswordChangeDTO passwordChangeDTO) throws PasswordValidationException {
        for (PasswordValidationRule passwordValidationRule : passwordValidationRules) {
            if (!passwordValidationRule.isValid(passwordChangeDTO)) {
                throw new PasswordValidationException(passwordValidationRule.getErrorMessageKey());
            }
        }

        // Is this really necessary? At this point all rules have been satisfied therefore
        // the method might be void instead of returning such a non-informative value.
        return true;
    }
}
