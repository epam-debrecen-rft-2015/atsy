package com.epam.rft.atsy.service.exception;

/**
 * This exception indicates that the saving process of a candidate failed because of duplication.
 */
public class DuplicateCandidateException extends DuplicateRecordException {
  private static final String ERROR_MESSAGE = "Duplication occurred when saving Candidate: ";

  /**
   * Creates a new instance of this exception containing an error message built from the parameters
   * and the base error message of this class.
   * @param name name of the candidate whose saving process failed
   * @param cause a Throwable that contains the error message of the cause event
   */
  public DuplicateCandidateException(String name, Throwable cause) {
    super(name, ERROR_MESSAGE + name, cause);
  }
}
