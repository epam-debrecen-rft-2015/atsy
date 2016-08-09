package com.epam.rft.atsy.mapper;


import com.epam.rft.atsy.service.exception.passwordchange.PasswordLengthValidationException;
import com.epam.rft.atsy.web.mapper.ExceptionEnumMapper;
import com.epam.rft.atsy.web.mapper.PasswordValidationMessageKeyMapper;
import com.epam.rft.atsy.web.validation.messagekey.PasswordValidationMessageKey;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PasswordValidationMessageKeyMapperTest {

  private static final Exception EXCEPTION = new Exception();
  private static final Exception PASSWORD_LENGTH_VALIDATION_EXCEPTION = new PasswordLengthValidationException();
  private static final PasswordValidationMessageKey PASSWORD_VALIDATION_MESSAGE_KEY = PasswordValidationMessageKey.LENGTH_RULE;
  private static final String PASSWORD_LENGTH_VALIDATION_MESSAGE_KEY = "passwordchange.validation.length";

  private ExceptionEnumMapper<PasswordValidationMessageKey> passwordValidationMessageKeyExceptionEnumMapper;


  @Before
  public void setUp() {
    passwordValidationMessageKeyExceptionEnumMapper = new PasswordValidationMessageKeyMapper();
  }

  @Test(expected = IllegalArgumentException.class)
  public void mapByExceptionShouldThrowIllegalArgumentExceptionWhenExceptionIsNull() throws IllegalArgumentException {
    passwordValidationMessageKeyExceptionEnumMapper.mapByException(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void mapByExceptionWhenExceptionIsNotValid() throws IllegalArgumentException {
    passwordValidationMessageKeyExceptionEnumMapper.mapByException(EXCEPTION);
  }

  @Test
  public void mapByExceptionWhenExceptionIsValid() throws IllegalArgumentException {
    PasswordValidationMessageKey passwordValidationMessageKey =
        passwordValidationMessageKeyExceptionEnumMapper.mapByException(PASSWORD_LENGTH_VALIDATION_EXCEPTION);

    assertThat(passwordValidationMessageKey, equalTo(PASSWORD_VALIDATION_MESSAGE_KEY));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getValueNameByExceptionWhenExceptionIsNull() throws IllegalArgumentException {
    passwordValidationMessageKeyExceptionEnumMapper.getValueNameByException(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getValueNameByExceptionWhenExceptionIsNotValid() throws IllegalArgumentException {
    passwordValidationMessageKeyExceptionEnumMapper.getValueNameByException(EXCEPTION);
  }

  @Test
  public void getValueNameByExceptionWhenExceptionIsValid() throws IllegalArgumentException {
    String messageKey = passwordValidationMessageKeyExceptionEnumMapper.getValueNameByException(PASSWORD_LENGTH_VALIDATION_EXCEPTION);

    assertThat(messageKey, equalTo(PASSWORD_LENGTH_VALIDATION_MESSAGE_KEY));
  }


}
