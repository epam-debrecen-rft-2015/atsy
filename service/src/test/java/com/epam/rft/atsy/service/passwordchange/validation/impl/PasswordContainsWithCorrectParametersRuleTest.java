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
  private final PasswordContainsRule passwordContainsRule;

  public PasswordContainsWithCorrectParametersRuleTest(String newPassword) {
    this.newPassword = newPassword;
    this.passwordContainsRule = new PasswordContainsRule();
  }

  @Parameterized.Parameters(name = "{index}: isValid(\"{0}\").")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {"a1b@"},
        {"aAbB%1"},
        {"1A@$2b3C!"},
        {"-A1B2C812$3$"}
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
