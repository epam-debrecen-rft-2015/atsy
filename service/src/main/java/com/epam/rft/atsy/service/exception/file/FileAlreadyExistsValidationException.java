package com.epam.rft.atsy.service.exception.file;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileAlreadyExistsValidationException extends FileValidationException {

  public FileAlreadyExistsValidationException(String message) {
    super(message);
  }
}
