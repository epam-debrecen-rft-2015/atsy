package com.epam.rft.atsy.service.exception.file;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CandidateAlreadyHasCVFileException extends FileValidationException {

  public CandidateAlreadyHasCVFileException(String message) {
    super(message);
  }
}
