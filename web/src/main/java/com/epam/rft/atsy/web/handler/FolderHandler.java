package com.epam.rft.atsy.web.handler;


import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;

@Component
public class FolderHandler {

  public void createFolderInParentDirectoryPath(String folderName, String parentDirectoryPath)
      throws IOException {
    Assert.notNull(parentDirectoryPath);
    Assert.notNull(folderName);
    FileUtils.forceMkdir(new File(parentDirectoryPath + File.separator + folderName));
  }

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