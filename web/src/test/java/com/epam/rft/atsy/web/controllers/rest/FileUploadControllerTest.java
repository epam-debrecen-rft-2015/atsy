package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.helper.MultipartFileCreatorTestHelper;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import com.epam.rft.atsy.service.exception.file.CandidateAlreadyHasCVFileException;
import com.epam.rft.atsy.service.exception.file.FileIsInWrongExtensionValidationException;
import com.epam.rft.atsy.service.exception.file.FileUploadNotAllowedException;
import com.epam.rft.atsy.service.exception.file.FileValidationException;
import com.epam.rft.atsy.web.controllers.AbstractControllerTest;
import com.epam.rft.atsy.web.handler.FileHandler;
import com.epam.rft.atsy.web.handler.FolderHandler;
import com.epam.rft.atsy.web.mapper.RuleValidationExceptionMapper;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import com.epam.rft.atsy.web.validator.FileValidator;

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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class FileUploadControllerTest extends AbstractControllerTest {

  private static final String REQUEST_URL_TO_UPLOAD_TO_CANDIDATE = "/secure/fileUpload/candidate/1";
  private static final String REQUEST_URL_TO_VALIDATE = "/secure/fileUpload/validate";
  private static final String REQUEST_URL_TO_UPLOAD_TO_STATE_HISTORY = "/secure/fileUpload/stateHistory/1";
  private static final String FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY = "file.is.in.wrong.extension";
  private static final String CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE_KEY = "candidate.already.has.cv.file";
  private static final String FILE_UPLOAD_NOT_ALLOWED_MESSAGE_KEY = "file.upload.not.allowed";
  private static final String FILE_IS_IN_WRONG_EXTENSION_MESSAGE = "The file is in wrong extension";
  private static final String CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE = "The candidate already has a CV file";
  private static final String FILE_UPLOAD_NOT_ALLOWED_MESSAGE = "File upload is not allowed";
  private static final String JSON_PATH_ERROR_MESSAGE = "$.errorMessage";

  private static final String UPLOAD_LOCATION_VARIABLE_NAME = "uploadLocation";
  private static final String CV_TEST_FOLDER_NAME = "cv_test_folder";

  private static final String CANDIDATE_ID_PARAM_NAME = "candidateId";
  private static final String CANDIDATE_ID_PARAM_VALUE = "1L";

  private static final long APPLICATION_ID = 1L;
  private static final long CANDIDATE_ID = 1L;
  private static final String CANDIDATE_NAME = "Candidate A";
  private static final String ORIGINAL_FILENAME_VALID = "file.pdf";
  private static final String ORIGINAL_FILENAME_INVALID = "file.txt";
  private static final int FILE_SIZE_HUNDRED_BYTE = 100;

  private static final String CANDIDATE_FOLDER_NAME = CV_TEST_FOLDER_NAME + File.separator + CANDIDATE_ID;
  private static final File TEST_FOLDER = new File(CV_TEST_FOLDER_NAME);
  private static final File CANDIDATE_FOLDER = new File(CANDIDATE_FOLDER_NAME);
  private static final String APPLICATION_ID_PARAM_NAME = "applicationId";
  private static final String APPLICATION_ID_PARAM_VALUE = "1";

  private File
      cvFile =
      new File(CV_TEST_FOLDER_NAME + File.separator + CANDIDATE_ID + File.separator + ORIGINAL_FILENAME_VALID);
  private CandidateDTO
      candidateDTOWithNullCVFilename =
      CandidateDTO.builder().id(CANDIDATE_ID).name(CANDIDATE_NAME).cvFilename(null).build();
  private CandidateDTO
      candidateDTOWithEmptyCVFilename =
      CandidateDTO.builder().id(CANDIDATE_ID).name(CANDIDATE_NAME).cvFilename(StringUtils.EMPTY).build();
  private CandidateDTO
      candidateDTOWithValidCVFilename =
      CandidateDTO.builder().id(CANDIDATE_ID).name(CANDIDATE_NAME).cvFilename(ORIGINAL_FILENAME_VALID).build();
  private StateHistoryDTO
      stateHistoryDTOWithCVState =
      StateHistoryDTO.builder().stateDTO(new StateDTO(1L, "cv")).build();
  private StateHistoryDTO
      stateHistoryDTOWithHRState =
      StateHistoryDTO.builder().stateDTO(new StateDTO(2L, "hr")).build();

  @Mock
  private FileValidator fileValidator;

  @Mock
  private RuleValidationExceptionMapper ruleValidationExceptionMapper;

  @Mock
  private FolderHandler folderHandler;

  @Mock
  private FileHandler fileHandler;

  @Mock
  private CandidateService candidateService;

  @Mock
  private StatesHistoryService statesHistoryService;

  @Mock
  private MessageKeyResolver messageKeyResolver;

  @Spy
  @InjectMocks
  private FileUploadController fileUploadController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{fileUploadController};
  }

  @Before
  public void setup() throws IOException {
    FileUtils.forceMkdir(TEST_FOLDER);
    FileUtils.forceMkdir(CANDIDATE_FOLDER);
    ReflectionTestUtils.setField(fileUploadController, UPLOAD_LOCATION_VARIABLE_NAME,
        CV_TEST_FOLDER_NAME);
  }

  @After
  public void tearDown() throws IOException {
    FileUtils.forceDelete(TEST_FOLDER);
  }

  @Test
  public void uploadFileForCandidateShouldRespondInternalServerErrorWhenCandidateIsNull() throws Exception {
    MockMultipartFile
        multipartFile =
        MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_INVALID, FILE_SIZE_HUNDRED_BYTE);

    given(candidateService.getCandidate(CANDIDATE_ID)).willReturn(null);

    this.mockMvc.perform(buildFileUploadRequestForFileUploadOnCandidateView(multipartFile))
        .andExpect(status().isInternalServerError());

    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.candidateService).should(never()).saveOrUpdate(any(CandidateDTO.class));
    verifyZeroInteractions(folderHandler, fileHandler, fileValidator, ruleValidationExceptionMapper,
        messageKeyResolver);
  }

  @Test
  public void uploadFileForCandidateShouldThrowFileValidationExceptionWhenFileIsInWrongExtensionAndCVFilenameIsNull()
      throws Exception {
    FileValidationException fileValidationException = new FileIsInWrongExtensionValidationException();
    MockMultipartFile
        multipartFile =
        MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_INVALID, FILE_SIZE_HUNDRED_BYTE);

    given(this.candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithNullCVFilename);
    given(this.ruleValidationExceptionMapper.getMessageKeyByException(fileValidationException))
        .willReturn(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY);
    given(this.messageKeyResolver.resolveMessageOrDefault(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY))
        .willReturn(FILE_IS_IN_WRONG_EXTENSION_MESSAGE);
    doThrow(fileValidationException).when(this.fileValidator).validate(multipartFile);

    this.mockMvc.perform(buildFileUploadRequestForFileUploadOnCandidateView(multipartFile))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).exists())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(FILE_IS_IN_WRONG_EXTENSION_MESSAGE));

    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.candidateService).should(never()).saveOrUpdate(any(CandidateDTO.class));
    then(this.fileValidator).should().validate(multipartFile);
    then(this.ruleValidationExceptionMapper).should().getMessageKeyByException(fileValidationException);
    then(this.messageKeyResolver).should().resolveMessageOrDefault(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY);
    verifyZeroInteractions(this.folderHandler, this.fileHandler);
  }

  @Test
  public void uploadFileForCandidateShouldThrowFileValidationExceptionWhenFileIsInWrongExtensionAndCVFilenameIsEmpty()
      throws Exception {
    FileValidationException fileValidationException = new FileIsInWrongExtensionValidationException();
    MockMultipartFile
        multipartFile =
        MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_INVALID, FILE_SIZE_HUNDRED_BYTE);

    given(this.candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithEmptyCVFilename);
    given(this.ruleValidationExceptionMapper.getMessageKeyByException(fileValidationException))
        .willReturn(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY);
    given(this.messageKeyResolver.resolveMessageOrDefault(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY))
        .willReturn(FILE_IS_IN_WRONG_EXTENSION_MESSAGE);
    doThrow(fileValidationException).when(fileValidator).validate(multipartFile);

    this.mockMvc.perform(buildFileUploadRequestForFileUploadOnCandidateView(multipartFile))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).exists())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(FILE_IS_IN_WRONG_EXTENSION_MESSAGE));

    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.candidateService).should(never()).saveOrUpdate(any(CandidateDTO.class));
    then(this.fileValidator).should().validate(multipartFile);
    then(this.ruleValidationExceptionMapper).should().getMessageKeyByException(fileValidationException);
    then(this.messageKeyResolver.resolveMessageOrDefault(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY));
    verifyZeroInteractions(this.folderHandler, this.fileHandler);
  }

  @Test
  public void uploadFileForCandidateShouldThrowCandidateAlreadyHasCVFileExceptionWhenCandidateAlreadyHasCVFile()
      throws Exception {
    MockMultipartFile
        multipartFile =
        MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_VALID, FILE_SIZE_HUNDRED_BYTE);

    given(this.candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithValidCVFilename);
    given(this.ruleValidationExceptionMapper.getMessageKeyByException(any(CandidateAlreadyHasCVFileException.class)))
        .willReturn(CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE_KEY);
    given(this.messageKeyResolver.resolveMessageOrDefault(CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE_KEY))
        .willReturn(CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE);

    this.mockMvc.perform(buildFileUploadRequestForFileUploadOnCandidateView(multipartFile))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).exists())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE));

    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.candidateService).should(never()).saveOrUpdate(any(CandidateDTO.class));
    then(this.ruleValidationExceptionMapper).should()
        .getMessageKeyByException(any(CandidateAlreadyHasCVFileException.class));
    then(this.messageKeyResolver).should().resolveMessageOrDefault(CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE_KEY);
    verifyZeroInteractions(this.fileValidator, this.folderHandler, this.fileHandler);
  }

  @Test
  public void uploadFileForCandidateShouldThrowFileUploadNotAllowedExceptionWhenApplicationStateNotEqualsCV()
      throws Exception {
    MockMultipartFile
        multipartFile =
        MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_VALID, FILE_SIZE_HUNDRED_BYTE);

    List<StateHistoryDTO>
        stateHistoryDTOs =
        Arrays.asList(stateHistoryDTOWithHRState);

    given(this.statesHistoryService.getStateHistoriesByApplicationId(APPLICATION_ID)).willReturn(stateHistoryDTOs);
    given(this.ruleValidationExceptionMapper.getMessageKeyByException(any(FileUploadNotAllowedException.class)))
        .willReturn(FILE_UPLOAD_NOT_ALLOWED_MESSAGE_KEY);
    given(this.messageKeyResolver.resolveMessageOrDefault(FILE_UPLOAD_NOT_ALLOWED_MESSAGE_KEY))
        .willReturn(FILE_UPLOAD_NOT_ALLOWED_MESSAGE);

    this.mockMvc.perform(buildFileUploadRequestForFileUploadOnStateHistoryView(multipartFile))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).exists())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(FILE_UPLOAD_NOT_ALLOWED_MESSAGE));

    then(this.ruleValidationExceptionMapper).should()
        .getMessageKeyByException(any(FileUploadNotAllowedException.class));
    then(this.messageKeyResolver).should().resolveMessageOrDefault(FILE_UPLOAD_NOT_ALLOWED_MESSAGE_KEY);
    verifyZeroInteractions(this.fileValidator, this.folderHandler, this.fileHandler);
  }

  @Test
  public void uploadFileForCandidateShouldSaveFileWhenEverythingIsOk() throws Exception {
    MockMultipartFile
        multipartFile =
        MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_VALID, FILE_SIZE_HUNDRED_BYTE);

    given(this.candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithNullCVFilename);
    given(this.fileHandler.getFileByParentDirectoryPathAndFolderNameAndFilename(CV_TEST_FOLDER_NAME,
        String.valueOf(CANDIDATE_ID), ORIGINAL_FILENAME_VALID)).willReturn(cvFile);

    this.mockMvc.perform(buildFileUploadRequestForFileUploadOnCandidateView(multipartFile))
        .andExpect(status().isOk())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).doesNotExist());

    assertThat(candidateDTOWithValidCVFilename.getCvFilename(), equalTo(ORIGINAL_FILENAME_VALID));

    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.fileValidator).should().validate(multipartFile);
    then(this.candidateService).should().saveOrUpdate(candidateDTOWithNullCVFilename);
    then(this.folderHandler).should().existFolderNameInParentDirectoryPath(any(String.class), any(String.class));
    then(this.folderHandler).should().createFolderInParentDirectoryPath(any(String.class), any(String.class));
    then(this.fileHandler).should().getFileByParentDirectoryPathAndFolderNameAndFilename(CV_TEST_FOLDER_NAME,
        String.valueOf(CANDIDATE_ID), ORIGINAL_FILENAME_VALID);
    verifyZeroInteractions(this.ruleValidationExceptionMapper, this.messageKeyResolver);
  }

  @Test
  public void uploadFileForStateShouldRespondInternalServerErrorWhenCandidateIdIsNull() throws Exception {
    MockMultipartFile
        multipartFile =
        MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_INVALID, FILE_SIZE_HUNDRED_BYTE);

    List<StateHistoryDTO>
        stateHistoryDTOs =
        Arrays.asList(stateHistoryDTOWithCVState);

    given(this.statesHistoryService.getStateHistoriesByApplicationId(APPLICATION_ID)).willReturn(stateHistoryDTOs);
    given(this.candidateService.getCandidateByApplicationID(APPLICATION_ID)).willReturn(null);

    this.mockMvc.perform(buildFileUploadRequestForFileUploadOnStateHistoryView(multipartFile))
        .andExpect(status().isInternalServerError());

    then(this.candidateService).should().getCandidateByApplicationID(APPLICATION_ID);
    then(this.candidateService).should(never()).getCandidate(any(Long.class));
    then(this.candidateService).should(never()).saveOrUpdate(any(CandidateDTO.class));
    verifyZeroInteractions(folderHandler, fileHandler, fileValidator, ruleValidationExceptionMapper,
        messageKeyResolver);
  }

  @Test
  public void uploadFileForStateShouldRespondWithErrorJsonWhenFileIsInWrongExtensionAndCVFilenameIsNull()
      throws Exception {
    FileValidationException fileValidationException = new FileIsInWrongExtensionValidationException();
    MockMultipartFile
        multipartFile =
        MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_INVALID, FILE_SIZE_HUNDRED_BYTE);

    List<StateHistoryDTO>
        stateHistoryDTOs =
        Arrays.asList(stateHistoryDTOWithCVState);

    given(this.statesHistoryService.getStateHistoriesByApplicationId(APPLICATION_ID)).willReturn(stateHistoryDTOs);
    given(this.candidateService.getCandidateByApplicationID(APPLICATION_ID)).willReturn(candidateDTOWithNullCVFilename);
    given(this.candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithNullCVFilename);
    given(this.ruleValidationExceptionMapper.getMessageKeyByException(fileValidationException))
        .willReturn(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY);
    given(this.messageKeyResolver.resolveMessageOrDefault(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY))
        .willReturn(FILE_IS_IN_WRONG_EXTENSION_MESSAGE);
    doThrow(fileValidationException).when(this.fileValidator).validate(multipartFile);

    this.mockMvc.perform(buildFileUploadRequestForFileUploadOnStateHistoryView(multipartFile))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).exists())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(FILE_IS_IN_WRONG_EXTENSION_MESSAGE));

    then(this.candidateService).should().getCandidateByApplicationID(APPLICATION_ID);
    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.candidateService).should(never()).saveOrUpdate(any(CandidateDTO.class));
    then(this.fileValidator).should().validate(multipartFile);
    then(this.ruleValidationExceptionMapper).should().getMessageKeyByException(fileValidationException);
    then(this.messageKeyResolver).should().resolveMessageOrDefault(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY);
    verifyZeroInteractions(this.folderHandler, this.fileHandler);
  }

  @Test
  public void uploadFileForStateShouldRespondWithErrorJsonWhenFileIsInWrongExtensionAndCVFilenameIsEmpty()
      throws Exception {
    FileValidationException fileValidationException = new FileIsInWrongExtensionValidationException();
    MockMultipartFile
        multipartFile =
        MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_INVALID, FILE_SIZE_HUNDRED_BYTE);

    List<StateHistoryDTO>
        stateHistoryDTOs =
        Arrays.asList(stateHistoryDTOWithCVState);

    given(this.statesHistoryService.getStateHistoriesByApplicationId(APPLICATION_ID)).willReturn(stateHistoryDTOs);
    given(this.candidateService.getCandidateByApplicationID(APPLICATION_ID)).willReturn(candidateDTOWithNullCVFilename);
    given(candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithEmptyCVFilename);
    given(ruleValidationExceptionMapper.getMessageKeyByException(fileValidationException))
        .willReturn(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY);
    given(this.messageKeyResolver.resolveMessageOrDefault(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY))
        .willReturn(FILE_IS_IN_WRONG_EXTENSION_MESSAGE);
    doThrow(fileValidationException).when(fileValidator).validate(multipartFile);

    this.mockMvc.perform(buildFileUploadRequestForFileUploadOnStateHistoryView(multipartFile))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).exists())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(FILE_IS_IN_WRONG_EXTENSION_MESSAGE));

    then(this.candidateService).should().getCandidateByApplicationID(APPLICATION_ID);
    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.candidateService).should(never()).saveOrUpdate(any(CandidateDTO.class));
    then(this.fileValidator).should().validate(multipartFile);
    then(this.ruleValidationExceptionMapper).should().getMessageKeyByException(fileValidationException);
    then(this.messageKeyResolver.resolveMessageOrDefault(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY));
    verifyZeroInteractions(this.folderHandler, this.fileHandler);
  }

  @Test
  public void uploadFileForStateShouldRespondWithErrorJsonWhenWhenCandidateAlreadyHasCVFile()
      throws Exception {
    MockMultipartFile
        multipartFile =
        MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_VALID, FILE_SIZE_HUNDRED_BYTE);

    List<StateHistoryDTO>
        stateHistoryDTOs =
        Arrays.asList(stateHistoryDTOWithCVState);

    given(this.statesHistoryService.getStateHistoriesByApplicationId(APPLICATION_ID)).willReturn(stateHistoryDTOs);
    given(this.candidateService.getCandidateByApplicationID(APPLICATION_ID)).willReturn(candidateDTOWithNullCVFilename);
    given(this.candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithValidCVFilename);
    given(this.ruleValidationExceptionMapper.getMessageKeyByException(any(CandidateAlreadyHasCVFileException.class)))
        .willReturn(CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE_KEY);
    given(this.messageKeyResolver.resolveMessageOrDefault(CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE_KEY))
        .willReturn(CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE);

    this.mockMvc.perform(buildFileUploadRequestForFileUploadOnStateHistoryView(multipartFile))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).exists())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE));

    then(this.candidateService).should().getCandidateByApplicationID(APPLICATION_ID);
    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.candidateService).should(never()).saveOrUpdate(any(CandidateDTO.class));
    then(this.ruleValidationExceptionMapper).should()
        .getMessageKeyByException(any(CandidateAlreadyHasCVFileException.class));
    then(this.messageKeyResolver).should().resolveMessageOrDefault(CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE_KEY);
    verifyZeroInteractions(this.fileValidator, this.folderHandler, this.fileHandler);
  }

  @Test
  public void uploadFileForStateShouldRespondOKWhenEverythingIsOk() throws Exception {
    MockMultipartFile
        multipartFile =
        MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_VALID, FILE_SIZE_HUNDRED_BYTE);

    List<StateHistoryDTO>
        stateHistoryDTOs =
        Arrays.asList(stateHistoryDTOWithCVState);

    given(this.statesHistoryService.getStateHistoriesByApplicationId(APPLICATION_ID)).willReturn(stateHistoryDTOs);
    given(this.candidateService.getCandidateByApplicationID(APPLICATION_ID)).willReturn(candidateDTOWithNullCVFilename);
    given(this.candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithNullCVFilename);
    given(this.fileHandler.getFileByParentDirectoryPathAndFolderNameAndFilename(CV_TEST_FOLDER_NAME,
        String.valueOf(CANDIDATE_ID), ORIGINAL_FILENAME_VALID)).willReturn(cvFile);

    this.mockMvc.perform(buildFileUploadRequestForFileUploadOnStateHistoryView(multipartFile))
        .andExpect(status().isOk())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).doesNotExist());

    assertThat(candidateDTOWithValidCVFilename.getCvFilename(), equalTo(ORIGINAL_FILENAME_VALID));
    then(this.candidateService).should().getCandidateByApplicationID(APPLICATION_ID);
    then(this.candidateService).should().getCandidate(CANDIDATE_ID);
    then(this.candidateService).should().saveOrUpdate(candidateDTOWithNullCVFilename);
    then(this.fileValidator).should().validate(multipartFile);
    then(this.folderHandler).should().existFolderNameInParentDirectoryPath(any(String.class), any(String.class));
    then(this.folderHandler).should().createFolderInParentDirectoryPath(any(String.class), any(String.class));
    then(this.fileHandler).should().getFileByParentDirectoryPathAndFolderNameAndFilename(CV_TEST_FOLDER_NAME,
        String.valueOf(CANDIDATE_ID), ORIGINAL_FILENAME_VALID);
    verifyZeroInteractions(this.ruleValidationExceptionMapper, this.messageKeyResolver);
  }

  @Test
  public void validateFileShouldRespondWithErrorJsonWhenFileHasValidationError() throws Exception {
    FileValidationException fileValidationException = new FileIsInWrongExtensionValidationException();
    MockMultipartFile
        multipartFile =
        MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_VALID, FILE_SIZE_HUNDRED_BYTE);

    doThrow(fileValidationException).when(this.fileValidator).validate(multipartFile);
    given(this.ruleValidationExceptionMapper.getMessageKeyByException(fileValidationException))
        .willReturn(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY);
    given(this.messageKeyResolver.resolveMessageOrDefault(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY))
        .willReturn(FILE_IS_IN_WRONG_EXTENSION_MESSAGE);

    this.mockMvc.perform(buildFileUploadRequestForValidateFile(multipartFile))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).exists())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(FILE_IS_IN_WRONG_EXTENSION_MESSAGE));

    then(this.fileValidator).should().validate(multipartFile);
    then(this.ruleValidationExceptionMapper).should().getMessageKeyByException(fileValidationException);
    then(this.messageKeyResolver).should().resolveMessageOrDefault(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY);
  }

  @Test
  public void validateFileShouldRespondOKWhenFileHasNoValidationErrors() throws Exception {
    MockMultipartFile
        multipartFile =
        MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_VALID, FILE_SIZE_HUNDRED_BYTE);

    this.mockMvc.perform(buildFileUploadRequestForValidateFile(multipartFile))
        .andExpect(status().isOk())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).isEmpty());

    then(this.fileValidator).should().validate(multipartFile);
    verifyZeroInteractions(this.ruleValidationExceptionMapper, this.messageKeyResolver);
  }

  private MockHttpServletRequestBuilder buildFileUploadRequestForFileUploadOnCandidateView(
      MockMultipartFile multipartFile) {
    return fileUpload(REQUEST_URL_TO_UPLOAD_TO_CANDIDATE)
        .file(multipartFile)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .param(CANDIDATE_ID_PARAM_NAME, CANDIDATE_ID_PARAM_VALUE);
  }

  private MockHttpServletRequestBuilder buildFileUploadRequestForFileUploadOnStateHistoryView(
      MockMultipartFile multipartFile) {
    return fileUpload(REQUEST_URL_TO_UPLOAD_TO_STATE_HISTORY)
        .file(multipartFile)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .param(APPLICATION_ID_PARAM_NAME, APPLICATION_ID_PARAM_VALUE);
  }

  private MockHttpServletRequestBuilder buildFileUploadRequestForValidateFile(MockMultipartFile multipartFile) {
    return fileUpload(REQUEST_URL_TO_VALIDATE)
        .file(multipartFile)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.MULTIPART_FORM_DATA);
  }

}