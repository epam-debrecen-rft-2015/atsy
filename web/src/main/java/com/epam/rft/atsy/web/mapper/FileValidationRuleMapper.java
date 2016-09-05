package com.epam.rft.atsy.web.mapper;


import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class FileValidationRuleMapper implements ExceptionEnumMapper<FileValidationRule_Backup> {

  @Override
  public FileValidationRule_Backup mapByException(Exception e) throws IllegalArgumentException {
    Assert.notNull(e);
    Class exceptionClass = e.getClass();

    for (FileValidationRule_Backup fileUploadValidationRule : FileValidationRule_Backup.values()) {
      if (fileUploadValidationRule.getExceptionClass().equals(exceptionClass)) {
        return fileUploadValidationRule;
      }
    }
    throw new IllegalArgumentException();
  }

  public String getMessageKeyByException(Exception e) throws IllegalArgumentException {
    return mapByException(e).getMessageKey();
  }
}
