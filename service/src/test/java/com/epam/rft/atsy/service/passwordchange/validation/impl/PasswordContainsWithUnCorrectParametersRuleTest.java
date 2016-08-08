package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordContainsValidationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class PasswordContainsWithUnCorrectParametersRuleTest {
  private final String newPassword;
  private final PasswordContainsRule passwordContainsRule;

  public PasswordContainsWithUnCorrectParametersRuleTest(String newPassword) {
    this.newPassword = newPassword;

    this.passwordContainsRule = new PasswordContainsRule();
  }

  @Parameterized.Parameters(name = "{index}: isValid(\"{0}\") should be {1}.")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {"", false},
        {"abc", false},
        {"aBc", false},
        {"ABC", false},
        {"!%@", false},
        {"123", false},
        {"a!b%", false},
        {"A!b%", false},
        {"A!B%", false},
        {"a1b2", false},
        {"A1b2C3", false},
        {"A1B2C3", false},
        {"1%2@", false},
    });
  }

  @Test(expected = PasswordContainsValidationException.class)
  public void validateWithUnCorrectParametersShouldNotThrowPasswordContainsValidationException()
      throws PasswordContainsValidationException {
    // Given
    PasswordChangeDTO passwordChangeDTO = passwordChangeDTOFromPassword(newPassword);

    // When
    passwordContainsRule.validate(passwordChangeDTO);
  }


  private PasswordChangeDTO passwordChangeDTOFromPassword(String password) {
    // Only the newPassword field will be tested, therefore there's no
    // need to set the other fields.
    return PasswordChangeDTO.builder().newPassword(password).build();
  }

}
