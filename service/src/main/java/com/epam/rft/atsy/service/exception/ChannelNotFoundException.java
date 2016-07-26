package com.epam.rft.atsy.service.exception;

public class ChannelNotFoundException extends ObjectNotFoundException {

  public ChannelNotFoundException() {
  }

  public ChannelNotFoundException(String message) {
    super(message);
  }

  public ChannelNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ChannelNotFoundException(Throwable cause) {
    super(cause);
  }
}
