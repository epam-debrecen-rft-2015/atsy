package com.epam.rft.atsy.web.handler;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class FolderHandlerTest extends AbstractHandlerTest {

  private FolderHandler folderHandler = new FolderHandler();

  @Test(expected = IllegalArgumentException.class)
  public void createFolderInParentDirectoryPathShouldThrowIllegalArgumentExceptionWhenFolderNameIsNull()
      throws IOException {
    folderHandler.createFolderInParentDirectoryPath(null, FOLDER_NAME_PARENT);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createFolderInParentDirectoryPathShouldThrowIllegalArgumentExceptionWhenParentDirectoryPathIsNull()
      throws IOException {
    folderHandler.createFolderInParentDirectoryPath(FOLDER_NAME_ACTUAL, null);
  }

  @Test
  public void createFolderInParentDirectoryPathShouldCreateWhenAllParamIsValid()
      throws IOException {
    folderHandler.createFolderInParentDirectoryPath(FOLDER_NAME_ACTUAL, FOLDER_NAME_PARENT);
    String actualFolderPath = FOLDER_NAME_PARENT + File.separator + FOLDER_NAME_ACTUAL;
    File actualFile = new File(actualFolderPath);

    assertThat(actualFile, notNullValue());
    assertTrue(actualFile.exists());
    assertTrue(actualFile.isDirectory());
  }

  @Test(expected = IllegalArgumentException.class)
  public void existFolderNameInParentDirectoryPathShouldThrowIllegalArgumentExceptionWhenFolderNameIsNull()
      throws IOException {
    folderHandler.existFolderNameInParentDirectoryPath(null, FOLDER_NAME_PARENT);
  }

  @Test(expected = IllegalArgumentException.class)
  public void existFolderNameInParentDirectoryPathShouldThrowIllegalArgumentExceptionWhenParentDirectoryPathIsNull()
      throws IOException {
    folderHandler.existFolderNameInParentDirectoryPath(FOLDER_NAME_ACTUAL, null);
  }

  @Test(expected = IOException.class)
  public void existFolderNameInParentDirectoryPathShouldThrowIOExceptionWhenParentDirectoryPathNotExists()
      throws IOException {
    folderHandler.existFolderNameInParentDirectoryPath(FOLDER_NAME_ACTUAL, StringUtils.EMPTY);
  }

  @Test
  public void existFolderNameInParentDirectoryPathShouldReturnFalseWhenFolderNameNotExists()
      throws IOException {
    boolean actual =
        folderHandler
            .existFolderNameInParentDirectoryPath(FOLDER_NAME_NON_EXISTENT, FOLDER_NAME_PARENT);

    assertFalse(actual);
  }

  @Test
  public void existFolderNameInParentDirectoryPathShouldReturnTrueWhenCandidateFolderExists()
      throws IOException {
    boolean actual =
        folderHandler.existFolderNameInParentDirectoryPath(FOLDER_NAME_ACTUAL, FOLDER_NAME_PARENT);

    assertTrue(actual);
  }
}