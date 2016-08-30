package com.epam.rft.atsy.web.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.rft.atsy.helper.MultipartFileCreatorTestHelper;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.exception.file.FileAlreadyExistsValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsInWrongExtensionValidationException;
import com.epam.rft.atsy.service.exception.file.FileValidationException;
import com.epam.rft.atsy.web.mapper.FileValidationRuleMapper;
import com.epam.rft.atsy.web.util.CandidateCVFileHandler;
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


@RunWith(MockitoJUnitRunner.class)
public class FileUploadControllerTest extends AbstractControllerTest {

  private static final String REQUEST_URL = "/secure/candidate/fileUpload/1";
  private static final String REDIRECT_URL = "/secure/candidate/1";
  private static final String VALIDATION_SUCCESS_KEY = "validationSuccessKey";
  private static final String VALIDATION_ERROR_KEY = "validationErrorKey";
  private static final String VALIDATION_FILE_SUCCESS_MESSAGE_KEY = "file.validation.success";
  private static final String FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY = "file.is.in.wrong.extension";
  private static final String FILE_ALREADY_EXISTS_MESSAGE_KEY = "file.already.exists";
  private static final String CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE_KEY = "candidate.already.has.cv.file";

  private static final String UPLOAD_LOCATION_VARIABLE_NAME = "uploadLocation";
  private static final String CATALINA_BASE = "${catalina.base}";
  private static final String CV_TEST_FOLDER_NAME = "cv_test_folder";

  private static final String CANDIDATE_ID_PARAM_NAME = "candidateId";
  private static final String CANDIDATE_ID_PARAM_VALUE = "1L";

  private static final long CANDIDATE_ID = 1L;
  private static final String CANDIDATE_NAME = "Candidate A";
  private static final String ORIGINAL_FILENAME_VALID = "file.pdf";
  private static final String ORIGINAL_FILENAME_INVALID = "file.txt";
  private static final int FILE_SIZE_HUNDRED_BYTE = 100;

  private static final String CV_TEST_FOLDER_LOCATION_PATH = System.getProperty(CATALINA_BASE) + File.separator + CV_TEST_FOLDER_NAME;
  private static final String CANDIDATE_FOLDER_NAME = CV_TEST_FOLDER_LOCATION_PATH + File.separator + CANDIDATE_ID;
  private static final File TEST_FOLDER = new File(CV_TEST_FOLDER_LOCATION_PATH);
  private static final File CANDIDATE_FOLDER = new File(CANDIDATE_FOLDER_NAME);

  private File cvFile = new File(CV_TEST_FOLDER_LOCATION_PATH + File.separator + CANDIDATE_ID + File.separator + ORIGINAL_FILENAME_VALID);
  private CandidateDTO candidateDTOWithNullCVFilename = CandidateDTO.builder().id(CANDIDATE_ID).name(CANDIDATE_NAME).cvFilename(null).build();
  private CandidateDTO candidateDTOWithEmptyCVFilename = CandidateDTO.builder().id(CANDIDATE_ID).name(CANDIDATE_NAME).cvFilename(StringUtils.EMPTY).build();
  private CandidateDTO candidateDTOWithValidCVFilename = CandidateDTO.builder().id(CANDIDATE_ID).name(CANDIDATE_NAME).cvFilename(ORIGINAL_FILENAME_VALID).build();

  @Mock
  private FileValidator fileValidator;

  @Mock
  private FileValidationRuleMapper fileValidationRuleMapper;

  @Mock
  private CandidateCVFileHandler candidateCVFileHandler;

