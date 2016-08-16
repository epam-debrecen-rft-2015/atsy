package com.epam.rft.atsy.web.messageresolution;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * Provides a developer friendly way of message key resolution by implementing the {@code
 * MessageKeyResolver} interface. Also implements the {@code MessageSource} interface so can be used
 * as the default {@code MessageSource}. Behaves as a proxy on top of an underlying {@code
 * MessageSource} implementation and delegates the methods inherited from the {@code MessageSource}
 * interface to the underlying concrete {@code MessageSource} object.
 */
public class MessageKeyResolverImpl implements MessageKeyResolver, MessageSource {
  private MessageSource messageSource;

  /**
   * Constructs a new {@code MessageKeyResolverImpl} instance that will act as a proxy on top of the
   * passed {@code MessageSource}.
   * @param underlyingMessageSource the {@code MessageSource} to hide
   */
  public MessageKeyResolverImpl(MessageSource underlyingMessageSource) {
    this.messageSource = underlyingMessageSource;
  }

  @Override
  public String resolveMessage(String messageKey, Object... substitutions)
      throws MessageResolutionFailedException {
    Locale currentLocale = LocaleContextHolder.getLocale();

    try {
      String resolvedMessage = getMessage(messageKey, substitutions, currentLocale);

      return resolvedMessage;
    } catch (NoSuchMessageException e) {
      throw new MessageResolutionFailedException(messageKey, currentLocale, e);
    }
  }

  @Override
  public String resolveMessageOrDefault(String messageKey,  Object... substitutions) {
    try {
      return resolveMessage(messageKey, substitutions);
    } catch (MessageResolutionFailedException e) {
      return "[ ! " + messageKey + " ! ]";
    }
  }

  @Override
  public String getMessage(String messageKey, Object[] substitutions, String defaultMessage,
                           Locale locale) {
    return messageSource.getMessage(messageKey, substitutions, defaultMessage, locale);
  }

  @Override
  public String getMessage(String messageKey, Object[] substitutions, Locale locale) {
    return messageSource.getMessage(messageKey, substitutions, locale);
  }

  @Override
  public String getMessage(MessageSourceResolvable messageSourceResolvable, Locale locale) {
    return messageSource.getMessage(messageSourceResolvable, locale);
  }
}
