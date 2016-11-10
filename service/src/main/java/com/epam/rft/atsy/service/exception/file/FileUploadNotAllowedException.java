package com.epam.rft.atsy.service.exception.file;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileUploadNotAllowedException extends FileValidationException {

  public FileUploadNotAllowedException(String message) {
    super(message);
  }
}
