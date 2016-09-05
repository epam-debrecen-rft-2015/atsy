package com.epam.rft.atsy.web.mapper;


import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class PasswordValidationRuleMapper implements ExceptionEnumMapper<PasswordValidationRule_Backup> {

  @Override
  public PasswordValidationRule_Backup mapByException(Exception e) throws IllegalArgumentException {
    Assert.notNull(e);

    Class exceptionClass = e.getClass();
    for (PasswordValidationRule_Backup passwordValidationRuleBackup : PasswordValidationRule_Backup.values()) {
      if (passwordValidationRuleBackup.getExceptionClass().equals(exceptionClass)) {
        return passwordValidationRuleBackup;
      }
    }
    throw new IllegalArgumentException();
  }


  public String getMessageKeyByException(Exception e) throws IllegalArgumentException {
    return mapByException(e).getMessageKey();
  }
}
