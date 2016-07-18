package com.epam.rft.atsy.service.configuration;

import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidator;
import com.epam.rft.atsy.service.passwordchange.validation.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collection;

@Configuration public class PasswordValidatorConfiguration {
    @Bean public PasswordValidator passwordValidator() {
        Collection<PasswordValidationRule> rules = Arrays
            .asList(new PasswordAllFieldFilledRule(), new PasswordContainsRule(),
                new PasswordLengthValidationRule(), new PasswordNewMatchValidationRule(),
                new PasswordOldPasswordMatchesRule(), new PasswordUniqueRule());

        return new PasswordValidatorImpl(rules);
    }
}
