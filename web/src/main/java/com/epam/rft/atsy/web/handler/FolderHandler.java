package com.epam.rft.atsy.web.handler;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;

/**
 * This class provides folder handling operations.
 */
@Component
public class FolderHandler {

  /**
   * Create folder in parentDirectory.
   * @param folderName          the name of the folder
   * @param parentDirectoryPath the path of the parentDirectory
   * @throws IOException if the directory cannot be created or the file already exists but is not a directory
   */
  public void createFolderInParentDirectoryPath(String folderName, String parentDirectoryPath)
      throws IOException {
    Assert.notNull(parentDirectoryPath);
    Assert.notNull(folderName);
    FileUtils.forceMkdir(new File(parentDirectoryPath + File.separator + folderName));
  }

  /**
   * Returns true if the folder exists, otherwise false.
   *
   * @param folderName          the name of the folder
   * @param parentDirectoryPath the path of the parentDirectory
   * @return true if the folder exists, otherwise false.
   * @throws IOException when the parentDirectory does not exist.
   */
  public boolean existFolderNameInParentDirectoryPath(String folderName, String parentDirectoryPath)
      throws IOException {
    Assert.notNull(parentDirectoryPath);
    Assert.notNull(folderName);
    File parentDirectory = new File(parentDirectoryPath);
    if (!parentDirectory.exists()) {
      throw new IOException();
    }
    return new File(parentDirectoryPath + File.separator + folderName).exists();
  }
}