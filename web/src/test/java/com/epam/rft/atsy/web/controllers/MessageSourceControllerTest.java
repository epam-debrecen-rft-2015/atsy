package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.web.messageresolution.MessageSourceRepresentationService;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class MessageSourceControllerTest extends AbstractControllerTest {

  private static final String LOCALE_SUPPORTED = "hu";
  private static final String LOCALE_UNSUPPORTED = "es";
  private static final String REQUEST_URL_FOR_AVAILABLE_MESSAGES = "/messages_hu.properties";
  private static final String REQUEST_URL_FOR_UNAVAILABLE_MESSAGES = "/messages_es.properties";
  private static final String REQUEST_URL_FOR_DEFAULT_MESSAGES = "/messages.properties";
  private static final String EXPECTED_PROPERTIES = "prop1=value1\nprop2=value2";

  @Mock
  private MessageSourceRepresentationService messageSourceRepresentationService;

  @InjectMocks
  private MessageSourceController controller;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{controller};
  }

  @Test
  public void testMessagesIsAvailableOnSupportedLocalePath() throws Exception {
    given(this.messageSourceRepresentationService.isSupportedLocale(LOCALE_SUPPORTED)).willReturn(true);
    given(this.messageSourceRepresentationService.getLocalePropertiesAsString(LOCALE_SUPPORTED))
        .willReturn(EXPECTED_PROPERTIES);

    mockMvc.perform(get(REQUEST_URL_FOR_AVAILABLE_MESSAGES))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MessageSourceController.TEXT_PLAIN_CHARSET_UTF_8))
        .andExpect(content().string(EXPECTED_PROPERTIES));

    then(this.messageSourceRepresentationService).should().isSupportedLocale(LOCALE_SUPPORTED);
    then(this.messageSourceRepresentationService).should().getLocalePropertiesAsString(LOCALE_SUPPORTED);
  }

  @Test
  public void testMessagesIsAvailableOnNotExistingLocalePath() throws Exception {
    given(this.messageSourceRepresentationService.isSupportedLocale(LOCALE_UNSUPPORTED)).willReturn(false);

    mockMvc.perform(get(REQUEST_URL_FOR_UNAVAILABLE_MESSAGES))
        .andExpect(status().is4xxClientError());

    then(this.messageSourceRepresentationService).should().isSupportedLocale(LOCALE_UNSUPPORTED);
    then(this.messageSourceRepresentationService).should(never()).getLocalePropertiesAsString(anyString());
  }

  @Test
  public void getDefaultMessagesShouldReturnAnEmptyString() throws Exception {
    mockMvc.perform(get(REQUEST_URL_FOR_DEFAULT_MESSAGES))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MessageSourceController.TEXT_PLAIN_CHARSET_UTF_8))
        .andExpect(content().string(StringUtils.EMPTY));
  }
}
