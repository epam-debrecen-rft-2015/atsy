package com.epam.rft.atsy.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PasswordChangeControllerTest extends AbstractControllerTest {
  private static final String REQUEST_URL = "/secure/password/manage";

  private static final String VIEW_NAME = "password_change";

  @Mock
  private PasswordChangeService passwordChangeService;

  @Mock
  private UserService userService;

  @Mock
  private PasswordValidator passwordValidator;

  @InjectMocks
  private PasswordChangeController passwordChangeController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{passwordChangeController};
  }

  @Test
  public void loadPageShouldRenderPasswordChangeView() throws Exception {
    mockMvc.perform(get(REQUEST_URL))
        .andExpect(status().isOk())
        .andExpect(view().name(VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX));
  }
}
