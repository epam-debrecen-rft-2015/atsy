package com.epam.rft.atsy.web.controllers.rest;


import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import com.epam.rft.atsy.web.FieldErrorResponseComposer;
import com.epam.rft.atsy.web.StateHistoryViewRepresentation;
import com.epam.rft.atsy.web.controllers.AbstractControllerTest;
import com.epam.rft.atsy.web.exceptionhandling.RestResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class StateHistoryControllerTest extends AbstractControllerTest {
  private static final Long APPLICATION_ID = 1L;

  private static final String REQUEST_BASE_URL = "/secure/application_state";

  private static final String REQUEST_URL =
      REQUEST_BASE_URL + "?applicationId=" + APPLICATION_ID.toString();

  private static final String COMMON_INVALID_INPUT_MESSAGE_KEY = "common.invalid.input";

  private static final String DATE_PARSE_ERROR_MESSAGE_KEY = "statehistory.error.parse.date";

  private static final String CLAIM_NEGATIVE_MESSAGE_KEY = "statehistory.error.claim.negative";

  private static final String OFFERED_MONEY_NEGATIVE_MESSAGE_KEY =
      "statehistory.error.offeredMoney.negative";

  private static final String POSITION_NOT_FOUND_MESSAGE_KEY = "position.not.found.error.message";

  private static final String CHANNEL_NOT_FOUND_MESSAGE_KEY = "channel.not.found.error.message";

  private static final String JSON_PATH_ERROR_MESSAGE = "$.errorMessage";

  private static final String JSON_PATH_APPLICATION_ID = "$.applicationId";

  private static final String JSON_PATH_FEEDBACK_DATE = "$.fields.feedbackDate";

  private static final String JSON_PATH_FIELD_CLAIM = "$.fields.claim";

  private static final String JSON_PATH_FIELD_OFFERED_MONEY = "$.fields.offeredMoney";

  private static final String JSON_PATH_FIELD_POSITION_NAME = "$.fields.positionName";

  private static final String JSON_PATH_FIELD_CHANNEL_NAME = "$.fields.channelName";

  private static final String FIELD_FEEDBACK_DATE = "feedbackDate";

  private static final String FIELD_CLAIM = "claim";

  private static final String FIELD_OFFERED_MONEY = "offeredMoney";

  private static final String FIELD_NAME_CHANNEL = "channelName";

  private static final String FIELD_NAME_POSITION = "positionName";

  private static final String MALFORMED_DATE = "malformed";

  private static final Long NEGATIVE_CLAIM = -1L;

  private static final Long NEGATIVE_OFFERED_MONEY = -1L;

  private static final int RECOMMENDATION_YES_INTEGER = 1;

  private static final boolean RECOMMENDATION_YES_BOOL = true;

  private StateHistoryViewRepresentation dummyStateHistory;

  private StateHistoryViewRepresentation dummyStateHistoryWithNewState;

  private StateHistoryDTO dummyStateHistoryDto;

  private StateHistoryDTO dummyStateHistoryDtoWithNewState;

  private ApplicationDTO dummyApplicationDto;

  private PositionDTO positionDTO;

  private ChannelDTO channelDTO;

  private static final Long ID = 1L;

  private static final Date CREATION_DATE =

      new GregorianCalendar(2016, 8 - 1, 15, 14, 0, 0).getTime();

  private static final String FEEDBACK_DATE_STRING = "2016-08-15 14:00:00";

  private static final Date
      FEEDBACK_DATE =
      new GregorianCalendar(2016, 8 - 1, 15, 14, 0, 0).getTime();

  private static final Date
      DAY_OF_START =
      new GregorianCalendar(2016, 8 - 1, 15, 0, 0, 0).getTime();

  private static final Short LANGUAGE_SKILL = 10;

  private static final String DESCRIPTION = "description";

  private static final Short RESULT = 45;

  private static final Long OFFERED_MONEY = 1L;

  private static final Long CLAIM = 1L;

  private static final String STATE_FULL_NAME = "full name";


  private static final Long STATE_ID = 2L;

  private static final Long STATE_ID_FOR_NEW_STATE = 1L;

  private static final String STATE_NAME = "name";

  private static final Long CANDIDATE_ID = 1L;

  private static final String POSITION_NAME_NON_EXISTENT = "Football instructor";

  private static final String CHANNEL_NAME_NON_EXISTENT = "Digisport channel";

  private static final String POSITION_NAME = "Developer";

  private static final String CHANNEL_NAME = "Facebook";

  @Mock
  private StatesHistoryService statesHistoryService;

  @Mock
  private ApplicationsService applicationsService;

  @Mock
  private ChannelService channelService;

  @Mock
  private PositionService positionService;

  @Mock
  private FieldErrorResponseComposer fieldErrorResponseComposer;

  @InjectMocks
  private StateHistoryController stateHistoryController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{stateHistoryController};
  }

  @Override
  public void setUp() {
    super.setUp();

    positionDTO = PositionDTO.builder()
        .id(ID)
        .name(POSITION_NAME)
        .build();

    channelDTO = ChannelDTO.builder()
        .id(ID)
        .name(CHANNEL_NAME)
        .build();

    dummyApplicationDto = ApplicationDTO.builder()
        .id(APPLICATION_ID)
        .candidateId(CANDIDATE_ID)
        .build();

    dummyStateHistory = StateHistoryViewRepresentation.builder()
        .id(ID)
        .candidateId(CANDIDATE_ID)
        .creationDate(CREATION_DATE)
        .feedbackDate(FEEDBACK_DATE_STRING)
        .languageSkill(LANGUAGE_SKILL)
        .description(DESCRIPTION)
        .result(RESULT)
        .offeredMoney(OFFERED_MONEY)
        .claim(CLAIM)
        .dayOfStart(DAY_OF_START)
        .stateFullName(STATE_FULL_NAME)
        .stateId(STATE_ID)
        .stateName(STATE_NAME)
        .recommendation(RECOMMENDATION_YES_INTEGER)
        .build();

    dummyStateHistoryWithNewState = StateHistoryViewRepresentation.builder()
        .id(ID)
        .candidateId(CANDIDATE_ID)
        .positionName(POSITION_NAME)
        .channelName(CHANNEL_NAME)
        .creationDate(CREATION_DATE)
        .feedbackDate(FEEDBACK_DATE_STRING)
        .languageSkill(LANGUAGE_SKILL)
        .description(DESCRIPTION)
        .result(RESULT)
        .offeredMoney(OFFERED_MONEY)
        .claim(CLAIM)
        .dayOfStart(DAY_OF_START)
        .stateFullName(STATE_FULL_NAME)
        .stateId(STATE_ID_FOR_NEW_STATE)
        .stateName(STATE_NAME)
        .recommendation(RECOMMENDATION_YES_INTEGER)
        .build();

    StateDTO dummyStateDto = StateDTO.builder()
        .id(STATE_ID)
        .name(STATE_NAME)
        .build();

    dummyStateHistoryDto = StateHistoryDTO.builder()
        .id(ID)
        .applicationDTO(dummyApplicationDto)
        .candidateId(CANDIDATE_ID)
        .feedbackDate(FEEDBACK_DATE)
        .description(DESCRIPTION)
        .result(RESULT)
        .offeredMoney(OFFERED_MONEY)
        .claim(CLAIM)
        .dayOfStart(DAY_OF_START)
        .stateDTO(dummyStateDto)
        .recommendation(RECOMMENDATION_YES_BOOL)
        .build();

    dummyStateHistoryDtoWithNewState = StateHistoryDTO.builder()
        .id(ID)
        .applicationDTO(dummyApplicationDto)
        .channel(channelDTO)
        .position(positionDTO)
        .candidateId(CANDIDATE_ID)
        .feedbackDate(FEEDBACK_DATE)
        .description(DESCRIPTION)
        .result(RESULT)
        .offeredMoney(OFFERED_MONEY)
        .claim(CLAIM)
        .dayOfStart(DAY_OF_START)
        .stateDTO(StateDTO.builder().id(STATE_ID_FOR_NEW_STATE).name(STATE_NAME).build())
        .recommendation(RECOMMENDATION_YES_BOOL)
        .build();
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorJSONWhenFeedbackDateIsMalformed() throws Exception {
    StateHistoryViewRepresentation stateHistoryViewRepresentation =
        StateHistoryViewRepresentation.builder().feedbackDate(MALFORMED_DATE).build();

    given(fieldErrorResponseComposer.composeResponse(any(BindingResult.class)))

        .willReturn(composeResponseFromField(FIELD_FEEDBACK_DATE, DATE_PARSE_ERROR_MESSAGE_KEY));

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, stateHistoryViewRepresentation))
        .andExpect(status().isBadRequest())

        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(COMMON_INVALID_INPUT_MESSAGE_KEY))
        .andExpect(jsonPath(JSON_PATH_FEEDBACK_DATE).value(DATE_PARSE_ERROR_MESSAGE_KEY));

    verifyZeroInteractions(statesHistoryService, channelService, positionService, applicationsService);
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorJSONWhenClaimIsNegative() throws Exception {
    StateHistoryViewRepresentation stateHistoryViewRepresentation =
        StateHistoryViewRepresentation.builder().claim(NEGATIVE_CLAIM).build();

    given(fieldErrorResponseComposer.composeResponse(any(BindingResult.class)))

        .willReturn(composeResponseFromField(FIELD_CLAIM, CLAIM_NEGATIVE_MESSAGE_KEY));

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, stateHistoryViewRepresentation))
        .andExpect(status().isBadRequest())

        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(COMMON_INVALID_INPUT_MESSAGE_KEY))
        .andExpect(jsonPath(JSON_PATH_FIELD_CLAIM).value(CLAIM_NEGATIVE_MESSAGE_KEY));

    verifyZeroInteractions(statesHistoryService, channelService, positionService, applicationsService);
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorJSONWhenOfferedMoneyIsNegative() throws Exception {
    StateHistoryViewRepresentation stateHistoryViewRepresentation =
        StateHistoryViewRepresentation.builder().offeredMoney(NEGATIVE_OFFERED_MONEY).build();

    given(fieldErrorResponseComposer.composeResponse(any(BindingResult.class)))

        .willReturn(composeResponseFromField(FIELD_OFFERED_MONEY, OFFERED_MONEY_NEGATIVE_MESSAGE_KEY));

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, stateHistoryViewRepresentation))
        .andExpect(status().isBadRequest())

        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(COMMON_INVALID_INPUT_MESSAGE_KEY))
        .andExpect(jsonPath(JSON_PATH_FIELD_OFFERED_MONEY).value(OFFERED_MONEY_NEGATIVE_MESSAGE_KEY));

    verifyZeroInteractions(statesHistoryService, channelService, positionService, applicationsService);
  }

  @Test
  public void saveOrUpdateShouldRespondWithApplicationIdWhenPostHasNoErrors() throws Exception {
    given(applicationsService.getApplicationDtoById(APPLICATION_ID)).willReturn(dummyApplicationDto);

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, dummyStateHistory))
        .andExpect(status().isOk())

        .andExpect(jsonPath(JSON_PATH_APPLICATION_ID).value(APPLICATION_ID.intValue()));

    ArgumentCaptor<StateHistoryDTO> historyCaptor = ArgumentCaptor.forClass(StateHistoryDTO.class);

    verify(statesHistoryService).saveStateHistory(historyCaptor.capture());
    assertThat(historyCaptor.getValue(), equalTo(dummyStateHistoryDto));

    then(applicationsService).should().getApplicationDtoById(APPLICATION_ID);
    then(statesHistoryService).should().saveStateHistory(any(StateHistoryDTO.class));
    verifyZeroInteractions(fieldErrorResponseComposer, channelService, positionService);
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorJSONWhenPositionNotExists() throws Exception {
    StateHistoryViewRepresentation stateHistoryViewRepresentation =
        StateHistoryViewRepresentation.builder().stateId(STATE_ID_FOR_NEW_STATE)
            .positionName(POSITION_NAME_NON_EXISTENT).build();

    given(positionService.getPositionDtoByName(POSITION_NAME_NON_EXISTENT)).willReturn(null);
    given(fieldErrorResponseComposer.composeResponse(any(BindingResult.class)))
        .willReturn(composeResponseFromField(FIELD_NAME_POSITION, POSITION_NOT_FOUND_MESSAGE_KEY));

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, stateHistoryViewRepresentation))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(COMMON_INVALID_INPUT_MESSAGE_KEY))
        .andExpect(jsonPath(JSON_PATH_FIELD_POSITION_NAME).value(POSITION_NOT_FOUND_MESSAGE_KEY));

    then(positionService).should().getPositionDtoByName(POSITION_NAME_NON_EXISTENT);
    then(channelService).should().getChannelDtoByName(any(String.class));
    then(fieldErrorResponseComposer).should().composeResponse(any(BindingResult.class));
    verifyZeroInteractions(applicationsService, statesHistoryService);
    verifyNoMoreInteractions(positionService, channelService);
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorJSONWhenChannelNotExists() throws Exception {
    StateHistoryViewRepresentation stateHistoryViewRepresentation =
        StateHistoryViewRepresentation.builder().stateId(STATE_ID_FOR_NEW_STATE)
            .channelName(CHANNEL_NAME_NON_EXISTENT).build();

    given(channelService.getChannelDtoByName(CHANNEL_NAME_NON_EXISTENT)).willReturn(null);
    given(fieldErrorResponseComposer.composeResponse(any(BindingResult.class)))
        .willReturn(composeResponseFromField(FIELD_NAME_CHANNEL, CHANNEL_NOT_FOUND_MESSAGE_KEY));

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, stateHistoryViewRepresentation))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(COMMON_INVALID_INPUT_MESSAGE_KEY))
        .andExpect(jsonPath(JSON_PATH_FIELD_CHANNEL_NAME).value(CHANNEL_NOT_FOUND_MESSAGE_KEY));

    then(positionService).should().getPositionDtoByName(any(String.class));
    then(channelService).should().getChannelDtoByName(CHANNEL_NAME_NON_EXISTENT);
    then(fieldErrorResponseComposer).should().composeResponse(any(BindingResult.class));
    verifyZeroInteractions(applicationsService, statesHistoryService);
    verifyNoMoreInteractions(positionService, channelService);
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorJSONWhenPositionAndChannelNotExist() throws Exception {
    StateHistoryViewRepresentation stateHistoryViewRepresentation =
        StateHistoryViewRepresentation.builder().stateId(STATE_ID_FOR_NEW_STATE)
            .positionName(POSITION_NAME_NON_EXISTENT).channelName(CHANNEL_NAME_NON_EXISTENT).build();

    given(positionService.getPositionDtoByName(POSITION_NAME_NON_EXISTENT)).willReturn(null);
    given(channelService.getChannelDtoByName(CHANNEL_NAME_NON_EXISTENT)).willReturn(null);
    given(fieldErrorResponseComposer.composeResponse(any(BindingResult.class)))
        .willReturn(composeResponseFromField(FIELD_NAME_POSITION, POSITION_NOT_FOUND_MESSAGE_KEY,
            FIELD_NAME_CHANNEL, CHANNEL_NOT_FOUND_MESSAGE_KEY));

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, stateHistoryViewRepresentation))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath(JSON_PATH_ERROR_MESSAGE).value(COMMON_INVALID_INPUT_MESSAGE_KEY))
        .andExpect(jsonPath(JSON_PATH_FIELD_POSITION_NAME).value(POSITION_NOT_FOUND_MESSAGE_KEY))
        .andExpect(jsonPath(JSON_PATH_FIELD_CHANNEL_NAME).value(CHANNEL_NOT_FOUND_MESSAGE_KEY));

    then(positionService).should().getPositionDtoByName(POSITION_NAME_NON_EXISTENT);
    then(channelService).should().getChannelDtoByName(CHANNEL_NAME_NON_EXISTENT);
    then(fieldErrorResponseComposer).should().composeResponse(any(BindingResult.class));
    verifyZeroInteractions(applicationsService, statesHistoryService);
    verifyNoMoreInteractions(positionService, channelService);
  }

  @Test
  public void saveOrUpdateShouldRespondWithApplicationIdWhenPositionAndChannelExist() throws Exception {
    given(positionService.getPositionDtoByName(POSITION_NAME)).willReturn(positionDTO);
    given(channelService.getChannelDtoByName(CHANNEL_NAME)).willReturn(channelDTO);
    given(applicationsService.getApplicationDtoById(APPLICATION_ID)).willReturn(dummyApplicationDto);

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, dummyStateHistoryWithNewState))
        .andExpect(status().isOk())
        .andExpect(jsonPath(JSON_PATH_APPLICATION_ID).value(APPLICATION_ID.intValue()));

    ArgumentCaptor<StateHistoryDTO> historyCaptor = ArgumentCaptor.forClass(StateHistoryDTO.class);
    verify(statesHistoryService).saveStateHistory(historyCaptor.capture());
    assertThat(historyCaptor.getValue(), equalTo(dummyStateHistoryDtoWithNewState));

    then(applicationsService).should().getApplicationDtoById(APPLICATION_ID);
    then(positionService).should(times(2)).getPositionDtoByName(POSITION_NAME);
    then(channelService).should(times(2)).getChannelDtoByName(CHANNEL_NAME);
    then(statesHistoryService).should().saveStateHistory(any(StateHistoryDTO.class));
    verifyZeroInteractions(fieldErrorResponseComposer);
  }

  private ResponseEntity<RestResponse> composeResponseFromField(String fieldName,
                                                                String fieldValue) {
    RestResponse restResponse = new RestResponse(COMMON_INVALID_INPUT_MESSAGE_KEY);

    restResponse.addField(fieldName, fieldValue);

    return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
  }

  private ResponseEntity<RestResponse> composeResponseFromField(String firstFieldName, String firstFieldValue,
                                                                String secondFieldName, String secondFieldValue) {
    RestResponse restResponse = new RestResponse(COMMON_INVALID_INPUT_MESSAGE_KEY);

    restResponse.addField(firstFieldName, firstFieldValue);
    restResponse.addField(secondFieldName, secondFieldValue);

    return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
  }
}
