package com.epam.rft.atsy.service.exception.file;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileIsInWrongExtensionValidationException extends FileValidationException {

  public FileIsInWrongExtensionValidationException(String message) {
    super(message);
  }
}
