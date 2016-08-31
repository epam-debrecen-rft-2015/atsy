package com.epam.rft.atsy.service.exception.file;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileIsEmptyValidationException extends FileValidationException {

  public FileIsEmptyValidationException(String message) {
    super(message);
  }
}
