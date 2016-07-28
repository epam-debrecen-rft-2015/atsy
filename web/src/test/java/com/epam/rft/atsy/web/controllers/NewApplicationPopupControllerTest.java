package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.ApplicationsService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class NewApplicationPopupControllerTest extends AbstractControllerTest {

  private static String VIEW_NAME = "new_application_popup";
  private static String REQUEST_URL_GET = "/new_application_popup";
  private static String REQUEST_URL_POST = "/secure/new_application_popup";

  @Mock
  private ApplicationsService applicationsService;
  @InjectMocks
  private NewApplicationPopupController newApplicationPopupController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{newApplicationPopupController};
  }

  @Test
  public void loadPageShouldRenderNewApplicationPopupView() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL_GET))
        .andExpect(status().isOk())
        .andExpect(view().name(VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX));
  }


}
