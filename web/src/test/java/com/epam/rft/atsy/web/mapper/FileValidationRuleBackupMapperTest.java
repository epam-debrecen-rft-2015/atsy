package com.epam.rft.atsy.web.mapper;


import com.epam.rft.atsy.service.exception.file.FileIsInWrongExtensionValidationException;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileValidationRuleBackupMapperTest {

  private static final Exception EXCEPTION = new Exception();
  private static final Exception FILE_IS_IN_WRONG_EXTENSION_VALIDATION_EXCEPTION =
      new FileIsInWrongExtensionValidationException();
  private static final FileValidationRule_Backup FILE_IS_IN_WRONG_EXTENSION_RULE =
      FileValidationRule_Backup.FILE_IS_IN_WRONG_EXTENSION_RULE;
  private static final String FILE_IS_IN_WRONG_EXTENSION_VALIDATION_MESSAGE_KEY = "file.is.in.wrong.extension";

  private FileValidationRuleMapper fileValidationRuleMapper;


  @Before
  public void setUp() {
    fileValidationRuleMapper = new FileValidationRuleMapper();
  }

  @Test(expected = IllegalArgumentException.class)
  public void mapByExceptionShouldThrowIllegalArgumentExceptionWhenExceptionIsNull() throws IllegalArgumentException {
    fileValidationRuleMapper.mapByException(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void mapByExceptionShouldThrowIllegalArgumentExceptionWhenExceptionIsNotValid()
      throws IllegalArgumentException {
    fileValidationRuleMapper.mapByException(EXCEPTION);
  }

  @Test
  public void mapByExceptionWhenExceptionIsValid() throws IllegalArgumentException {
    FileValidationRule_Backup fileValidationRuleBackup =
        fileValidationRuleMapper.mapByException(FILE_IS_IN_WRONG_EXTENSION_VALIDATION_EXCEPTION);

    assertThat(fileValidationRuleBackup, equalTo(FILE_IS_IN_WRONG_EXTENSION_RULE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getMessageKeyByExceptionShouldThrowIllegalArgumentExceptionWhenExceptionIsNull()
      throws IllegalArgumentException {
    fileValidationRuleMapper.getMessageKeyByException(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getMessageKeyByExceptionShouldThrowIllegalArgumentExceptionWhenExceptionIsNotValid()
      throws IllegalArgumentException {
    fileValidationRuleMapper.getMessageKeyByException(EXCEPTION);
  }

  @Test
  public void getMessageKeyByExceptionWhenExceptionIsValid() throws IllegalArgumentException {
    String messageKey =
        fileValidationRuleMapper.getMessageKeyByException(FILE_IS_IN_WRONG_EXTENSION_VALIDATION_EXCEPTION);

    assertThat(messageKey, equalTo(FILE_IS_IN_WRONG_EXTENSION_VALIDATION_MESSAGE_KEY));
  }

}
