package com.epam.rft.atsy.web.messageresolution;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpringMessageSourceRepresentationServiceTest {

  private AtsyReloadableResourceBundleMessageSource internalMessageSource;
  private SpringMessageSourceRepresentationService service;

  @Before
  public void setup() {
    this.internalMessageSource = new AtsyReloadableResourceBundleMessageSource();
    this.internalMessageSource.setBasename("classpath:/test-i18n/messages");
    this.internalMessageSource.setDefaultEncoding("UTF-8");
    this.internalMessageSource.setFallbackToSystemLocale(false);

    this.service =
        new SpringMessageSourceRepresentationService(this.internalMessageSource, "\\.js($|.)");
  }

  @Test
  public void isSupportedLocaleShouldReturnTrueForHungarianLocale() {
    boolean result = this.service.isSupportedLocale("hu");

    Assert.assertTrue(result);
  }

  @Test
  public void isSupportedLocaleShouldReturnFalseForSpanishLocale() {
    boolean result = this.service.isSupportedLocale("sp");

    Assert.assertFalse(result);
  }

  @Test
  public void getLocalePropertiesAsStringShouldReturnFilteredProperties() {
    final String expectedProperties = "prop.js=javascript érték\n";
    String result = this.service.getLocalePropertiesAsString("hu");

    Assert.assertEquals(expectedProperties, result);
  }


}
