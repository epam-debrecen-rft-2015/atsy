package com.epam.rft.atsy.service.exception;

public class DuplicatePositionException extends DuplicateRecordException {
  private static final String ERROR_MESSAGE = "Duplication occurred when saving Position: ";

  public DuplicatePositionException(String name, Throwable cause) {
    super(name, ERROR_MESSAGE + name, cause);
  }
}
