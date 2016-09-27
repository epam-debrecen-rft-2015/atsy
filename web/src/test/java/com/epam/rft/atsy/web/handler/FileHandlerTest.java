package com.epam.rft.atsy.web.handler;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileHandlerTest extends AbstractHandlerTest {

  private FileHandler fileHandler = new FileHandler();

  @Test(expected = IllegalArgumentException.class)
  public void getFileByParentDirectoryPathAndFolderNameAndFilenameShouldThrowIllegalArgumentExceptionWhenParentDirectoryPathIsNull() {
    // Given

    // When
    fileHandler.getFileByParentDirectoryPathAndFolderNameAndFilename(null, FOLDER_NAME_ACTUAL, FILENAME);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void getFileByParentDirectoryPathAndFolderNameAndFilenameShouldThrowIllegalArgumentExceptionWhenFolderNameIsNull() {
    // Given

    // When
    fileHandler.getFileByParentDirectoryPathAndFolderNameAndFilename(FOLDER_NAME_PARENT, null, FILENAME);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void getFileByParentDirectoryPathAndFolderNameAndFilenameShouldThrowIllegalArgumentExceptionWhenFilenameIsNull() {
    // Given

    // When
    fileHandler.getFileByParentDirectoryPathAndFolderNameAndFilename(FOLDER_NAME_PARENT, FOLDER_NAME_ACTUAL, null);

    // Then
  }

  @Test
  public void getFileByParentDirectoryPathAndFolderNameAndFilenameShouldReturnExistingFileWhenAllParamIsValid() {
    // Given
    File expectedFile =
        new File(FOLDER_NAME_PARENT + File.separator + FOLDER_NAME_ACTUAL + File.separator + FILENAME);

    // When
    File actualFile = fileHandler
        .getFileByParentDirectoryPathAndFolderNameAndFilename(FOLDER_NAME_PARENT, FOLDER_NAME_ACTUAL, FILENAME);

    // Then
    assertThat(actualFile, equalTo(expectedFile));
  }
}