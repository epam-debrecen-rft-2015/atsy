package com.epam.rft.atsy.service.exception.file;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class FileValidationException extends Exception {

  public FileValidationException(String message) {
    super(message);
  }
}
