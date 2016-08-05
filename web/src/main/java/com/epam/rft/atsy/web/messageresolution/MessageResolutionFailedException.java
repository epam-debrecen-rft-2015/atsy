package com.epam.rft.atsy.web.messageresolution;

public class MessageResolutionFailedException extends RuntimeException {
  private final String messageKey;

  public MessageResolutionFailedException(String messageKey, Throwable cause) {
    super(cause);

    this.messageKey = messageKey;
  }

  public String getMessageKey() {
    return messageKey;
  }

  @Override
  public String getMessage() {
    return "Message resolution failed for message key: " + messageKey;
  }
}
