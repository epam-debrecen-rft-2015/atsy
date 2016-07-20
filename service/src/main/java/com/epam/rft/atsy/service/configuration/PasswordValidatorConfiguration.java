package com.epam.rft.atsy.service.configuration;

import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidator;
import com.epam.rft.atsy.service.passwordchange.validation.impl.PasswordAllFieldFilledRule;
import com.epam.rft.atsy.service.passwordchange.validation.impl.PasswordContainsRule;
import com.epam.rft.atsy.service.passwordchange.validation.impl.PasswordLengthValidationRule;
import com.epam.rft.atsy.service.passwordchange.validation.impl.PasswordNewMatchValidationRule;
import com.epam.rft.atsy.service.passwordchange.validation.impl.PasswordOldPasswordMatchesRule;
import com.epam.rft.atsy.service.passwordchange.validation.impl.PasswordUniqueRule;
import com.epam.rft.atsy.service.passwordchange.validation.impl.PasswordValidatorImpl;
import com.epam.rft.atsy.service.security.CustomSecurityContextHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collection;

@Configuration
public class PasswordValidatorConfiguration {

  @Autowired
  private PasswordChangeService passwordChangeService;

  @Autowired
  private CustomSecurityContextHolderService customSecurityContextHolderService;

  @Bean
  public PasswordValidator passwordValidator() {
    Collection<PasswordValidationRule> rules = Arrays.asList(
        new PasswordAllFieldFilledRule(), new PasswordContainsRule(),
        new PasswordLengthValidationRule(), new PasswordNewMatchValidationRule(),
        new PasswordOldPasswordMatchesRule(customSecurityContextHolderService),
        new PasswordUniqueRule(passwordChangeService, customSecurityContextHolderService)
    );

    return new PasswordValidatorImpl(rules);
  }
}
