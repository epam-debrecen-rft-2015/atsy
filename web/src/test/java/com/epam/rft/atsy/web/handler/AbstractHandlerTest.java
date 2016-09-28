package com.epam.rft.atsy.web.handler;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.IOException;

public abstract class AbstractHandlerTest {

  protected static final String FOLDER_NAME_PARENT = "parent folder";
  protected static final String FOLDER_NAME_ACTUAL = "actual folder";
  protected static final String FOLDER_NAME_NON_EXISTENT = "non existent folder";
  protected static final String FILENAME = "angry_cat.pdf";

  protected final File parentFolder = new File(FOLDER_NAME_PARENT);
  protected final File actualFolder =
      new File(FOLDER_NAME_PARENT + File.separator + FOLDER_NAME_ACTUAL);

  @Before
  public void setup() throws IOException {
    FileUtils.forceMkdir(parentFolder);
    FileUtils.forceMkdir(actualFolder);
  }

  @After
  public void teardown() throws IOException {
    FileUtils.forceDelete(parentFolder);
  }
}