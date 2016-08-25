package com.epam.rft.atsy.service.exception;

/**
 * Represents an exception, which is thrown when a duplicated record found in the database.
 */
public class DuplicateRecordException extends RuntimeException {
  private final String name;

  /**
   * Constructs a new instance of {@code DuplicateRecordException}, with the specified parameters.
   * @param name the name
   * @param message the message
   * @param cause the cause
   */
  public DuplicateRecordException(String name, String message, Throwable cause) {
    super(message, cause);

    this.name = name;
  }

  /**
   * Returns the name.
   * @return the name
   */
  public String getName() {
    return name;
  }
}
