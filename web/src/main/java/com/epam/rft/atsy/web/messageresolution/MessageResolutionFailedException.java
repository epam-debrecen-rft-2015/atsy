package com.epam.rft.atsy.web.messageresolution;

import java.util.Locale;

public class MessageResolutionFailedException extends Exception {
  private final String messageKey;

  private final Locale locale;

  public MessageResolutionFailedException(String messageKey, Locale locale, Throwable cause) {
    super(cause);

    this.messageKey = messageKey;

    this.locale = locale;
  }

  public String getMessageKey() {
    return messageKey;
  }

  public Locale getLocale() {
    return locale;
  }

  @Override
  public String getMessage() {
    return "Message resolution failed for message key '" + messageKey + "' for locale '" + locale
        + "'.";
  }
}
