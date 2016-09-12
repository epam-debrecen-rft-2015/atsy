package com.epam.rft.atsy.web.messageresolution;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyString;

public class SpringMessageSourceRepresentationServiceTest {

  private static final String LOCALE_HU_AS_STRING = "hu";
  private static final String LOCALE_EN_AS_STRING = "en";
  private static final String LOCALE_DE_AS_STRING = "de";
  private static final String LOCALE_ES_AS_STRING = "es";

  private static final String EXPECTED_FILTERED_PROPERTY_LOCALE_HU = "prop3.js=javascript érték3\n";
  private static final String EXPECTED_FILTERED_PROPERTIES_LOCALE_DE =
      "prop5.js=javascript wert5\n" + "prop4.js=javascript wert4\n" + "prop3.js=javascript wert3\n";

  private AtsyReloadableResourceBundleMessageSource internalMessageSource;
  private SpringMessageSourceRepresentationService service;

  @Before
  public void setup() {
    this.internalMessageSource = new AtsyReloadableResourceBundleMessageSource();
    this.internalMessageSource.setBasename("classpath:/test-i18n/messages");
    this.internalMessageSource.setDefaultEncoding(CharEncoding.UTF_8);
    this.internalMessageSource.setFallbackToSystemLocale(false);

    this.service = new SpringMessageSourceRepresentationService(this.internalMessageSource,
        SpringMessageSourceRepresentationService.DEFAULT_PATTERN_AS_STRING);
  }

  @Test(expected = IllegalArgumentException.class)
  public void isSupportedLocaleShouldThrowIllegalArgumentExceptionWhenLocaleParamIsNull() {
    this.service.isSupportedLocale(null);
  }

  @Test
  public void isSupportedLocaleShouldThrowIllegalArgumentExceptionWhenLocaleParamIsEmpty() {
    boolean result = this.service.isSupportedLocale(StringUtils.EMPTY);

    assertThat(result, is(false));
  }

  @Test
  public void isSupportedLocaleShouldReturnFalseForSpanishLocaleWhenLocaleParamIsSpanish() {
    boolean result = this.service.isSupportedLocale(LOCALE_ES_AS_STRING);

    assertThat(result, is(false));
  }

  @Test
  public void isSupportedLocaleShouldReturnTrueWhenLocaleParamIsHungarian() {
    boolean result = this.service.isSupportedLocale(LOCALE_HU_AS_STRING);

    assertThat(result, is(true));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getLocalePropertiesAsStringShouldThrowIllegalArgumentExceptionWhenLocaleParamIsNull() {
    this.service.getLocalePropertiesAsString(null);
  }

  @Test
  public void getLocalePropertiesAsStringShouldReturnEmptyStringWhenLocaleParamIsEmpty() {
    String result = this.service.getLocalePropertiesAsString(StringUtils.EMPTY);

    assertThat(result, notNullValue());
    assertThat(result, isEmptyString());
  }

  @Test
  public void getLocalePropertiesAsStringShouldReturnEmptyStringWhenLocaleParamIsEnglish() {
    String result = this.service.getLocalePropertiesAsString(LOCALE_EN_AS_STRING);

    assertThat(result, notNullValue());
    assertThat(result, isEmptyString());
  }

  @Test
  public void getLocalePropertiesAsStringShouldReturnSingleFilteredPropertyWhenLocaleParamIsHungarian() {
    String result = this.service.getLocalePropertiesAsString(LOCALE_HU_AS_STRING);

    assertThat(result, notNullValue());
    assertThat(result, equalTo(EXPECTED_FILTERED_PROPERTY_LOCALE_HU));
  }

  @Test
  public void getLocalePropertiesAsStringShouldReturnThreeFilteredPropertiesWhenLocaleParamIsGerman() {
    String result = this.service.getLocalePropertiesAsString(LOCALE_DE_AS_STRING);

    assertThat(result, notNullValue());
    assertThat(result, equalTo(EXPECTED_FILTERED_PROPERTIES_LOCALE_DE));
  }
}
