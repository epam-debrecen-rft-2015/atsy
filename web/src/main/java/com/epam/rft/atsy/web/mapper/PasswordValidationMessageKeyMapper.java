package com.epam.rft.atsy.web.mapper;


import com.epam.rft.atsy.web.validation.messagekey.PasswordValidationMessageKey;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class PasswordValidationMessageKeyMapper implements ExceptionEnumMapper<PasswordValidationMessageKey> {

  @Override
  public PasswordValidationMessageKey mapByException(Exception e) throws IllegalArgumentException {
    Assert.notNull(e);

    Class exceptionClass = e.getClass();
    for (PasswordValidationMessageKey passwordValidationMessageKey : PasswordValidationMessageKey.values()) {
      if (passwordValidationMessageKey.getExceptionClass().equals(exceptionClass)) {
        return passwordValidationMessageKey;
      }
    }
    throw new IllegalArgumentException();
  }

  @Override
  public String getValueNameByException(Exception e) throws IllegalArgumentException {
    return mapByException(e).getMessageKey();
  }
}
