package com.epam.rft.atsy.service.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CandidateAlreadyHasCVFileException extends Exception {

  public CandidateAlreadyHasCVFileException(String message) {
    super(message);
  }
}
