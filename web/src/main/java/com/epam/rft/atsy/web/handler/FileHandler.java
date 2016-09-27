package com.epam.rft.atsy.web.handler;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;

/**
 * This class provides file handling operations.
 */
@Component
public class FileHandler {

  /**
   * Returns the file which locates in parentDirectoryPath/folderName/fileName.
   * If at least one of these parameters is null, then throws {@link IllegalArgumentException}
   * @param parentDirectoryPath the path without the folderName and fileName
   * @param folderName the name of the folder which contains the file
   * @param filename the name of the file
   * @return the file
   */
  public File getFileByParentDirectoryPathAndFolderNameAndFilename(String parentDirectoryPath,
                                                                   String folderName,
                                                                   String filename) {
    Assert.notNull(parentDirectoryPath);
    Assert.notNull(folderName);
    Assert.notNull(filename);
    return new File(parentDirectoryPath + File.separator + folderName + File.separator + filename);
  }
}