  @Mock
  private CandidateService candidateService;

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
        CV_TEST_FOLDER_LOCATION_PATH);
  }

  @After
  public void tearDown() throws IOException {
    FileUtils.forceDelete(TEST_FOLDER);
  }

  @Test
  public void uploadFileShouldRespondInternalServerErrorWhenCandidateIsNull() throws Exception {
    MockMultipartFile multipartFile = MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_INVALID, FILE_SIZE_HUNDRED_BYTE);
    given(candidateService.getCandidate(CANDIDATE_ID)).willReturn(null);

    this.mockMvc.perform(buildFileUploadRequest(multipartFile))
        .andExpect(status().isInternalServerError());

    then(candidateService).should().getCandidate(CANDIDATE_ID);
    then(candidateService).should(times(0)).saveOrUpdate(any(CandidateDTO.class));
    verifyZeroInteractions(fileValidator, fileValidationRuleMapper);
  }

  @Test
  public void uploadFileShouldThrowFileValidationExceptionWhenFileIsInWrongExtensionAndCVFilenameIsNull()
      throws Exception {
    FileValidationException fileValidationException = new FileIsInWrongExtensionValidationException();
    MockMultipartFile multipartFile = MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_INVALID, FILE_SIZE_HUNDRED_BYTE);

    given(candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithNullCVFilename);
    given(fileValidationRuleMapper.getMessageKeyByException(fileValidationException)).willReturn(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY);
    doThrow(fileValidationException).when(fileValidator).validate(multipartFile);

    this.mockMvc.perform(buildFileUploadRequest(multipartFile))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists(VALIDATION_ERROR_KEY))
        .andExpect(flash().attribute(VALIDATION_ERROR_KEY, equalTo(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY)))
        .andExpect(redirectedUrl(REDIRECT_URL));

    then(candidateService).should().getCandidate(CANDIDATE_ID);
    then(fileValidator).should().validate(multipartFile);
    then(fileValidationRuleMapper).should().getMessageKeyByException(fileValidationException);
    then(candidateService).should(times(0)).saveOrUpdate(any(CandidateDTO.class));
  }

  @Test
  public void uploadFileShouldThrowFileValidationExceptionWhenFileIsInWrongExtensionAndCVFilenameIsEmpty()
      throws Exception {
    FileValidationException fileValidationException = new FileIsInWrongExtensionValidationException();
    MockMultipartFile multipartFile = MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_INVALID, FILE_SIZE_HUNDRED_BYTE);

    given(candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithEmptyCVFilename);
    given(fileValidationRuleMapper.getMessageKeyByException(fileValidationException)).willReturn(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY);
    doThrow(fileValidationException).when(fileValidator).validate(multipartFile);

    this.mockMvc.perform(buildFileUploadRequest(multipartFile))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists(VALIDATION_ERROR_KEY))
        .andExpect(flash().attribute(VALIDATION_ERROR_KEY, equalTo(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY)))
        .andExpect(redirectedUrl(REDIRECT_URL));

    then(candidateService).should().getCandidate(CANDIDATE_ID);
    then(fileValidator).should().validate(multipartFile);
    then(fileValidationRuleMapper).should().getMessageKeyByException(fileValidationException);
    then(candidateService).should(times(0)).saveOrUpdate(any(CandidateDTO.class));
  }

  @Test
  public void uploadFileShouldThrowFileAlreadyExistsValidationExceptionWhenFileAlreadyExists()
      throws Exception {
    FileValidationException fileValidationException = new FileAlreadyExistsValidationException();
    MockMultipartFile multipartFile = MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_VALID, FILE_SIZE_HUNDRED_BYTE);

    given(candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithNullCVFilename);
    given(fileValidationRuleMapper.getMessageKeyByException(fileValidationException)).willReturn(FILE_ALREADY_EXISTS_MESSAGE_KEY);
    doThrow(fileValidationException).when(fileUploadController).createFile(CANDIDATE_ID, ORIGINAL_FILENAME_VALID);

    this.mockMvc.perform(buildFileUploadRequest(multipartFile))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists(VALIDATION_ERROR_KEY))
        .andExpect(flash().attribute(VALIDATION_ERROR_KEY, equalTo(FILE_ALREADY_EXISTS_MESSAGE_KEY)))
        .andExpect(redirectedUrl(REDIRECT_URL));

    then(candidateService).should().getCandidate(CANDIDATE_ID);
    then(fileValidator).should().validate(multipartFile);
    then(fileValidationRuleMapper).should().getMessageKeyByException(fileValidationException);
    then(candidateService).should(times(0)).saveOrUpdate(any(CandidateDTO.class));
  }

  @Test
  public void uploadFileShouldThrowCandidateAlreadyHasCVFileExceptionWhenCandidateAlreadyHasCVFile()
      throws Exception {
    MockMultipartFile multipartFile = MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_VALID, FILE_SIZE_HUNDRED_BYTE);
    given(candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithValidCVFilename);

    this.mockMvc.perform(buildFileUploadRequest(multipartFile))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists(VALIDATION_ERROR_KEY))
        .andExpect(flash().attribute(VALIDATION_ERROR_KEY, equalTo(CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE_KEY)))
        .andExpect(redirectedUrl(REDIRECT_URL));

    then(candidateService).should().getCandidate(CANDIDATE_ID);
    then(candidateService).should(times(0)).saveOrUpdate(any(CandidateDTO.class));
    verifyZeroInteractions(fileValidator, fileValidationRuleMapper);
  }

  @Test
  public void uploadFileShouldSaveFileWhenEverythingIsOk() throws Exception {
    MockMultipartFile multipartFile = MultipartFileCreatorTestHelper.createMultipartFile(ORIGINAL_FILENAME_VALID, FILE_SIZE_HUNDRED_BYTE);
    given(candidateService.getCandidate(CANDIDATE_ID)).willReturn(candidateDTOWithNullCVFilename);
    given(candidateCVFileHandler.createCVFileFromFolderLocationAndCandidateDtoAndCVFilename(CV_TEST_FOLDER_LOCATION_PATH, CANDIDATE_ID, ORIGINAL_FILENAME_VALID)).willReturn(cvFile);

    this.mockMvc.perform(buildFileUploadRequest(multipartFile))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists(VALIDATION_SUCCESS_KEY))
        .andExpect(flash().attribute(VALIDATION_SUCCESS_KEY, equalTo(VALIDATION_FILE_SUCCESS_MESSAGE_KEY)))
        .andExpect(redirectedUrl(REDIRECT_URL));

    assertThat(candidateDTOWithValidCVFilename.getCvFilename(), equalTo(ORIGINAL_FILENAME_VALID));

    then(candidateService).should().getCandidate(CANDIDATE_ID);
    then(fileValidator).should().validate(multipartFile);
    then(candidateService).should().saveOrUpdate(candidateDTOWithNullCVFilename);
    then(candidateCVFileHandler).should().createCVFileFromFolderLocationAndCandidateDtoAndCVFilename(CV_TEST_FOLDER_LOCATION_PATH, CANDIDATE_ID, ORIGINAL_FILENAME_VALID);
    verifyZeroInteractions(fileValidationRuleMapper);
  }

  private MockHttpServletRequestBuilder buildFileUploadRequest(MockMultipartFile multipartFile) {
    return fileUpload(REQUEST_URL)
        .file(multipartFile)
        .accept(MediaType.TEXT_PLAIN)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .param(CANDIDATE_ID_PARAM_NAME, CANDIDATE_ID_PARAM_VALUE);
  }
}