package com.epam.rft.atsy.service.exception.file;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileIsMissingValidationException extends FileValidationException {

  public FileIsMissingValidationException(String message) {
    super(message);
  }
}
