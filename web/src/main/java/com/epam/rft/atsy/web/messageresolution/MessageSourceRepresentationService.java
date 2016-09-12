package com.epam.rft.atsy.web.messageresolution;

public interface MessageSourceRepresentationService {

  boolean isSupportedLocale(String locale);

  String getLocalePropertiesAsString(String locale);
}
