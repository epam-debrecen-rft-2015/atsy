package com.epam.rft.atsy.service.exception;

/**
 * Exception thrown when a duplicated position is found in the database.
 */
public class DuplicatePositionException extends DuplicateRecordException {
  private static final String ERROR_MESSAGE = "Duplication occurred when saving Position: ";

  /**
   * Constructs a new instance of {@code DuplicatePositionException}, with the specified parameters.
   * @param name the name
   * @param cause the cause
   */
  public DuplicatePositionException(String name, Throwable cause) {
    super(name, ERROR_MESSAGE + name, cause);
  }

  public DuplicatePositionException(String name) {
    super(name);
  }
}
