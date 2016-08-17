package com.epam.rft.atsy.service.exception;

/**
 * A wrapper exception class, thrown in the service layer to unite various checked and unchecked
 * exceptions of the service layer.
 */
public class BackendException extends RuntimeException {

  /**
   * Creates a new instance of this exception containing the real cause exception.
   * @param cause the real cause Throwable which to be wrapped by this exception
   */
  public BackendException(Throwable cause) {
    super(cause);
  }
}
