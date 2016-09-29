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
    // Given

    // When
    folderHandler.createFolderInParentDirectoryPath(null, FOLDER_NAME_PARENT);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void createFolderInParentDirectoryPathShouldThrowIllegalArgumentExceptionWhenParentDirectoryPathIsNull()
      throws IOException {
    // Given

    // When
    folderHandler.createFolderInParentDirectoryPath(FOLDER_NAME_ACTUAL, null);

    // Then
  }

  @Test
  public void createFolderInParentDirectoryPathShouldCreateWhenAllParamIsValid()
      throws IOException {
    // Given
    String actualFolderPath = FOLDER_NAME_PARENT + File.separator + FOLDER_NAME_ACTUAL;
    File actualFile = new File(actualFolderPath);

    // When
    folderHandler.createFolderInParentDirectoryPath(FOLDER_NAME_ACTUAL, FOLDER_NAME_PARENT);

    // Then
    assertThat(actualFile, notNullValue());
    assertTrue(actualFile.exists());
    assertTrue(actualFile.isDirectory());
  }

  @Test(expected = IllegalArgumentException.class)
  public void existFolderNameInParentDirectoryPathShouldThrowIllegalArgumentExceptionWhenFolderNameIsNull()
      throws IOException {
    // Given

    // When
    folderHandler.existFolderNameInParentDirectoryPath(null, FOLDER_NAME_PARENT);

    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void existFolderNameInParentDirectoryPathShouldThrowIllegalArgumentExceptionWhenParentDirectoryPathIsNull()
      throws IOException {
    // Given

    // When
    folderHandler.existFolderNameInParentDirectoryPath(FOLDER_NAME_ACTUAL, null);

    // Then
  }

  @Test(expected = IOException.class)
  public void existFolderNameInParentDirectoryPathShouldThrowIOExceptionWhenParentDirectoryPathNotExists()
      throws IOException {
    // Given

    // When
    folderHandler.existFolderNameInParentDirectoryPath(FOLDER_NAME_ACTUAL, StringUtils.EMPTY);

    // Then
  }

  @Test
  public void existFolderNameInParentDirectoryPathShouldReturnFalseWhenFolderNameNotExists()
      throws IOException {
    // Given

    // When
    boolean actual = folderHandler.existFolderNameInParentDirectoryPath(FOLDER_NAME_NON_EXISTENT, FOLDER_NAME_PARENT);

    // Then
    assertFalse(actual);
  }

  @Test
  public void existFolderNameInParentDirectoryPathShouldReturnTrueWhenCandidateFolderExists()
      throws IOException {
    // Given

    // When
    boolean actual = folderHandler.existFolderNameInParentDirectoryPath(FOLDER_NAME_ACTUAL, FOLDER_NAME_PARENT);

    // Then
    assertTrue(actual);
  }
}