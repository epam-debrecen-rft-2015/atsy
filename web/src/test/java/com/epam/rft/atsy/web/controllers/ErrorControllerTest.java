package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.web.util.WebUtils;

@RunWith(MockitoJUnitRunner.class)
public class ErrorControllerTest extends AbstractControllerTest {
  private static final String VIEW_NAME_ERROR = "error";

  private static final String NOT_FOUND_REQUEST_URL = "/error/404";
  
  private static final String HTTP_NOT_FOUND_ERROR_MESSAGE_KEY = "http.not.found.error.message";
  
  private static final String HTTP_NOT_FOUND_ERROR_MESSAGE = "The requested page was not found!";

  @Mock
  private MessageKeyResolver messageKeyResolver;
  
  @InjectMocks
  private ErrorController errorController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{errorController};
  }

  @Test
  public void pageLoadShouldRenderHttpNotFoundErrorPage() throws Exception {
    given(messageKeyResolver
        .resolveMessageOrDefault(HTTP_NOT_FOUND_ERROR_MESSAGE_KEY))
            .willReturn(HTTP_NOT_FOUND_ERROR_MESSAGE);

    mockMvc.perform(
        get(NOT_FOUND_REQUEST_URL))
        .andExpect(status().isOk())
        .andExpect(view().name(VIEW_NAME_ERROR))
        .andExpect(model().attribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE, HttpServletResponse.SC_NOT_FOUND))
        .andExpect(model().attribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE, HTTP_NOT_FOUND_ERROR_MESSAGE));

    then(messageKeyResolver).should()
        .resolveMessageOrDefault(HTTP_NOT_FOUND_ERROR_MESSAGE_KEY);
  }
}
