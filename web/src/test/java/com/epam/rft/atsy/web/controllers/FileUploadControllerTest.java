package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.web.mapper.FileValidationRuleMapper;
import com.epam.rft.atsy.web.validator.FileValidator;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;


@RunWith(MockitoJUnitRunner.class)
public class FileUploadControllerTest extends AbstractControllerTest {

  private static final String CATALINA_BASE = "${catalina.base}";
  private static final String TEST_CV_DIRECTORY_NAME = "cv_directory_test";
  private static final String FOLDER_LOCATION =
      System.getProperty(CATALINA_BASE) + File.separator + TEST_CV_DIRECTORY_NAME;
  private static final File FOLDER = new File(FOLDER_LOCATION);

  @Mock
  private FileValidator fileValidator;

  @Mock
  private FileValidationRuleMapper fileValidationRuleMapper;

  @Mock
  private CandidateService candidateService;

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
}
