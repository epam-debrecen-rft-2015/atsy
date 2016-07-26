package com.epam.rft.atsy.service.exception;

public abstract class ObjectNotFoundException extends Exception {

  protected ObjectNotFoundException() {
  }

  protected ObjectNotFoundException(String message) {
    super(message);
  }

  protected ObjectNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  protected ObjectNotFoundException(Throwable cause) {
    super(cause);
  }
}
