package com.epam.rft.atsy.web.handler;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import java.io.File;

public class FileHandlerTest extends AbstractHandlerTest {

  private FileHandler fileHandler = new FileHandler();

  @Test(expected = IllegalArgumentException.class)
  public void getFileByParentDirectoryPathAndFolderNameAndFilenameShouldThrowIllegalArgumentExceptionWhenParentDirectoryPathIsNull() {
    fileHandler
        .getFileByParentDirectoryPathAndFolderNameAndFilename(null, FOLDER_NAME_ACTUAL, FILENAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getFileByParentDirectoryPathAndFolderNameAndFilenameShouldThrowIllegalArgumentExceptionWhenFolderNameIsNull() {
    fileHandler
        .getFileByParentDirectoryPathAndFolderNameAndFilename(FOLDER_NAME_PARENT, null, FILENAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getFileByParentDirectoryPathAndFolderNameAndFilenameShouldThrowIllegalArgumentExceptionWhenFilenameIsNull() {
    fileHandler
        .getFileByParentDirectoryPathAndFolderNameAndFilename(FOLDER_NAME_PARENT,
            FOLDER_NAME_ACTUAL, null);
  }

  @Test
  public void getFileByParentDirectoryPathAndFolderNameAndFilenameShouldReturnExistingFileWhenAllParamIsValid() {
    File expectedFile =
        new File(
            FOLDER_NAME_PARENT + File.separator + FOLDER_NAME_ACTUAL + File.separator + FILENAME);

    File actualFile = fileHandler
        .getFileByParentDirectoryPathAndFolderNameAndFilename(FOLDER_NAME_PARENT,
            FOLDER_NAME_ACTUAL, FILENAME);

    assertThat(actualFile, equalTo(expectedFile));
  }
}