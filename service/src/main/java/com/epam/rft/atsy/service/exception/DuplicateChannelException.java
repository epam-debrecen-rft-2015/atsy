package com.epam.rft.atsy.service.exception;

/**
 * Exception thrown when a duplicate channel found in the database.
 */
public class DuplicateChannelException extends DuplicateRecordException {
  private static final String ERROR_MESSAGE = "Duplication occurred when saving Channel: ";

  /**
   * Constructs a new instance of {@code DuplicateChannelException} with the name of the channel
   * which caused the exception.
   * @param name a String which represent the name of the Channel which caused the exception
   * @param cause a {@code Throwable} which represents the exception which was caught when the
   * exception occurred
   */
  public DuplicateChannelException(String name, Throwable cause) {
    super(name, ERROR_MESSAGE + name, cause);
  }
}
