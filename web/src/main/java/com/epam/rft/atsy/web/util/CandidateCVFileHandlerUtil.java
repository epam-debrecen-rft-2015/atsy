package com.epam.rft.atsy.web.util;


import com.epam.rft.atsy.service.domain.CandidateDTO;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class CandidateCVFileHandlerUtil {
  private static final String SEPARATOR = "-";

  private CandidateCVFileHandlerUtil() {}

  public static File createCVFileFromFolderLocationAndCandidateDtoAndCVFilename(String folderLocation, CandidateDTO candidateDTO, String cvFilename) {
    return new File(folderLocation + File.separator + getCandidateFolderNameFromCandidateDto(candidateDTO) + File.separator + cvFilename);
  }

  public static void createCandidateFolderOnFolderLocation(String folderLocation, CandidateDTO candidateDTO) throws IOException {
    String candidateFolderName = getCandidateFolderNameFromCandidateDto(candidateDTO);
    FileUtils.forceMkdir(new File(folderLocation + File.separator + candidateFolderName));
  }

  public static boolean existCandidateFolderOnFolderLocation(String folderLocation, CandidateDTO candidateDTO) throws IOException {
    File originalFolder = new File(folderLocation);
    if (!originalFolder.exists()) {
      throw new IOException();
    }
    String candidateFolderName = getCandidateFolderNameFromCandidateDto(candidateDTO);
    return new File(folderLocation + File.separator + candidateFolderName).exists();
  }

  private static String getCandidateFolderNameFromCandidateDto(CandidateDTO candidateDTO) {
    return candidateDTO.getId() + SEPARATOR + candidateDTO.getName();
  }
}
