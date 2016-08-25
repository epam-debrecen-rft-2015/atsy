package com.epam.rft.atsy.web.controllers.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.rft.atsy.service.StatesHistoryService;
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

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;

@RunWith(MockitoJUnitRunner.class)
public class StateHistoryControllerTest extends AbstractControllerTest {
  private static final Long APPLICATION_ID = 1L;

  private static final String REQUEST_BASE_URL = "/secure/application_state";

  private static final String REQUEST_URL =
      REQUEST_BASE_URL + "?applicationId=" + APPLICATION_ID.toString();

  private static final String COMMON_INVALID_INPUT_MESSAGE_KEY = "common.invalid.input";

  private static final String DATE_PARSE_ERROR_MESSAGE_KEY = "statehistory.error.parse.date";

  private static final String LANGUAGE_SKILL_INCORRECT_MESSAGE_KEY =
      "candidate.error.language.incorrect";

  private static final String CLAIM_NEGATIVE_MESSAGE_KEY = "statehistory.error.claim.negative";

  private static final String OFFERED_MONEY_NEGATIVE_MESSAGE_KEY =
      "statehistory.error.offeredMoney.negative";

  private static final String DATE_FORMAT_CONSTANT = "yyyy-MM-dd HH:mm:ss";

  private static final String MALFORMED_DATE = "malformed";

  private static final Short LOWER_LANGUAGE_SKILL = -1;

  private static final Short HIGHER_LANGUAGE_SKILL = 11;

  private static final Long NEGATIVE_CLAIM = -1L;

  private static final Long NEGATIVE_OFFERED_MONEY = -1L;

  private StateHistoryViewRepresentation dummyStateHistory;

  private StateHistoryDTO dummyStateHistoryDto;

  private static final DateTimeFormatter DATE_FORMATTER =
      DateTimeFormatter.ofPattern(DATE_FORMAT_CONSTANT);

  private static final Long ID = 1L;

  private static final String CREATION_DATE_STRING = "2016-08-15 14:00:00";

  private static final Date
      CREATION_DATE =
      new GregorianCalendar(2016, 8 - 1, 15, 14, 0, 0).getTime();

  private static final String FEEDBACK_DATE_STRING = "2016-08-15 14:00:00";

  private static final Date
      FEEDBACK_DATE =
      new GregorianCalendar(2016, 8 - 1, 15, 14, 0, 0).getTime();

  private static final String DAY_OF_START_STRING = "2016-08-15 14:00:00";

  private static final Date
      DAY_OF_START =
      new GregorianCalendar(2016, 8 - 1, 15, 14, 0, 0).getTime();

  private static final Short LANGUAGE_SKILL = 10;

  private static final String DESCRIPTION = "description";

  private static final String RESULT = "result";

  private static final Long OFFERED_MONEY = 1L;

  private static final Long CLAIM = 1L;

  private static final String STATE_FULL_NAME = "full name";

  private static final Long STATE_ID = 1L;

  private static final String STATE_NAME = "name";

  private static final Long CANDIDATE_ID = 1L;

  @Mock
  private StatesHistoryService statesHistoryService;

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

    dummyStateHistory = StateHistoryViewRepresentation.builder()
        .id(ID)
        .candidateId(CANDIDATE_ID)
        .creationDate(CREATION_DATE_STRING)
        .feedbackDate(FEEDBACK_DATE_STRING)
        .languageSkill(LANGUAGE_SKILL)
        .description(DESCRIPTION)
        .result(RESULT)
        .offeredMoney(OFFERED_MONEY)
        .claim(CLAIM)
        .dayOfStart(DAY_OF_START_STRING)
        .stateFullName(STATE_FULL_NAME)
        .stateId(STATE_ID)
        .stateName(STATE_NAME)
        .build();

    StateDTO dummyStateDto = StateDTO.builder()
        .id(STATE_ID)
        .name(STATE_NAME)
        .build();

