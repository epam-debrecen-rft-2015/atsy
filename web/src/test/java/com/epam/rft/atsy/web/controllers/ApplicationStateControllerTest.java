package com.epam.rft.atsy.web.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.StateFlowService;
import com.epam.rft.atsy.service.StateService;
import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateFlowDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryViewDTO;
import com.epam.rft.atsy.web.StateHistoryViewRepresentation;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationStateControllerTest extends AbstractControllerTest {

  private static final String REQUEST_URL = "/secure/application_state";
  private static final String STATE_FLOW_OBJECT_KEY = "stateflows";
  private static final String STATES_OBJECT_KEY = "states";
  private static final String APPLICATION_STATE = "candidate.table.state.";
  private static final String ERROR_VIEW_NAME = "error";

  private static final String PARAM_APPLICATION_ID = "applicationId";
  private static final String PARAM_CLICKED_STATE = "state";
  private static final String TEXT = "text";

  private static final String ID_FIRST_MINUS = "-1";
  private static final String ID_FIRST = "1";

  private static final String CLICKED_STATE_NAME_NEW_APPLY = "new.apply";
  private static final String CLICKED_STATE_NAME_CV = "cv";
  private static final String CLICKED_STATE_NAME_CODING = "coding";
  private static final String STATE_NAME_NEW_APPLY = "New apply";
  private static final String STATE_NAME_CV = "CV";
  private static final String STATE_NAME_CODING = "Coding";

  private StateDTO
      stateDTOWithStateNameNewApply =
      StateDTO.builder().id(1L).name(CLICKED_STATE_NAME_NEW_APPLY).build();
  private StateDTO
      stateDTOWithStateNameCoding =
      StateDTO.builder().id(3L).name(CLICKED_STATE_NAME_CODING).build();

  private StateHistoryViewRepresentation actualFirstStateHistoryViewRepresentation =
      StateHistoryViewRepresentation.builder().stateId(1L).stateName(CLICKED_STATE_NAME_NEW_APPLY)
          .build();
  private StateHistoryViewRepresentation actualSecondStateHistoryViewRepresentation =
      StateHistoryViewRepresentation.builder().stateId(2L).stateName(CLICKED_STATE_NAME_CV).build();


  private List<StateHistoryViewDTO> emptyStateHistoryViewDTOList = Collections.emptyList();
  private List<StateHistoryViewRepresentation>
      emptyStateHistoryViewRepresentationList =
      new LinkedList<>();
  private List<StateFlowDTO>
      stateFlowDTOListWithSingleElement =
      Collections.singletonList(new StateFlowDTO());
  private List<StateHistoryViewDTO> stateHistoryViewDTOListWithTwoElements =
      Collections.nCopies(2, new StateHistoryViewDTO());


  private StateHistoryViewRepresentation expectedFirstStateHistoryViewRepresentation =
      StateHistoryViewRepresentation.builder().stateId(1L).stateName(CLICKED_STATE_NAME_NEW_APPLY)
          .stateFullName(STATE_NAME_NEW_APPLY).build();
  private StateHistoryViewRepresentation expectedSecondStateHistoryViewRepresentation =
      StateHistoryViewRepresentation.builder().stateId(2L).stateName(CLICKED_STATE_NAME_CV)
          .stateFullName(STATE_NAME_CV).build();
  private StateHistoryViewRepresentation expectedThirdStateHistoryViewRepresentation =
      StateHistoryViewRepresentation.builder().stateId(3L).stateName(CLICKED_STATE_NAME_CODING)
          .stateFullName(STATE_NAME_CODING).build();


  private List<StateHistoryViewRepresentation>
      actualStateHistoryViewRepresentationListWithTwoElements =
      new ArrayList<>(Arrays
          .asList(actualSecondStateHistoryViewRepresentation,
              actualFirstStateHistoryViewRepresentation));

  private List<StateHistoryViewRepresentation>
      expectedStateHistoryViewRepresentationListWithSingleElement =
      new LinkedList<>(Arrays.asList(expectedFirstStateHistoryViewRepresentation));

  private List<StateHistoryViewRepresentation>
      expectedStateHistoryViewRepresentationListWithThreeElements =
      new LinkedList<>(Arrays.asList(expectedThirdStateHistoryViewRepresentation,
          expectedSecondStateHistoryViewRepresentation,
          expectedFirstStateHistoryViewRepresentation));

  @Mock
  private StatesHistoryService statesHistoryService;
  @Mock
  private StateFlowService stateFlowService;
  @Mock
  private StateService stateService;
  @Mock
  private MessageKeyResolver messageKeyResolver;
  @Mock
  private ConverterService converterService;

  @InjectMocks
  private ApplicationStateController applicationStateController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{applicationStateController};
  }

  @Test
  public void loadPageShouldRespondInternalServerErrorWhenParamApplicationIdIsAnEmptyString()
      throws Exception {
    given(statesHistoryService.getStateHistoriesByApplicationId(null))
        .willThrow(IllegalArgumentException.class);
    this.mockMvc.perform(get(REQUEST_URL).param(PARAM_APPLICATION_ID, StringUtils.EMPTY))
        .andExpect(status().isInternalServerError())
        .andExpect(view().name(ERROR_VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + ERROR_VIEW_NAME + VIEW_SUFFIX));

    then(statesHistoryService).should().getStateHistoriesByApplicationId(null);
    verifyZeroInteractions(stateFlowService, stateService, messageKeyResolver, converterService);
  }

  @Test
  public void loadPageShouldRespondInternalServerErrorWhenParamApplicationIdIsANegativeNumber()
      throws Exception {
    given(statesHistoryService.getStateHistoriesByApplicationId(-1L))
        .willThrow(IllegalArgumentException.class);

    this.mockMvc.perform(get(REQUEST_URL).param(PARAM_APPLICATION_ID, ID_FIRST_MINUS))
        .andExpect(status().isInternalServerError())
        .andExpect(view().name(ERROR_VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + ERROR_VIEW_NAME + VIEW_SUFFIX));

    then(statesHistoryService).should().getStateHistoriesByApplicationId(-1L);
    verifyZeroInteractions(stateFlowService, stateService, messageKeyResolver, converterService);
  }


  @Test
  public void loadPageShouldRespondInternalServerErrorWhenParamClickedStateIsAnEmptyString()
      throws Exception {
    given(statesHistoryService.getStateHistoriesByApplicationId(1L))
        .willReturn(emptyStateHistoryViewDTOList);

    given(converterService
        .convert(emptyStateHistoryViewDTOList, StateHistoryViewRepresentation.class))
        .willReturn(emptyStateHistoryViewRepresentationList);

    given(stateService.getStateDtoByName(StringUtils.EMPTY)).willReturn(null);

    this.mockMvc.perform(
        get(REQUEST_URL).param(PARAM_APPLICATION_ID, ID_FIRST)
            .param(PARAM_CLICKED_STATE, StringUtils.EMPTY))
        .andExpect(status().isInternalServerError())
        .andExpect(view().name(ERROR_VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + ERROR_VIEW_NAME + VIEW_SUFFIX));

    then(statesHistoryService).should().getStateHistoriesByApplicationId(1L);

    then(stateService).should().getStateDtoByName(StringUtils.EMPTY);
    verifyZeroInteractions(stateFlowService, messageKeyResolver);
  }

  @Test
  public void pageLoadShouldRespondInternalServerErrorWhenParamClickedStateIsAWrongText()
      throws Exception {
    given(statesHistoryService.getStateHistoriesByApplicationId(1L))
        .willReturn(emptyStateHistoryViewDTOList);

    given(converterService
        .convert(emptyStateHistoryViewDTOList, StateHistoryViewRepresentation.class))
        .willReturn(emptyStateHistoryViewRepresentationList);

    given(stateService.getStateDtoByName(TEXT)).willReturn(null);

    this.mockMvc
        .perform(
            get(REQUEST_URL).param(PARAM_APPLICATION_ID, ID_FIRST).param(PARAM_CLICKED_STATE, TEXT))
        .andExpect(status().isInternalServerError())
        .andExpect(view().name(ERROR_VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + ERROR_VIEW_NAME + VIEW_SUFFIX));

    then(statesHistoryService).should().getStateHistoriesByApplicationId(1L);

    then(stateService).should().getStateDtoByName(TEXT);
    verifyZeroInteractions(stateFlowService, messageKeyResolver);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void pageLoadShouldRespondModelAndViewWhenParamApplicationIdAndSParamClickedStateAreCorrectAndStateHistoryViewRepresentationListContainsSingleElement()
      throws Exception {
    given(statesHistoryService.getStateHistoriesByApplicationId(1L))
        .willReturn(emptyStateHistoryViewDTOList);

    given(converterService
        .convert(emptyStateHistoryViewDTOList, StateHistoryViewRepresentation.class))
        .willReturn(emptyStateHistoryViewRepresentationList);

    given(stateService.getStateDtoByName(CLICKED_STATE_NAME_NEW_APPLY))
        .willReturn(stateDTOWithStateNameNewApply);
    given(messageKeyResolver
        .resolveMessageOrDefault(APPLICATION_STATE + CLICKED_STATE_NAME_NEW_APPLY,
            CLICKED_STATE_NAME_NEW_APPLY)).willReturn(STATE_NAME_NEW_APPLY);
    given(stateFlowService.getStateFlowDTOByFromStateDTO(stateDTOWithStateNameNewApply))
        .willReturn(stateFlowDTOListWithSingleElement);

    MvcResult
        mvcResult =
        this.mockMvc.perform(get(REQUEST_URL).param(PARAM_APPLICATION_ID, ID_FIRST)
            .param(PARAM_CLICKED_STATE, CLICKED_STATE_NAME_NEW_APPLY))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists(PARAM_APPLICATION_ID))
            .andExpect(model().attribute(PARAM_APPLICATION_ID, 1L))
            .andExpect(model().attributeExists(STATE_FLOW_OBJECT_KEY))
            .andExpect(model().attribute(STATE_FLOW_OBJECT_KEY, stateFlowDTOListWithSingleElement))
            .andExpect(model().attributeExists(STATES_OBJECT_KEY))
            .andReturn();

    List<StateHistoryViewRepresentation> representationList =
        (List<StateHistoryViewRepresentation>) mvcResult.getModelAndView().getModel()
            .get(STATES_OBJECT_KEY);

    assertStateHistoryViewRepresentationList(representationList,
        expectedStateHistoryViewRepresentationListWithSingleElement);

    then(statesHistoryService).should().getStateHistoriesByApplicationId(1L);

    then(stateService).should().getStateDtoByName(CLICKED_STATE_NAME_NEW_APPLY);
    then(messageKeyResolver).should()
        .resolveMessageOrDefault(APPLICATION_STATE + CLICKED_STATE_NAME_NEW_APPLY,
            CLICKED_STATE_NAME_NEW_APPLY);
    then(stateFlowService).should().getStateFlowDTOByFromStateDTO(stateDTOWithStateNameNewApply);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void pageLoadShouldRespondModelAndViewWhenParamApplicationIdAndParamClickedStateAreCorrectAndStateHistoryViewRepresentationListContainsThreeElements()
      throws Exception {
    given(statesHistoryService.getStateHistoriesByApplicationId(1L))
        .willReturn(stateHistoryViewDTOListWithTwoElements);
    given(converterService
        .convert(stateHistoryViewDTOListWithTwoElements, StateHistoryViewRepresentation.class))
        .willReturn(actualStateHistoryViewRepresentationListWithTwoElements);
    given(stateService.getStateDtoByName(CLICKED_STATE_NAME_CODING))
        .willReturn(stateDTOWithStateNameCoding);
    given(stateFlowService.getStateFlowDTOByFromStateDTO(stateDTOWithStateNameCoding))
        .willReturn(stateFlowDTOListWithSingleElement);

    given(messageKeyResolver
        .resolveMessageOrDefault(APPLICATION_STATE + CLICKED_STATE_NAME_NEW_APPLY,
            CLICKED_STATE_NAME_NEW_APPLY)).willReturn(STATE_NAME_NEW_APPLY);
    given(messageKeyResolver
        .resolveMessageOrDefault(APPLICATION_STATE + CLICKED_STATE_NAME_CV, CLICKED_STATE_NAME_CV))
        .willReturn(STATE_NAME_CV);
    given(messageKeyResolver
        .resolveMessageOrDefault(APPLICATION_STATE + CLICKED_STATE_NAME_CODING,
            CLICKED_STATE_NAME_CODING)).willReturn(STATE_NAME_CODING);

    MvcResult mvcResult = this.mockMvc
        .perform(get(REQUEST_URL).param(PARAM_APPLICATION_ID, ID_FIRST)
            .param(PARAM_CLICKED_STATE, CLICKED_STATE_NAME_CODING))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists(PARAM_APPLICATION_ID))
        .andExpect(model().attribute(PARAM_APPLICATION_ID, 1L))
        .andExpect(model().attributeExists(STATE_FLOW_OBJECT_KEY))
        .andExpect(model().attribute(STATE_FLOW_OBJECT_KEY, stateFlowDTOListWithSingleElement))
        .andExpect(model().attributeExists(STATES_OBJECT_KEY))
        .andReturn();

    List<StateHistoryViewRepresentation> representationList =
        (List<StateHistoryViewRepresentation>) mvcResult.getModelAndView().getModel()
            .get(STATES_OBJECT_KEY);

    assertStateHistoryViewRepresentationList(representationList,
        expectedStateHistoryViewRepresentationListWithThreeElements);

    then(statesHistoryService).should().getStateHistoriesByApplicationId(1L);
    then(converterService).should()
        .convert(stateHistoryViewDTOListWithTwoElements, StateHistoryViewRepresentation.class);
    then(stateService).should().getStateDtoByName(CLICKED_STATE_NAME_CODING);
    then(stateFlowService).should().getStateFlowDTOByFromStateDTO(stateDTOWithStateNameCoding);

    then(messageKeyResolver).should()
        .resolveMessageOrDefault(APPLICATION_STATE + CLICKED_STATE_NAME_NEW_APPLY,
            CLICKED_STATE_NAME_NEW_APPLY);
    then(messageKeyResolver).should()
        .resolveMessageOrDefault(APPLICATION_STATE + CLICKED_STATE_NAME_CV, CLICKED_STATE_NAME_CV);
    then(messageKeyResolver).should()
        .resolveMessageOrDefault(APPLICATION_STATE + CLICKED_STATE_NAME_CODING,
            CLICKED_STATE_NAME_CODING);
  }

  private void assertStateHistoryViewRepresentationList(
      List<StateHistoryViewRepresentation> actualList,
      List<StateHistoryViewRepresentation> expectedList) {
    assertThat(actualList.size(), equalTo(expectedList.size()));

    // Creation date is only set on the first element
    Date currentDate = new Date();

    assertThat(actualList.get(0).getCreationDate(), lessThanOrEqualTo(currentDate));

    for (int i = 0; i < actualList.size(); ++i) {
      assertStateHistoryViewRepresentation(actualList.get(i), expectedList.get(i));
    }
  }

  private void assertStateHistoryViewRepresentation(
      StateHistoryViewRepresentation actualRepresentation,
      StateHistoryViewRepresentation expectedRepresentation) {
    assertThat(actualRepresentation.getStateId(), equalTo(expectedRepresentation.getStateId()));

    assertThat(actualRepresentation.getStateName(), equalTo(expectedRepresentation.getStateName()));

    assertThat(actualRepresentation.getStateFullName(),
        equalTo(expectedRepresentation.getStateFullName()));
  }
}