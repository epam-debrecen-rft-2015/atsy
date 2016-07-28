package com.epam.rft.atsy.web.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.rft.atsy.service.AuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RootRedirectionControllerTest extends AbstractControllerTest {
  private static final String REQUEST_URL = "/";

  private static final String LOGIN_URL = "/login";

  private static final String WELCOME_URL = "/secure/welcome";

  @Mock
  private AuthenticationService authenticationService;

  @InjectMocks
  private RootRedirectionController rootRedirectionController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{rootRedirectionController};
  }

  @Test
  public void pageLoadShouldRedirectToLoginUrlWhenNotAuthenticated() throws Exception {
    given(authenticationService.isCurrentUserAuthenticated()).willReturn(false);

    mockMvc.perform(get(REQUEST_URL))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(LOGIN_URL));
  }

  @Test
  public void pageLoadShouldRedirectToWelcomeUrlWhenAuthenticated() throws Exception {
    given(authenticationService.isCurrentUserAuthenticated()).willReturn(true);

    mockMvc.perform(get(REQUEST_URL))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(WELCOME_URL));
  }
}
