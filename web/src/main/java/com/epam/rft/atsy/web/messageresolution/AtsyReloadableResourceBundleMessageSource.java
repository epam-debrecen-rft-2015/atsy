package com.epam.rft.atsy.web.messageresolution;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;
import java.util.Properties;

public class AtsyReloadableResourceBundleMessageSource
    extends ReloadableResourceBundleMessageSource {

  public Properties getMergedPropertiesRepresentation(Locale locale) {
    return super.getMergedProperties(locale).getProperties();
  }

  public boolean isSupportedLocale(Locale locale) {
    PropertiesHolder holder = this.getMergedProperties(locale);
    return !holder.getProperties().isEmpty();
  }

}
