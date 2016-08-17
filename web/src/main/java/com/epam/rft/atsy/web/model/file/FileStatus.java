package com.epam.rft.atsy.web.model.file;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FileStatus {

  FILE_IS_NOT_EXIST(false),
  FILE_IS_IN_PROGRESS(null),
  FILE_IS_ALREADY_EXIST(true);

  public static final String CV_STATUS = "cv_status";
  private Boolean value;

  public Boolean getValue() { return value; }
}
