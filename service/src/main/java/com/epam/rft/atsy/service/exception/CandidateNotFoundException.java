package com.epam.rft.atsy.service.exception;

public class CandidateNotFoundException extends ObjectNotFoundException {

  public CandidateNotFoundException() {
  }

  public CandidateNotFoundException(String message) {
    super(message);
  }

  public CandidateNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public CandidateNotFoundException(Throwable cause) {
    super(cause);
  }
}
