package com.epam.rft.atsy.util;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.util.CandidateCVFileHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class CandidateCVFileHandlerTest {

  private static final long CANDIDATE_ID = 1L;
  private static final String CANDIDATE_NAME = "Candidate A";
  private static final String CANDIDATE_NAME_WITHOUT_FOLDER = "Candidate X";
  private static final String CANDIDATE_FOLDER_NAME =
      CANDIDATE_ID + CandidateCVFileHandler.SEPARATOR + CANDIDATE_NAME;
  private static final String TEST_FOLDER_LOCATION = "folder";
  private static final String CV_FILENAME = "hungry_cat.pdf";
  private static final File TEST_FOLDER = new File(TEST_FOLDER_LOCATION);
  private static final File
      CANDIDATE_FOLDER =
      new File(TEST_FOLDER_LOCATION + File.separator + CANDIDATE_FOLDER_NAME);

  private CandidateDTO candidateDTOWithNullId =
      CandidateDTO.builder().id(null).name(CANDIDATE_NAME).build();
  private CandidateDTO candidateDTOWithNullName =
      CandidateDTO.builder().id(CANDIDATE_ID).name(null).build();
  private CandidateDTO candidateDTOWithoutFolder =
      CandidateDTO.builder().id(CANDIDATE_ID).name(CANDIDATE_NAME_WITHOUT_FOLDER).build();
  private CandidateDTO candidateDTOValid =
      CandidateDTO.builder().id(CANDIDATE_ID).name(CANDIDATE_NAME).build();

  private CandidateCVFileHandler candidateCVFileHandler = new CandidateCVFileHandler();

  @Before
  public void setup() throws IOException {
    FileUtils.forceMkdir(TEST_FOLDER);
    FileUtils.forceMkdir(CANDIDATE_FOLDER);
  }

  @After
  public void teardown() throws IOException {
    FileUtils.forceDelete(TEST_FOLDER);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createCVFileFromFolderLocationAndCandidateDtoAndCVFilenameShouldThrowIllegalArgumentExceptionWhenFolderLocationIsNull() {
    candidateCVFileHandler
        .createCVFileFromFolderLocationAndCandidateDtoAndCVFilename(null, candidateDTOValid,
            CV_FILENAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createCVFileFromFolderLocationAndCandidateDtoAndCVFilenameShouldThrowIllegalArgumentExceptionWhenCandidateDtoIsNull() {
    candidateCVFileHandler
        .createCVFileFromFolderLocationAndCandidateDtoAndCVFilename(TEST_FOLDER_LOCATION, null,
            CV_FILENAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createCVFileFromFolderLocationAndCandidateDtoAndCVFilenameShouldThrowIllegalArgumentExceptionWhenCVFilenameIsNull() {
    candidateCVFileHandler
        .createCVFileFromFolderLocationAndCandidateDtoAndCVFilename(TEST_FOLDER_LOCATION,
            candidateDTOValid, null);
  }

  @Test
  public void createCVFileFromFolderLocationAndCandidateDtoAndCVFilenameShouldReturnFileWhenAllParamIsValid() {
    File actualFile = candidateCVFileHandler
        .createCVFileFromFolderLocationAndCandidateDtoAndCVFilename(TEST_FOLDER_LOCATION,
            candidateDTOValid, CV_FILENAME);
    File expectedFile =
        new File(TEST_FOLDER_LOCATION + File.separator + CANDIDATE_FOLDER_NAME + File.separator
            + CV_FILENAME);

    assertThat(actualFile, equalTo(expectedFile));
  }

  @Test(expected = IllegalArgumentException.class)
  public void createCandidateFolderOnFolderLocationShouldThrowIllegalArgumentExceptionWhenFolderLocationIsNull()
      throws
      IOException {
    candidateCVFileHandler.createCandidateFolderOnFolderLocation(null, candidateDTOValid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createCandidateFolderOnFolderLocationShouldThrowIllegalArgumentExceptionWhenCandidateDtoIsNull()
      throws IOException {
    candidateCVFileHandler.createCandidateFolderOnFolderLocation(TEST_FOLDER_LOCATION, null);
  }

  @Test
  public void createCandidateFolderOnFolderLocationShouldCreateWhenAllParamIsValid()
      throws IOException {
    candidateCVFileHandler
        .createCandidateFolderOnFolderLocation(TEST_FOLDER_LOCATION, candidateDTOValid);
    String
        createdCandidateFolderPath =
        TEST_FOLDER_LOCATION + File.separator + CANDIDATE_FOLDER_NAME;
    File actualFile = new File(createdCandidateFolderPath);

    assertThat(actualFile, notNullValue());
    assertTrue(actualFile.exists());
    assertTrue(actualFile.isDirectory());
  }

  @Test(expected = IllegalArgumentException.class)
  public void existCandidateFolderOnFolderLocationShouldThrowIllegalArgumentExceptionWhenFolderLocationIsNull()
      throws IOException {
    candidateCVFileHandler.existCandidateFolderOnFolderLocation(null, candidateDTOValid);
  }

  @Test(expected = IllegalArgumentException.class)
  public void existCandidateFolderOnFolderLocationShouldThrowIllegalArgumentExceptionWhenCandidateDtoIsNull()
      throws IOException {
    candidateCVFileHandler.existCandidateFolderOnFolderLocation(TEST_FOLDER_LOCATION, null);
  }

  @Test(expected = IOException.class)
  public void existCandidateFolderOnFolderLocationShouldThrowIOExceptionWhenFolderLocationNotExists()
      throws IOException {
    candidateCVFileHandler
        .existCandidateFolderOnFolderLocation(StringUtils.EMPTY, candidateDTOValid);
  }

  @Test
  public void existCandidateFolderOnFolderLocationShouldReturnFalseWhenCandidateFolderNotExists()
      throws IOException {
    boolean
        actual =
        candidateCVFileHandler
            .existCandidateFolderOnFolderLocation(TEST_FOLDER_LOCATION, candidateDTOWithoutFolder);

    assertFalse(actual);
  }

  @Test
  public void existCandidateFolderOnFolderLocationShouldReturnTrueWhenCandidateFolderExists()
      throws IOException {
    boolean
        actual =
        candidateCVFileHandler
            .existCandidateFolderOnFolderLocation(TEST_FOLDER_LOCATION, candidateDTOValid);

    assertTrue(actual);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCandidateFolderNameFromCandidateDtoShouldThrowIllegalArgumentExceptionWhenCandidateDtoIsNull() {
    candidateCVFileHandler.getCandidateFolderNameFromCandidateDto(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCandidateFolderNameFromCandidateDtoShouldThrowIllegalArgumentExceptionWhenCandidateIdIsNull() {
    candidateCVFileHandler.getCandidateFolderNameFromCandidateDto(candidateDTOWithNullId);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCandidateFolderNameFromCandidateDtoShouldThrowIllegalArgumentExceptionWhenCandidateNameIsNull() {
    candidateCVFileHandler.getCandidateFolderNameFromCandidateDto(candidateDTOWithNullName);
  }

  @Test
  public void getCandidateFolderNameFromCandidateDtoShouldReturnCandidateFolderNameWhenAllParamIsValid() {
    String candidateFolderName =
        candidateCVFileHandler.getCandidateFolderNameFromCandidateDto(candidateDTOValid);

    assertThat(candidateFolderName, equalTo(CANDIDATE_FOLDER_NAME));
  }

}
