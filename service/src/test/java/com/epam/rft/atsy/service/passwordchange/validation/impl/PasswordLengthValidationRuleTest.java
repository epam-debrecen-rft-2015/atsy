package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordLengthValidationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PasswordLengthValidationRuleTest {

  private static final String PASSWORD_WITH_LENGTH_UNDER_MIN = "foo";
  private static final String PASSWORD_WITH_LENGTH_EQUAL_TO_MIN = "foobar";
  private static final String PASSWORD_WITH_LENGTH_OVER_MIN = "foobarbuz";

  private PasswordLengthValidationRule rule;

  @Before
  public void setUp() {
    rule = new PasswordLengthValidationRule();
  }

  @Test(expected = PasswordLengthValidationException.class)
  public void isValidShouldNotBeValidUnderMinPasswordLength() throws PasswordLengthValidationException {
    //Given
    PasswordChangeDTO passwordChangeDtoUnderMinLength = PasswordChangeDTO.builder()
        .newPassword(PASSWORD_WITH_LENGTH_UNDER_MIN)
        .build();

    //When
    rule.validate(passwordChangeDtoUnderMinLength);
  }

  @Test
  public void isValidShouldBeValidWithExactMinPasswordLength() throws PasswordLengthValidationException {
    //Given
    PasswordChangeDTO passwordChangeDtoExactMinLength = PasswordChangeDTO.builder()
        .newPassword(PASSWORD_WITH_LENGTH_EQUAL_TO_MIN)
        .build();

    //When
    rule.validate(passwordChangeDtoExactMinLength);
  }

  @Test
  public void isValidShouldBeValidOverMinPasswordLength() throws PasswordLengthValidationException {
    //Given
    PasswordChangeDTO passwordChangeDtoOverMinLength = PasswordChangeDTO.builder()
        .newPassword(PASSWORD_WITH_LENGTH_OVER_MIN)
        .build();

    //When
    rule.validate(passwordChangeDtoOverMinLength);
  }

}
