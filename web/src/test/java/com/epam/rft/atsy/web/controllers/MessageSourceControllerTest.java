package com.epam.rft.atsy.web.controllers;

import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.rft.atsy.web.messageresolution.MessageSourceRepresentationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessageSourceControllerTest extends AbstractControllerTest {

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
    final String expectedProperties = "prop1=value1\nprop2=value2";
    given(this.messageSourceRepresentationService.isSupportedLocale("hu")).willReturn(true);
    given(this.messageSourceRepresentationService.getLocalePropertiesAsString("hu"))
        .willReturn(expectedProperties);

    mockMvc.perform(get("/messages_hu.properties"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MessageSourceController.TEXT_PLAIN_CHARSET_UTF_8))
        .andExpect(content().string(expectedProperties));
  }

  @Test
  public void testMessagesIsAvailableOnNotExistingLocalePath() throws Exception {
    given(this.messageSourceRepresentationService.isSupportedLocale("es")).willReturn(false);

    mockMvc.perform(get("/messages_es.properties"))
        .andExpect(status().is4xxClientError());

    then(this.messageSourceRepresentationService).should(never())
        .getLocalePropertiesAsString(anyString());
  }

}
