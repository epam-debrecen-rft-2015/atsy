package com.epam.rft.atsy.service.exception;

public class PositionNotFoundException extends ObjectNotFoundException {

  public PositionNotFoundException() {
  }

  public PositionNotFoundException(String message) {
    super(message);
  }

  public PositionNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public PositionNotFoundException(Throwable cause) {
    super(cause);
  }
}
