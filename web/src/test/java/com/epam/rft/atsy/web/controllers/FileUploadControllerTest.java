package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.helper.MultipartFileCreatorTestHelper;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.exception.CandidateAlreadyHasCVFileException;
import com.epam.rft.atsy.service.exception.file.FileAlreadyExistsValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsInWrongExtensionValidationException;
import com.epam.rft.atsy.service.exception.file.FileValidationException;
import com.epam.rft.atsy.web.mapper.FileValidationRuleMapper;
import com.epam.rft.atsy.web.validator.FileValidator;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class FileUploadControllerTest extends AbstractControllerTest {

  private static final String REQUEST_URL = "/secure/candidate/fileUpload/1";
  private static final String VALIDATION_SUCCESS_KEY = "validationSuccessKey";
  private static final String VALIDATION_ERROR_KEY = "validationErrorKey";
  private static final String FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY = "file.is.in.wrong.extension";
  private static final String FILE_ALREADY_EXISTS_MESSAGE_KEY = "file.already.exists";

  private static final String UPLOAD_LOCATION_VARIABLE_NAME = "uploadLocation";
  private static final String CATALINA_BASE = "${catalina.base}";
  private static final String CV_TEST_FOLDER_NAME = "cv_test_folder";
  private static final String CV_TEST_FOLDER_LOCATION_PATH =
      System.getProperty(CATALINA_BASE) + File.separator + CV_TEST_FOLDER_NAME;
  private static final File FOLDER = new File(CV_TEST_FOLDER_LOCATION_PATH);

  private static final String CANDIDATE_ID_PARAM_NAME = "candidateId";
  private static final String CANDIDATE_ID_PARAM_VALUE = "1L";

  private static final Long CANDIDATE_ID_A = 1L;
  private static final String CANDIDATE_NAME_A = "Candidate A";

  private CandidateDTO candidateDTOWithoutCVFile =
      CandidateDTO.builder().id(CANDIDATE_ID_A).name(CANDIDATE_NAME_A).cvFilename(null).build();

  @Mock
  private FileValidator fileValidator;

  @Mock
  private FileValidationRuleMapper fileValidationRuleMapper;

  @Mock
  private CandidateService candidateService;

  @Spy
  @InjectMocks
  private FileUploadController fileUploadController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{fileUploadController};
  }

  @BeforeClass
  public static void doBeforeClass() throws IOException {
    FileUtils.forceMkdir(FOLDER);
  }

  @AfterClass
  public static void doAfterClass() throws IOException {
    FileUtils.forceDelete(FOLDER);
  }

  @Before
  public void setup() {
    ReflectionTestUtils.setField(fileUploadController, UPLOAD_LOCATION_VARIABLE_NAME, CV_TEST_FOLDER_LOCATION_PATH);
  }

  @Test
  public void uploadFileShouldThrowFileValidationExceptionWhenFileIsInWrongExtension() throws Exception {
    // Given
    FileValidationException fileValidationException = new FileIsInWrongExtensionValidationException();
    String originalFileName = "file.txt";
    MockMultipartFile multipartFile = MultipartFileCreatorTestHelper.createMultipartFile(originalFileName, 20);

    given(candidateService.getCandidate(CANDIDATE_ID_A)).willReturn(candidateDTOWithoutCVFile);
    given(fileValidationRuleMapper.getMessageKeyByException(fileValidationException))
        .willReturn(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY);
    doThrow(fileValidationException).when(fileValidator).validate(multipartFile);

    this.mockMvc.perform(buildFileUploadRequest(multipartFile))
        .andExpect(flash().attributeExists(VALIDATION_ERROR_KEY))
        .andExpect(flash().attribute(VALIDATION_ERROR_KEY, equalTo(FILE_IS_IN_WRONG_EXTENSION_MESSAGE_KEY)))
        .andExpect(status().is3xxRedirection());

    then(candidateService).should().getCandidate(CANDIDATE_ID_A);
    then(fileValidator).should().validate(multipartFile);
    then(fileValidationRuleMapper).should().getMessageKeyByException(fileValidationException);
    then(candidateService).should(times(0)).saveOrUpdate(any(CandidateDTO.class));
  }

  @Test
  public void uploadFileShouldThrowFileAlreadyExistsValidationExceptionWhenFileAlreadyExists() throws Exception {
    // Given
    FileValidationException fileValidationException = new FileAlreadyExistsValidationException();
    String originalFilename = "file.pdf";
    MockMultipartFile multipartFile = MultipartFileCreatorTestHelper.createMultipartFile(originalFilename, 20);

    given(candidateService.getCandidate(CANDIDATE_ID_A)).willReturn(candidateDTOWithoutCVFile);
    given(fileValidationRuleMapper.getMessageKeyByException(fileValidationException))
        .willReturn(FILE_ALREADY_EXISTS_MESSAGE_KEY);
    doThrow(fileValidationException).when(fileUploadController).createFile(originalFilename);

    this.mockMvc.perform(buildFileUploadRequest(multipartFile))
        .andExpect(flash().attributeExists(VALIDATION_ERROR_KEY))
        .andExpect(flash().attribute(VALIDATION_ERROR_KEY, equalTo(FILE_ALREADY_EXISTS_MESSAGE_KEY)))
        .andExpect(status().is3xxRedirection());

    then(candidateService).should().getCandidate(CANDIDATE_ID_A);
    then(fileValidator).should().validate(multipartFile);
    then(fileValidationRuleMapper).should().getMessageKeyByException(fileValidationException);
    then(candidateService).should(times(0)).saveOrUpdate(any(CandidateDTO.class));
  }




  private MockHttpServletRequestBuilder buildFileUploadRequest(MockMultipartFile multipartFile) {
    return fileUpload(REQUEST_URL)
        .file(multipartFile)
        .accept(MediaType.TEXT_PLAIN)
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .param(CANDIDATE_ID_PARAM_NAME, CANDIDATE_ID_PARAM_VALUE);
  }
}
