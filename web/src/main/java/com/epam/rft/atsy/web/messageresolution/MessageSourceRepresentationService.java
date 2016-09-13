package com.epam.rft.atsy.web.messageresolution;

/**
 * Service that operates with message source files in web layer.
 */
public interface MessageSourceRepresentationService {

  /**
   * Returns {@code true} if the {@code locale} parameter is supported, otherwise {@code false}.
   * @param locale the actual {@code locale} parameter as {@code String}, that can be supported or unsupported
   * @return {@code true} if the {@code locale} parameter is supported, otherwise {@code false}
   * @throws IllegalArgumentException If the {@code locale} parameter is {@code null}
   */
  boolean isSupportedLocale(String locale);

  /**
   * Returns all properties as key-value pairs in single {@code String}.
   * @param locale the actual {@code locale} parameter as {@code String}, that decides which property file will be read
   * @return all properties as key-value pairs in single {@code String}
   * @throws IllegalArgumentException If the {@code locale} parameter is {@code null}
   */
  String getLocalePropertiesAsString(String locale);
}
