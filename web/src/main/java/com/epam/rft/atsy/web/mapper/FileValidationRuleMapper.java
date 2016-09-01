package com.epam.rft.atsy.web.mapper;


import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class FileValidationRuleMapper implements ExceptionEnumMapper<FileValidationRule> {

  @Override
  public FileValidationRule mapByException(Exception e) throws IllegalArgumentException {
    Assert.notNull(e);
    Class exceptionClass = e.getClass();

    for (FileValidationRule fileUploadValidationRule : FileValidationRule.values()) {
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
