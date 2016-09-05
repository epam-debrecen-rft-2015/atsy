package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordAllFieldFilledValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PasswordAllFieldFilledRuleForValidInputsTest {

  public static final String NOT_BLANK_PASSWORD = "baz";

  private PasswordAllFieldFilledRule rule;

  @Before
  public void setUp() {
    rule = new PasswordAllFieldFilledRule();
  }

  @Test
  public void validateShouldNotThrowPasswordAllFieldFilledValidationExceptionWhenAllFieldsFilled()
      throws PasswordAllFieldFilledValidationException {
    //Given
    PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder()
        .newPassword(NOT_BLANK_PASSWORD)
        .newPasswordConfirm(NOT_BLANK_PASSWORD)
        .oldPassword(NOT_BLANK_PASSWORD)
        .build();

    //When
    rule.validate(passwordChangeDTO);
  }
}
