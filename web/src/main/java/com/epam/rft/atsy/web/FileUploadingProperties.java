package com.epam.rft.atsy.web;


import java.io.File;

public class FileUploadingProperties {
  private static final String CV = "cv";
  public static final String SESSION_PARAM_CV_PATH = "cv_path";
  public static final String UPLOAD_LOCATION = "catalina.base";
  public static final File FOLDER_CV =
      new File(System.getProperty(UPLOAD_LOCATION) + File.separator + CV);

}
