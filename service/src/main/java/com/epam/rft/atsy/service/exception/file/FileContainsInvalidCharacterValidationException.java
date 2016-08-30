package com.epam.rft.atsy.service.exception.file;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileContainsInvalidCharacterValidationException extends FileValidationException {

  public FileContainsInvalidCharacterValidationException(String message) {
    super(message);
  }
}
