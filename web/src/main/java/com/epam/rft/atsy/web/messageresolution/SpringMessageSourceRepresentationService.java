package com.epam.rft.atsy.web.messageresolution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Pattern;

@Service
public class SpringMessageSourceRepresentationService implements MessageSourceRepresenationService {

  public static final String DEFAULT_PATTERN_AS_STRING = "\\.js($|.)";
  private AtsyReloadableResourceBundleMessageSource internalMessageSource;
  private Pattern pattern;

  @Autowired
  public SpringMessageSourceRepresentationService(
      AtsyReloadableResourceBundleMessageSource internalMessageSource) {
    this(internalMessageSource, DEFAULT_PATTERN_AS_STRING);
  }

  public SpringMessageSourceRepresentationService(
      AtsyReloadableResourceBundleMessageSource internalMessageSource, String patternString) {
    this.internalMessageSource = internalMessageSource;
    this.pattern = Pattern.compile(patternString);
  }

  @Override
  public boolean isSupportedLocale(String locale) {
    return this.internalMessageSource.isSupportedLocale(new Locale(locale));
  }

  @Override
  public String getLocalePropertiesAsString(String locale) {
    Properties
        properties =
        this.internalMessageSource.getMergedPropertiesRepresentation(new Locale(locale));
    StringWriter writer = new StringWriter();
    properties.keySet().stream().filter(key -> pattern.matcher(key.toString()).find())
        .forEach(key -> writer.write(key + "=" + properties.getProperty(key.toString()) + "\n"));
    return writer.toString();
  }

}
