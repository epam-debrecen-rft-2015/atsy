package com.epam.rft.atsy.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;

public class ManageChannelsControllerTest extends AbstractControllerTest {
  private static final String VIEW_NAME = "manage_channels";

  private static final String REQUEST_URL = "/secure/channels/manage";

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{new ManageChannelsController()};
  }

  @Test
  public void loadPageShouldRenderManageChannelsView() throws Exception {
    mockMvc.perform(get(REQUEST_URL))
        .andExpect(status().isOk())
        .andExpect(view().name(VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX));
  }
}
