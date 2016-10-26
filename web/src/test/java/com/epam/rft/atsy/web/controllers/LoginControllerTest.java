package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.AuthenticationService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest extends AbstractControllerTest {
  private static final String VIEW_NAME = "login";

  private static final String REQUEST_URL = "/login";

  @Mock
  private AuthenticationService authenticationService;

  @InjectMocks
  private LoginController loginController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{loginController};
  }

  @Test
  public void pageLoadShouldRenderLoginView() throws Exception {
    given(this.authenticationService.isCurrentUserAuthenticated()).willReturn(false);

    mockMvc.perform(get(REQUEST_URL))
        .andExpect(status().isOk())
        .andExpect(view().name(VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX));
  }
}
