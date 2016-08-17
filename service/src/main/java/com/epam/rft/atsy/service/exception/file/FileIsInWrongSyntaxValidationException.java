package com.epam.rft.atsy.service.exception.file;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileIsInWrongSyntaxValidationException extends FileValidationException {

  public FileIsInWrongSyntaxValidationException(String message) {
    super(message);
  }
}
