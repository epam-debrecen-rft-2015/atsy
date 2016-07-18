package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

  @Test
  public void isValidShouldNotBeValidUnderMinPasswordLength() {
    //Given
    PasswordChangeDTO passwordChangeDtoUnderMinLength = PasswordChangeDTO.builder()
        .newPassword(PASSWORD_WITH_LENGTH_UNDER_MIN)
        .build();

    //When
    boolean result = rule.isValid(passwordChangeDtoUnderMinLength);

    //Then
    assertFalse(result);
  }

  @Test
  public void isValidShouldBeValidWithExactMinPasswordLength() {
    //Given
    PasswordChangeDTO passwordChangeDtoExactMinLength = PasswordChangeDTO.builder()
        .newPassword(PASSWORD_WITH_LENGTH_EQUAL_TO_MIN)
        .build();
    //When
    boolean result = rule.isValid(passwordChangeDtoExactMinLength);

    //Then
    assertTrue(result);
  }

  @Test
  public void isValidShouldBeValidOverMinPasswordLength() {
    //Given
    PasswordChangeDTO passwordChangeDtoOverMinLength = PasswordChangeDTO.builder()
        .newPassword(PASSWORD_WITH_LENGTH_OVER_MIN)
        .build();

    //When
    boolean result = rule.isValid(passwordChangeDtoOverMinLength);

    //Then
    assertTrue(result);
  }

}
