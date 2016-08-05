package com.epam.rft.atsy.web.messageresolution;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class MessageKeyResolverImpl implements MessageKeyResolver, MessageSource {
  private MessageSource messageSource;

  public MessageKeyResolverImpl(MessageSource underlyingMessageSource) {
    this.messageSource = underlyingMessageSource;
  }

  @Override
  public String resolveMessage(String messageKey, Object... substitutions) {
    Locale currentLocale = LocaleContextHolder.getLocale();

    try {
      String resolvedMessage = getMessage(messageKey, substitutions, currentLocale);

      return resolvedMessage;
    } catch (NoSuchMessageException e) {
      throw new MessageResolutionFailedException(messageKey, currentLocale, e);
    }
  }

  @Override
  public String getMessage(String s, Object[] objects, String s1, Locale locale) {
    return messageSource.getMessage(s, objects, s1, locale);
  }

  @Override
  public String getMessage(String s, Object[] objects, Locale locale) {
    return messageSource.getMessage(s, objects, locale);
  }

  @Override
  public String getMessage(MessageSourceResolvable messageSourceResolvable, Locale locale) {
    return messageSource.getMessage(messageSourceResolvable, locale);
  }
}
