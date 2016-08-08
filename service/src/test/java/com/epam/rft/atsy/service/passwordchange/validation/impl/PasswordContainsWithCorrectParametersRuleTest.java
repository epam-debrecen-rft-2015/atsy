package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordContainsValidationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class PasswordContainsWithCorrectParametersRuleTest {
  private final String newPassword;
  private final boolean expectedIsValid;
  private final PasswordContainsRule passwordContainsRule;

  public PasswordContainsWithCorrectParametersRuleTest(String newPassword, boolean expectedIsValid) {
    this.newPassword = newPassword;

    this.expectedIsValid = expectedIsValid;

    this.passwordContainsRule = new PasswordContainsRule();
  }

  @Parameterized.Parameters(name = "{index}: isValid(\"{0}\") should be {1}.")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {"a1b@", true},
        {"aAbB%1", true},
        {"1A@$2b3C!", true},
        {"-A1B2C812$3$", true}
    });
  }

  @Test
  public void validateWithCorrectParametersShouldNotThrowPasswordContainsValidationException()
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
