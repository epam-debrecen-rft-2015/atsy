package com.epam.rft.atsy.web.mapper;


import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class PasswordValidationRuleMapper implements ExceptionEnumMapper<PasswordValidationRule> {

  @Override
  public PasswordValidationRule mapByException(Exception e) throws IllegalArgumentException {
    Assert.notNull(e);

    Class exceptionClass = e.getClass();
    for (PasswordValidationRule passwordValidationRule : PasswordValidationRule.values()) {
      if (passwordValidationRule.getExceptionClass().equals(exceptionClass)) {
        return passwordValidationRule;
      }
    }
    throw new IllegalArgumentException();
  }


  public String getMessageKeyByException(Exception e) throws IllegalArgumentException {
    return mapByException(e).getMessageKey();
  }
}
