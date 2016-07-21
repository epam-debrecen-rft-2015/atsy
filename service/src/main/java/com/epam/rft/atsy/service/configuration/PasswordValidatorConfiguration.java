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
import com.epam.rft.atsy.service.security.SpringSecurityAuthenticationService;
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
  private SpringSecurityAuthenticationService springSecurityAuthenticationService;

  @Bean
  public PasswordValidator passwordValidator() {
    Collection<PasswordValidationRule> rules = Arrays.asList(
        new PasswordAllFieldFilledRule(), new PasswordContainsRule(),
        new PasswordLengthValidationRule(), new PasswordNewMatchValidationRule(),
        new PasswordOldPasswordMatchesRule(springSecurityAuthenticationService),
        new PasswordUniqueRule(passwordChangeService, springSecurityAuthenticationService)
    );

    return new PasswordValidatorImpl(rules);
  }
}
