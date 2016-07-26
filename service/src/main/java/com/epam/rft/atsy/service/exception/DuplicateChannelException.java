package com.epam.rft.atsy.service.exception;

public class DuplicateChannelException extends DuplicateRecordException {
  private static final String ERROR_MESSAGE = "Duplication occurred when saving Channel: ";

  public DuplicateChannelException(String name, Throwable cause) {
    super(name, ERROR_MESSAGE + name, cause);
  }
}
