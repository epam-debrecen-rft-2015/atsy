package com.epam.rft.atsy.web.controllers;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.epam.rft.atsy.service.StateFlowService;
import com.epam.rft.atsy.service.StateService;
import com.epam.rft.atsy.service.StatesHistoryService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationStateControllerTest extends AbstractControllerTest {

  private static final String REQUEST_URL = "/secure/application_state";
  private static final String APPLICATION_ID = "applicationId";
  private static final String TEXT = "text";
  private static final String ID_STRING_MINUS_ONE = "-1";
  private static final Long ID_LONG_MINUS_ONE = -1L;
  private static final String ERROR_VIEW_NAME = "error";

  @Mock
  private StatesHistoryService statesHistoryService;
  @Mock
  private StateFlowService stateFlowService;
  @Mock
  private StateService stateService;
  @Mock
  private MessageSource messageSource;
  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private ApplicationStateController applicationStateController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{applicationStateController};
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadPageShouldThrowIllegalArgumentExceptionWhenApplicationIdIsNull()
      throws Exception {
    this.mockMvc.perform(get(REQUEST_URL).param(APPLICATION_ID, null));
  }

  @Test
  public void loadPageShouldRespondInternalServerErrorWhenApplicationIdIsAnEmptyString()
      throws Exception {

    given(statesHistoryService.getStateHistoriesByApplicationId(null))
        .willThrow(IllegalArgumentException.class);
    this.mockMvc.perform(get(REQUEST_URL).param(APPLICATION_ID, StringUtils.EMPTY))
        .andExpect(status().isInternalServerError())
        .andExpect(view().name(ERROR_VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + ERROR_VIEW_NAME + VIEW_SUFFIX));

    then(statesHistoryService).should().getStateHistoriesByApplicationId(null);
    verifyZeroInteractions(stateFlowService, stateService, messageSource, modelMapper);
  }

  @Test
  public void loadPageShouldRespondInternalServerErrorWhenApplicationIdIsAText() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL).param(APPLICATION_ID, TEXT))
        .andExpect(status().isInternalServerError())
        .andExpect(view().name(ERROR_VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + ERROR_VIEW_NAME + VIEW_SUFFIX));

    verifyZeroInteractions(statesHistoryService, stateFlowService, stateService, messageSource,
        modelMapper);
  }

  @Test
  public void loadPageShouldRespondInternalServerErrorWhenApplicationIdIsANegativeNumber()
      throws Exception {
    given(statesHistoryService.getStateHistoriesByApplicationId(ID_LONG_MINUS_ONE))
        .willThrow(IllegalArgumentException.class);

    this.mockMvc.perform(get(REQUEST_URL).param(APPLICATION_ID, ID_STRING_MINUS_ONE))
        .andExpect(status().isInternalServerError())
        .andExpect(view().name(ERROR_VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + ERROR_VIEW_NAME + VIEW_SUFFIX));

    then(statesHistoryService).should().getStateHistoriesByApplicationId(ID_LONG_MINUS_ONE);
    verifyZeroInteractions(stateFlowService, stateService, messageSource, modelMapper);
  }
}
