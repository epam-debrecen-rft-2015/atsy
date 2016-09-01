package com.epam.rft.atsy.web.util;


import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;

@Component
public class CandidateCVFileHandler {

  public File createCVFileFromFolderLocationAndCandidateDtoAndCVFilename(String folderLocation, Long candidateId,
                                                                         String cvFilename) {
    Assert.notNull(folderLocation);
    Assert.notNull(candidateId);
    Assert.notNull(cvFilename);
    return new File(folderLocation + File.separator + candidateId + File.separator + cvFilename);
  }

  public void createCandidateFolderOnFolderLocation(String folderLocation, Long candidateId) throws IOException {
    Assert.notNull(folderLocation);
    Assert.notNull(candidateId);
    FileUtils.forceMkdir(new File(folderLocation + File.separator + candidateId));
  }

  public boolean existCandidateFolderOnFolderLocation(String folderLocation, Long candidateId) throws IOException {
    Assert.notNull(folderLocation);
    Assert.notNull(candidateId);
    File originalFolder = new File(folderLocation);
    if (!originalFolder.exists()) {
      throw new IOException();
    }
    return new File(folderLocation + File.separator + candidateId).exists();
  }
}
