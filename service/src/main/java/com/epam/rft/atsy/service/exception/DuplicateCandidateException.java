package com.epam.rft.atsy.service.exception;

public class DuplicateCandidateException extends DuplicateRecordException {
  private static final String ERROR_MESSAGE = "Duplication occurred when saving Candidate: ";

  public DuplicateCandidateException(String name, Throwable cause) {
    super(name, ERROR_MESSAGE + name, cause);
  }
}
