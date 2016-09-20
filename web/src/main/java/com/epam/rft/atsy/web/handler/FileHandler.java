package com.epam.rft.atsy.web.handler;


import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;

@Component
public class FileHandler {

  public File getFileByParentDirectoryPathAndFolderNameAndFilename(String parentDirectoryPath,
                                                                   String folderName,
                                                                   String filename) {
    Assert.notNull(parentDirectoryPath);
    Assert.notNull(folderName);
    Assert.notNull(filename);
    return new File(parentDirectoryPath + File.separator + folderName + File.separator + filename);
  }
}