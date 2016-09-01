package com.epam.rft.atsy.service.exception.file;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileIsTooLargeValidationException extends FileValidationException {

  public FileIsTooLargeValidationException(String message) {
    super(message);
  }
}
