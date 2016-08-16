package com.epam.rft.atsy.service.exception.file;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileIsAlreadyExistValidationException extends FileValidationException {

  public FileIsAlreadyExistValidationException(String message) {
    super(message);
  }
}
