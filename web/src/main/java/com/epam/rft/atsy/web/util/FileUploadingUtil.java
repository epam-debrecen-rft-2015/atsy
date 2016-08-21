package com.epam.rft.atsy.web.util;


import java.io.File;

public class FileUploadingUtil {
  private static final String CV = "cv";
  public static final String CV_NAME = "cvName";
  public static final String UPLOAD_LOCATION = "catalina.base";
  public static final File FOLDER_CV =
      new File(System.getProperty(UPLOAD_LOCATION) + File.separator + CV);

}
