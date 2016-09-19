package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordNewMatchValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordValidationException;
import org.junit.Before;
import org.junit.Test;

public class PasswordNewMatchValidationRuleTest {

  private static final String NEW_PASSWORD = "New password";
  private static final String NEW_PASSWORD_CONFIRM_RIGHT = NEW_PASSWORD;
  private static final String NEW_PASSWORD_CONFIRM_NOT_RIGHT = "Not a new password";
  private static final String NEW_PASSWORD_CONFIRM_UPPER_CASE_NOT_RIGHT = "NEW PASSWORD";

  private PasswordNewMatchValidator passwordNewMatchValidationRule;

  @Before
  public void setUp() {
    passwordNewMatchValidationRule = new PasswordNewMatchValidator();
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidNewPasswordShouldThrowIllegalArgumentExceptionWhenParameterIsNull() throws
      PasswordNewMatchValidationException {
    PasswordChangeDTO passwordChangeDTO = null;
    passwordNewMatchValidationRule.validate(passwordChangeDTO);
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidNewPasswordShouldThrowIllegalArgumentExceptionWhenParameterNewPasswordFieldIsNull()
      throws PasswordNewMatchValidationException {
    PasswordChangeDTO passwordChangeDTO = getPasswordChangeDTO(null, NEW_PASSWORD_CONFIRM_RIGHT);
    passwordNewMatchValidationRule.validate(passwordChangeDTO);
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidNewPasswordShouldThrowIllegalArgumentExceptionWhenParameterNewPasswordConfirmFieldIsNull()
      throws
      PasswordNewMatchValidationException {
    PasswordChangeDTO passwordChangeDTO = getPasswordChangeDTO(NEW_PASSWORD, null);
    passwordNewMatchValidationRule.validate(passwordChangeDTO);
  }

  @Test
  public void isValidNewPasswordShouldBeRight() throws PasswordValidationException {
    PasswordChangeDTO
        passwordChangeDTO =
        getPasswordChangeDTO(NEW_PASSWORD, NEW_PASSWORD_CONFIRM_RIGHT);
    passwordNewMatchValidationRule.validate(passwordChangeDTO);
  }

  @Test(expected = PasswordNewMatchValidationException.class)
  public void isValidNewPasswordShouldBeNotRight() throws PasswordNewMatchValidationException {
    PasswordChangeDTO
        passwordChangeDTO =
        getPasswordChangeDTO(NEW_PASSWORD, NEW_PASSWORD_CONFIRM_NOT_RIGHT);
    passwordNewMatchValidationRule.validate(passwordChangeDTO);
  }

  @Test(expected = PasswordNewMatchValidationException.class)
  public void isValidNewPasswordShouldBeNotRightUpperCase()
      throws PasswordNewMatchValidationException {
    PasswordChangeDTO
        passwordChangeDTO =
        getPasswordChangeDTO(NEW_PASSWORD, NEW_PASSWORD_CONFIRM_UPPER_CASE_NOT_RIGHT);
    passwordNewMatchValidationRule.validate(passwordChangeDTO);
  }

  private PasswordChangeDTO getPasswordChangeDTO(String newPassword, String newPasswordConfirm) {
    return PasswordChangeDTO.builder().newPassword(newPassword)
        .newPasswordConfirm(newPasswordConfirm).build();
  }
}
