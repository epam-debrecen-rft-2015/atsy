package com.epam.rft.atsy.web.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FileStatus {

  FILE_IS_NOT_EXIST(false),
  FILE_IS_IN_PROGRESS(false),
  FILE_IS_ALREADY_EXIST(true);

  private boolean status;

  public boolean getStatus() {
    return status;
  }
}
