package com.epam.rft.atsy.web.messageresolution;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;
import java.util.Properties;

/**
 * The {@code AtsyReloadableResourceBundleMessageSource} extends the {@code ReloadableResourceBundleMessageSource} class.
 * In this class the {@code getMergedPropertiesRepresentation} method is available from outside.
 */
public class AtsyReloadableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

  /**
   * Returns {@code Properties} that stores the key-value pairs from the chosen property file.
   * @param locale the actual {@code locale} parameter that decides which property file will be read
   * @return {@code Properties} that stores the key-value pairs from the chosen property file
   */
  public Properties getMergedPropertiesRepresentation(Locale locale) {
    return super.getMergedProperties(locale).getProperties();
  }

  /**
   * Returns {@code true} if the property file for {@code locale} is not empty, otherwise {@code false}
   * @param locale the actual {@code locale} parameter that decides which property file will be read
   * @return {@code true} if the property file for {@code locale} is not empty, otherwise {@code false}
   */
  public boolean isSupportedLocale(Locale locale) {
    return !this.getMergedProperties(locale).getProperties().isEmpty();
  }

}
