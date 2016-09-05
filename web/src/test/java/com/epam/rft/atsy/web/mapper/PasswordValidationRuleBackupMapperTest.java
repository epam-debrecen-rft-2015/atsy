package com.epam.rft.atsy.web.mapper;


import com.epam.rft.atsy.service.exception.passwordchange.PasswordLengthValidationException;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PasswordValidationRuleBackupMapperTest {

  private static final Exception EXCEPTION = new Exception();
  private static final Exception PASSWORD_LENGTH_VALIDATION_EXCEPTION = new PasswordLengthValidationException();
  private static final PasswordValidationRule_Backup
      PASSWORD_LENGTH_VALIDATION_RULE = PasswordValidationRule_Backup.LENGTH_RULE;
  private static final String PASSWORD_LENGTH_VALIDATION_MESSAGE_KEY = "passwordchange.validation.length";

  private PasswordValidationRuleMapper passwordValidationRuleExceptionEnumMapper;


  @Before
  public void setUp() {
    passwordValidationRuleExceptionEnumMapper = new PasswordValidationRuleMapper();
  }

  @Test(expected = IllegalArgumentException.class)
  public void mapByExceptionShouldThrowIllegalArgumentExceptionWhenExceptionIsNull() throws IllegalArgumentException {
    passwordValidationRuleExceptionEnumMapper.mapByException(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void mapByExceptionShouldThrowIllegalArgumentExceptionWhenExceptionIsNotValid()
      throws IllegalArgumentException {
    passwordValidationRuleExceptionEnumMapper.mapByException(EXCEPTION);
  }

  @Test
  public void mapByExceptionWhenExceptionIsValid() throws IllegalArgumentException {
    PasswordValidationRule_Backup passwordValidationRuleBackup =
        passwordValidationRuleExceptionEnumMapper.mapByException(PASSWORD_LENGTH_VALIDATION_EXCEPTION);

    assertThat(passwordValidationRuleBackup, equalTo(PASSWORD_LENGTH_VALIDATION_RULE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getMessageKeyByExceptionShouldThrowIllegalArgumentExceptionWhenExceptionIsNull()
      throws IllegalArgumentException {
    passwordValidationRuleExceptionEnumMapper.getMessageKeyByException(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getMessageKeyByExceptionShouldThrowIllegalArgumentExceptionWhenExceptionIsNotValid()
      throws IllegalArgumentException {
    passwordValidationRuleExceptionEnumMapper.getMessageKeyByException(EXCEPTION);
  }

  @Test
  public void getMessageKeyByExceptionWhenExceptionIsValid() throws IllegalArgumentException {
    String messageKey =
        passwordValidationRuleExceptionEnumMapper.getMessageKeyByException(PASSWORD_LENGTH_VALIDATION_EXCEPTION);

    assertThat(messageKey, equalTo(PASSWORD_LENGTH_VALIDATION_MESSAGE_KEY));
  }


}
