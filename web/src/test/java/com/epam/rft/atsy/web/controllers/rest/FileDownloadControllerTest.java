package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.controllers.AbstractControllerTest;
import com.epam.rft.atsy.web.handler.FileHandler;
import com.epam.rft.atsy.web.handler.FolderHandler;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class FileDownloadControllerTest extends AbstractControllerTest {

  private static final String REQUEST_URL_TO_DOWNLOAD = "/secure/fileDownload/1";
  private static final String REQUEST_URL_TO_VALIDATE = "/secure/fileDownload/validate/1";
  private static final String FILE_ERROR_MESSAGE_KEY = "file.not.exists.anymore";
  private static final String FILE_ERROR_MESSAGE = "The file not exists anymore.";

  private static final String CONTENT_DISPOSITION = "Content-Disposition";
  private static final String INLINE_FORMAT = "inline; filename=\"";

  private static final String UPLOAD_LOCATION_VARIABLE_NAME = "uploadLocation";
  private static final String CATALINA_BASE = "${catalina.base}";
  private static final String CV_TEST_FOLDER_NAME = "cv_test_folder";

  private static final String
      CV_TEST_FOLDER_LOCATION_PATH =
      System.getProperty(CATALINA_BASE) + File.separator + CV_TEST_FOLDER_NAME;
  private static final File TEST_FOLDER = new File(CV_TEST_FOLDER_LOCATION_PATH);

  private static final String JSON_PATH_ERROR_MESSAGE = "$.errorMessage";

  private static final String CANDIDATE_ID_PARAM_NAME = "candidateId";
  private static final String CANDIDATE_ID_PARAM_VALUE = "1L";

  private static final long CANDIDATE_ID = 1L;
  private static final String CANDIDATE_NAME = "Candidate A";
  private static final String CANDIDATE_CV_FILENAME = "file.pdf";
  private static final String NON_EXISTENT_CV_FILENAME = "missing_file.pdf";
  private static final int FILE_SIZE_HUNDRED_BYTE = 100;
  private static final char CHARACTER = 'a';

  private CandidateDTO
      candidateDTOWitNullCVFilename =
      CandidateDTO.builder().id(CANDIDATE_ID).name(CANDIDATE_NAME).cvFilename(null).build();
  private CandidateDTO
      candidateDTOWithEmptyCVFilename =
      CandidateDTO.builder().id(CANDIDATE_ID).name(CANDIDATE_NAME).cvFilename(StringUtils.EMPTY).build();
  private CandidateDTO
      candidateDTOWithValidCVFilename =
      CandidateDTO.builder().id(CANDIDATE_ID).name(CANDIDATE_NAME).cvFilename(CANDIDATE_CV_FILENAME).build();
  private CandidateDTO
      candidateDTOWithNonExistentCVFilename =
      CandidateDTO.builder().id(CANDIDATE_ID).name(CANDIDATE_NAME).cvFilename(NON_EXISTENT_CV_FILENAME).build();
  private File cvFileExisting = null;
  private File cvFileNonExistent = null;

  @Mock
  private CandidateService candidateService;

  @Mock
  private FolderHandler folderHandler;

  @Mock
  private FileHandler fileHandler;

  @Mock
  private MessageKeyResolver messageKeyResolver;

  @Spy
  @InjectMocks
  private FileDownloadController fileDownloadController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{fileDownloadController};
  }

  @Before
  public void setup() throws IOException {
    FileUtils.forceMkdir(TEST_FOLDER);
    FileUtils.forceMkdir(new File(CV_TEST_FOLDER_LOCATION_PATH + File.separator + CANDIDATE_ID));
    cvFileExisting =
        new File(CV_TEST_FOLDER_LOCATION_PATH + File.separator + CANDIDATE_ID + File.separator + CANDIDATE_CV_FILENAME);
    cvFileNonExistent =
        new File(
            CV_TEST_FOLDER_LOCATION_PATH + File.separator + CANDIDATE_ID + File.separator + NON_EXISTENT_CV_FILENAME);
    FileCopyUtils.copy(StringUtils.repeat(CHARACTER, FILE_SIZE_HUNDRED_BYTE).getBytes(), cvFileExisting);
    ReflectionTestUtils.setField(fileDownloadController, UPLOAD_LOCATION_VARIABLE_NAME, CV_TEST_FOLDER_LOCATION_PATH);
  }

  @After
  public void teardown() throws IOException {
    FileUtils.forceDelete(TEST_FOLDER);
  }

  @Test
  public void downloadFileShouldRespondInternalServerErrorWhenCandidateIsNull() throws Exception {
    given(this.candidateService.getCandidate(CANDIDATE_ID)).willReturn(null);

    this.mockMvc.perform(get(REQUEST_URL_TO_DOWNLOAD).param(CANDIDATE_ID_PARAM_NAME, CANDIDATE_ID_PARAM_VALUE))
        .andExpect(status().isInternalServerError());

    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    verifyZeroInteractions(this.fileHandler, this.messageKeyResolver);
  }

  @Test
  public void downloadFileShouldNotDownloadWhenTheNameOfTheCVFileIsNullInDB() throws Exception {
    given(this.candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWitNullCVFilename);
    given(this.messageKeyResolver.resolveMessageOrDefault(FILE_ERROR_MESSAGE_KEY)).willReturn(FILE_ERROR_MESSAGE);

    this.mockMvc.perform(get(REQUEST_URL_TO_DOWNLOAD).param(CANDIDATE_ID_PARAM_NAME, CANDIDATE_ID_PARAM_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).exists())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(FILE_ERROR_MESSAGE));

    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.messageKeyResolver).should().resolveMessageOrDefault(FILE_ERROR_MESSAGE_KEY);
    verifyZeroInteractions(this.fileHandler);
  }

  @Test
  public void downloadFileShouldNotDownloadWhenTheNameOfTheCVFileIsEmptyInDB() throws Exception {
    given(this.candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithEmptyCVFilename);
    given(this.messageKeyResolver.resolveMessageOrDefault(FILE_ERROR_MESSAGE_KEY)).willReturn(FILE_ERROR_MESSAGE);

    this.mockMvc.perform(get(REQUEST_URL_TO_DOWNLOAD).param(CANDIDATE_ID_PARAM_NAME, CANDIDATE_ID_PARAM_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).exists())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(FILE_ERROR_MESSAGE));

    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.messageKeyResolver).should().resolveMessageOrDefault(FILE_ERROR_MESSAGE_KEY);
    verifyZeroInteractions(this.fileHandler);
  }

  @Test
  public void downloadFileShouldNotDownloadWhenTheNameOfTheCVFileExistsInDBAndNotExistsInFileSystem() throws Exception {
    given(this.candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithNonExistentCVFilename);
    given(this.messageKeyResolver.resolveMessageOrDefault(FILE_ERROR_MESSAGE_KEY)).willReturn(FILE_ERROR_MESSAGE);
    given(this.fileHandler.getFileByParentDirectoryPathAndFolderNameAndFilename(CV_TEST_FOLDER_LOCATION_PATH,
        String.valueOf(CANDIDATE_ID), NON_EXISTENT_CV_FILENAME)).willReturn(cvFileNonExistent);

    this.mockMvc.perform(get(REQUEST_URL_TO_DOWNLOAD).param(CANDIDATE_ID_PARAM_NAME, CANDIDATE_ID_PARAM_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).exists())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(FILE_ERROR_MESSAGE));

    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.messageKeyResolver).should().resolveMessageOrDefault(FILE_ERROR_MESSAGE_KEY);
    then(this.fileHandler).should().getFileByParentDirectoryPathAndFolderNameAndFilename(CV_TEST_FOLDER_LOCATION_PATH,
        String.valueOf(CANDIDATE_ID), NON_EXISTENT_CV_FILENAME);
  }

  @Test
  public void downloadFileShouldDownloadWhenTheNameOfTheCVFileExistsInDBAndInFileSystem() throws Exception {
    given(candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithValidCVFilename);
    given(this.fileHandler.getFileByParentDirectoryPathAndFolderNameAndFilename(CV_TEST_FOLDER_LOCATION_PATH,
        String.valueOf(CANDIDATE_ID), CANDIDATE_CV_FILENAME)).willReturn(cvFileExisting);
    MockHttpServletResponse
        result =
        this.mockMvc.perform(get(REQUEST_URL_TO_DOWNLOAD).param(CANDIDATE_ID_PARAM_NAME, CANDIDATE_ID_PARAM_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).doesNotExist())
            .andReturn().getResponse();

    assertThat(INLINE_FORMAT + CANDIDATE_CV_FILENAME, equalTo(result.getHeader(CONTENT_DISPOSITION)));
    assertThat(MediaType.APPLICATION_OCTET_STREAM_VALUE, equalTo(result.getContentType()));
    assertThat(result.getContentLength(), equalTo(FILE_SIZE_HUNDRED_BYTE));

    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.fileHandler).should().getFileByParentDirectoryPathAndFolderNameAndFilename(CV_TEST_FOLDER_LOCATION_PATH,
        String.valueOf(CANDIDATE_ID), CANDIDATE_CV_FILENAME);
    verifyZeroInteractions(this.messageKeyResolver);
  }


  @Test
  public void validateFileShouldRespondInternalServerErrorWhenCandidateIsNull() throws Exception {
    given(this.candidateService.getCandidate(CANDIDATE_ID)).willReturn(null);

    this.mockMvc.perform(get(REQUEST_URL_TO_VALIDATE).param(CANDIDATE_ID_PARAM_NAME, CANDIDATE_ID_PARAM_VALUE))
        .andExpect(status().isInternalServerError());

    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    verifyZeroInteractions(this.fileHandler, this.messageKeyResolver);
  }

  @Test
  public void validateFileShouldNotValidatedSuccessfulWhenTheNameOfTheCVFileIsNullInDB() throws Exception {
    given(this.candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWitNullCVFilename);
    given(this.messageKeyResolver.resolveMessageOrDefault(FILE_ERROR_MESSAGE_KEY)).willReturn(FILE_ERROR_MESSAGE);

    this.mockMvc.perform(get(REQUEST_URL_TO_VALIDATE).param(CANDIDATE_ID_PARAM_NAME, CANDIDATE_ID_PARAM_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).exists())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(FILE_ERROR_MESSAGE));

    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.messageKeyResolver).should().resolveMessageOrDefault(FILE_ERROR_MESSAGE_KEY);
    verifyZeroInteractions(this.fileHandler);
  }

  @Test
  public void validateFileShouldNotValidatedSuccessfulWhenTheNameOfTheCVFileIsEmptyInDB() throws Exception {
    given(this.candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithEmptyCVFilename);
    given(this.messageKeyResolver.resolveMessageOrDefault(FILE_ERROR_MESSAGE_KEY)).willReturn(FILE_ERROR_MESSAGE);

    this.mockMvc.perform(get(REQUEST_URL_TO_VALIDATE).param(CANDIDATE_ID_PARAM_NAME, CANDIDATE_ID_PARAM_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).exists())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(FILE_ERROR_MESSAGE));

    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.messageKeyResolver).should().resolveMessageOrDefault(FILE_ERROR_MESSAGE_KEY);
    verifyZeroInteractions(this.fileHandler);
  }

  @Test
  public void validateFileShouldNotValidatedSuccessFulWhenTheNameOfTheCVFileExistsInDBAndNotExistsInFileSystem()
      throws Exception {
    given(this.candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithNonExistentCVFilename);
    given(this.messageKeyResolver.resolveMessageOrDefault(FILE_ERROR_MESSAGE_KEY)).willReturn(FILE_ERROR_MESSAGE);
    given(this.fileHandler.getFileByParentDirectoryPathAndFolderNameAndFilename(CV_TEST_FOLDER_LOCATION_PATH,
        String.valueOf(CANDIDATE_ID), NON_EXISTENT_CV_FILENAME)).willReturn(cvFileNonExistent);

    this.mockMvc.perform(get(REQUEST_URL_TO_VALIDATE).param(CANDIDATE_ID_PARAM_NAME, CANDIDATE_ID_PARAM_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).exists())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(FILE_ERROR_MESSAGE));

    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.messageKeyResolver).should().resolveMessageOrDefault(FILE_ERROR_MESSAGE_KEY);
    then(this.fileHandler).should().getFileByParentDirectoryPathAndFolderNameAndFilename(CV_TEST_FOLDER_LOCATION_PATH,
        String.valueOf(CANDIDATE_ID), NON_EXISTENT_CV_FILENAME);
  }

  @Test
  public void validateFileShouldValidatedSuccessFulWhenTheNameOfTheCVFileExistsInDBAndInFileSystem() throws Exception {
    given(candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithValidCVFilename);
    given(this.fileHandler.getFileByParentDirectoryPathAndFolderNameAndFilename(CV_TEST_FOLDER_LOCATION_PATH,
        String.valueOf(CANDIDATE_ID), CANDIDATE_CV_FILENAME)).willReturn(cvFileExisting);

    this.mockMvc.perform(get(REQUEST_URL_TO_DOWNLOAD).param(CANDIDATE_ID_PARAM_NAME, CANDIDATE_ID_PARAM_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).doesNotExist());

    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.fileHandler).should().getFileByParentDirectoryPathAndFolderNameAndFilename(CV_TEST_FOLDER_LOCATION_PATH,
        String.valueOf(CANDIDATE_ID), CANDIDATE_CV_FILENAME);
    verifyZeroInteractions(this.messageKeyResolver);
  }
}