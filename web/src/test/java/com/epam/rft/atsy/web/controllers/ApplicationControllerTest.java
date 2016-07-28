package com.epam.rft.atsy.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;

public class ApplicationControllerTest extends AbstractControllerTest {
  private static final String VIEW_NAME = "application";

  private static final String ERROR_VIEW_NAME = "error";

  private static final String REQUEST_URL = "/secure/application";

  private static final String CANDIDATE_ID_PARAM_NAME = "candidateId";

  private static final String CORRECT_CANDIDATE_ID_PARAM_VALUE = "1";

  private static final String MALFORMED_CANDIDATE_ID_PARAM_VALUE = "asd";

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{new ApplicationController()};
  }

  @Test
  public void loadPageShouldRenderApplicationViewWhenCandidateIdParamIsCorrect() throws Exception {
    mockMvc
        .perform(get(REQUEST_URL).param(CANDIDATE_ID_PARAM_NAME, CORRECT_CANDIDATE_ID_PARAM_VALUE))
        .andExpect(status().isOk())
        .andExpect(view().name(VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX))
        .andExpect(model().attributeExists(CANDIDATE_ID_PARAM_NAME))
        .andExpect(
            model().attribute(CANDIDATE_ID_PARAM_NAME,
                Long.valueOf(CORRECT_CANDIDATE_ID_PARAM_VALUE)));
  }

  @Test
  public void loadPageShouldRenderErrorViewWhenCandidateIdParamIsMalformed() throws Exception {
    mockMvc.perform(
        get(REQUEST_URL).param(CANDIDATE_ID_PARAM_NAME, MALFORMED_CANDIDATE_ID_PARAM_VALUE))
        .andExpect(status().isInternalServerError())
        .andExpect(view().name(ERROR_VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + ERROR_VIEW_NAME + VIEW_SUFFIX));
  }
}