    dummyStateHistoryDto = StateHistoryDTO.builder()
        .id(ID)
        .candidateId(CANDIDATE_ID)
        .creationDate(CREATION_DATE)
        .feedbackDate(FEEDBACK_DATE)
        .languageSkill(LANGUAGE_SKILL)
        .description(DESCRIPTION)
        .result(RESULT)
        .offeredMoney(OFFERED_MONEY)
        .claim(CLAIM)
        .dayOfStart(DAY_OF_START)
        .stateDTO(dummyStateDto)
        .build();
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorJSONWhenCreateDateIsMalformed() throws Exception {
    StateHistoryViewRepresentation stateHistoryViewRepresentation =
        StateHistoryViewRepresentation.builder().creationDate(MALFORMED_DATE).build();

    given(fieldErrorResponseComposer.composeResponse(any(BindingResult.class)))
        .willReturn(composeResponseFromField("creationDate", DATE_PARSE_ERROR_MESSAGE_KEY));

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, stateHistoryViewRepresentation))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorMessage").value(COMMON_INVALID_INPUT_MESSAGE_KEY))
        .andExpect(jsonPath("$.fields.creationDate").value(DATE_PARSE_ERROR_MESSAGE_KEY));

    verifyZeroInteractions(statesHistoryService);
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorJSONWhenFeedbackDateIsMalformed() throws Exception {
    StateHistoryViewRepresentation stateHistoryViewRepresentation =
        StateHistoryViewRepresentation.builder().feedbackDate(MALFORMED_DATE).build();

    given(fieldErrorResponseComposer.composeResponse(any(BindingResult.class)))
        .willReturn(composeResponseFromField("feedbackDate", DATE_PARSE_ERROR_MESSAGE_KEY));

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, stateHistoryViewRepresentation))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorMessage").value(COMMON_INVALID_INPUT_MESSAGE_KEY))
        .andExpect(jsonPath("$.fields.feedbackDate").value(DATE_PARSE_ERROR_MESSAGE_KEY));

    verifyZeroInteractions(statesHistoryService);
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorJSONWhenLanguageSkillIsLower() throws Exception {
    StateHistoryViewRepresentation stateHistoryViewRepresentation =
        StateHistoryViewRepresentation.builder().languageSkill(LOWER_LANGUAGE_SKILL).build();

    given(fieldErrorResponseComposer.composeResponse(any(BindingResult.class)))
        .willReturn(
            composeResponseFromField("languageSkill", LANGUAGE_SKILL_INCORRECT_MESSAGE_KEY));

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, stateHistoryViewRepresentation))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorMessage").value(COMMON_INVALID_INPUT_MESSAGE_KEY))
        .andExpect(jsonPath("$.fields.languageSkill").value(LANGUAGE_SKILL_INCORRECT_MESSAGE_KEY));

    verifyZeroInteractions(statesHistoryService);
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorJSONWhenLanguageSkillIsHigher() throws Exception {
    StateHistoryViewRepresentation stateHistoryViewRepresentation =
        StateHistoryViewRepresentation.builder().languageSkill(HIGHER_LANGUAGE_SKILL).build();

    given(fieldErrorResponseComposer.composeResponse(any(BindingResult.class)))
        .willReturn(
            composeResponseFromField("languageSkill", LANGUAGE_SKILL_INCORRECT_MESSAGE_KEY));

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, stateHistoryViewRepresentation))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorMessage").value(COMMON_INVALID_INPUT_MESSAGE_KEY))
        .andExpect(jsonPath("$.fields.languageSkill").value(LANGUAGE_SKILL_INCORRECT_MESSAGE_KEY));

    verifyZeroInteractions(statesHistoryService);
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorJSONWhenClaimIsNegative() throws Exception {
    StateHistoryViewRepresentation stateHistoryViewRepresentation =
        StateHistoryViewRepresentation.builder().claim(NEGATIVE_CLAIM).build();

    given(fieldErrorResponseComposer.composeResponse(any(BindingResult.class)))
        .willReturn(composeResponseFromField("claim", CLAIM_NEGATIVE_MESSAGE_KEY));

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, stateHistoryViewRepresentation))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorMessage").value(COMMON_INVALID_INPUT_MESSAGE_KEY))
        .andExpect(jsonPath("$.fields.claim").value(CLAIM_NEGATIVE_MESSAGE_KEY));

    verifyZeroInteractions(statesHistoryService);
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorJSONWhenOfferedMoneyIsNegative() throws Exception {
    StateHistoryViewRepresentation stateHistoryViewRepresentation =
        StateHistoryViewRepresentation.builder().offeredMoney(NEGATIVE_OFFERED_MONEY).build();

    given(fieldErrorResponseComposer.composeResponse(any(BindingResult.class)))
        .willReturn(composeResponseFromField("offeredMoney", OFFERED_MONEY_NEGATIVE_MESSAGE_KEY));

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, stateHistoryViewRepresentation))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorMessage").value(COMMON_INVALID_INPUT_MESSAGE_KEY))
        .andExpect(jsonPath("$.fields.offeredMoney").value(OFFERED_MONEY_NEGATIVE_MESSAGE_KEY));

    verifyZeroInteractions(statesHistoryService);
  }

  @Test
  public void saveOrUpdateShouldRespondWithApplicationIdWhenPostHasNoErrors() throws Exception {
    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, dummyStateHistory))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.applicationId").value(APPLICATION_ID.intValue()));

    ArgumentCaptor<StateHistoryDTO> historyCaptor = ArgumentCaptor.forClass(StateHistoryDTO.class);

    then(statesHistoryService).should()
        .saveStateHistory(historyCaptor.capture(), eq(APPLICATION_ID));

    assertThat(historyCaptor.getValue(), equalTo(dummyStateHistoryDto));
  }


  private ResponseEntity<RestResponse> composeResponseFromField(String fieldName,
                                                                String fieldValue) {
    RestResponse restResponse = new RestResponse(COMMON_INVALID_INPUT_MESSAGE_KEY);

    restResponse.addField(fieldName, fieldValue);

    return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
  }
}
