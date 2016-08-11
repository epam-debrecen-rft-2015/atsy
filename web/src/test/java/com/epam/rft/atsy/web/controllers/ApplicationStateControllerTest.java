package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.StateFlowService;
import com.epam.rft.atsy.service.StateService;
import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryViewDTO;
import com.epam.rft.atsy.web.StateHistoryViewRepresentation;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.context.MessageSource;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationStateControllerTest extends AbstractControllerTest {

  private static final String REQUEST_URL = "/secure/application_state";
  private static final String APPLICATION_STATE = "candidate.table.state.";
  private static final String STATE_FLOW_OBJECT_KEY = "stateflows";
  private static final String STATES_OBJECT_KEY = "states";

  private static final String APPLICATION_ID = "applicationId";
  private static final String CLICKED_STATE = "state";
  private static final String TEXT = "text";

  private static final String ID_MINUS_ONE_STRING = "-1";
  private static final String ID_ONE_STRING = "1";

  private static final String CLICKED_STATE_NAME_CV = "cv";
  private static final String ERROR_VIEW_NAME = "error";


  private StateDTO stateDTO = StateDTO.builder().id(1L).name("cv").build();
  private StateHistoryViewDTO stateHistoryViewDTO = StateHistoryViewDTO.builder().build();
  private StateHistoryViewRepresentation stateHistoryViewRepresentation =
      StateHistoryViewRepresentation.builder().stateId(1L).stateName("cv").build();


  private List<StateHistoryViewDTO> stateHistoryViewDTOList = Collections.EMPTY_LIST;
  private List<StateHistoryViewRepresentation>
      stateHistoryViewRepresentationList =
      Arrays.asList(stateHistoryViewRepresentation);


  private final static Type
      STATE_HISTORY_VIEW_REPRESENTATION_LIST_TYPE =
      new TypeToken<List<StateHistoryViewRepresentation>>() {
      }.getType();

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
    given(statesHistoryService.getStateHistoriesByApplicationId(-1L))
        .willThrow(IllegalArgumentException.class);

    this.mockMvc.perform(get(REQUEST_URL).param(APPLICATION_ID, ID_MINUS_ONE_STRING))
        .andExpect(status().isInternalServerError())
        .andExpect(view().name(ERROR_VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + ERROR_VIEW_NAME + VIEW_SUFFIX));

    then(statesHistoryService).should().getStateHistoriesByApplicationId(-1L);
    verifyZeroInteractions(stateFlowService, stateService, messageSource, modelMapper);
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadPageShouldThrowIllegalArgumentExceptionWhenClickedStateIsNull() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL).param(APPLICATION_ID, ID_ONE_STRING).param(CLICKED_STATE, null));

    verifyZeroInteractions(statesHistoryService, stateFlowService, stateService, messageSource,
        modelMapper);
  }

  @Test
  public void loadPageShouldRespondInternalServerErrorWhenClickedStateIsAnEmptyString() throws Exception {
    given(statesHistoryService.getStateHistoriesByApplicationId(1L)).willReturn(stateHistoryViewDTOList);
    given(modelMapper.map(stateHistoryViewDTOList, STATE_HISTORY_VIEW_REPRESENTATION_LIST_TYPE))
        .willReturn(stateHistoryViewRepresentationList);
    given(stateService.getStateDtoByName(StringUtils.EMPTY)).willReturn(null);

    this.mockMvc.perform(get(REQUEST_URL).param(APPLICATION_ID, ID_ONE_STRING).param(CLICKED_STATE, StringUtils.EMPTY))
        .andExpect(status().isInternalServerError())
        .andExpect(view().name(ERROR_VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + ERROR_VIEW_NAME + VIEW_SUFFIX));

    then(statesHistoryService).should().getStateHistoriesByApplicationId(1L);
    then(modelMapper).should().map(stateHistoryViewDTOList, STATE_HISTORY_VIEW_REPRESENTATION_LIST_TYPE);
    then(stateService).should().getStateDtoByName(StringUtils.EMPTY);
    verifyZeroInteractions(stateFlowService, messageSource);
  }

  @Test
  public void loadPageShould() throws Exception {
    given(statesHistoryService.getStateHistoriesByApplicationId(1L)).willReturn(stateHistoryViewDTOList);
    given(modelMapper.map(stateHistoryViewDTOList, STATE_HISTORY_VIEW_REPRESENTATION_LIST_TYPE))
        .willReturn(stateHistoryViewRepresentationList);
    given(stateService.getStateDtoByName(CLICKED_STATE_NAME_CV)).willReturn(stateDTO);
    String stateType = CLICKED_STATE_NAME_CV;
    given(messageSource.getMessage(eq(APPLICATION_STATE + stateHistoryViewRepresentation.getStateName()),
        any(Object[].class), any(Locale.class))).willReturn("CV");

    //String stateType = messageSource
    //  .getMessage(APPLICATION_STATE + CLICKED_STATE_NAME_CV, new Object[]{CLICKED_STATE_NAME_CV}, Locale.ENGLISH);
    StateHistoryViewRepresentation stateHistoryViewRepresentation = StateHistoryViewRepresentation.builder()
        .id(1L).stateName(CLICKED_STATE_NAME_CV).stateFullName(stateType).build();
    List<StateHistoryViewRepresentation>
        stateHistoryViewRepresentationList =
        Arrays.asList(stateHistoryViewRepresentation);

    this.mockMvc
        .perform(get(REQUEST_URL).param(APPLICATION_ID, ID_ONE_STRING).param(CLICKED_STATE, CLICKED_STATE_NAME_CV))
        //.andExpect(model().attributeExists(STATE_FLOW_OBJECT_KEY))
        //.andExpect(model().attribute(STATE_FLOW_OBJECT_KEY, ""))
        //.andExpect(model().attribute(STATES_OBJECT_KEY, stateHistoryViewRepresentationList))
        .andDo(print());
  }
}
