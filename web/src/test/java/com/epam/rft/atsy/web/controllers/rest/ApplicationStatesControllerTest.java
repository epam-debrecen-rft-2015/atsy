package com.epam.rft.atsy.web.controllers.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import com.epam.rft.atsy.web.controllers.AbstractControllerTest;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationStatesControllerTest extends AbstractControllerTest {
  private static final String REQUEST_URL = "/secure/applications_states/";

  private static final String APPLICATION_STATE_MESSAGE_KEY_PREFIX = "candidate.table.state.";

  private static final Clock TEST_CLOCK = Clock.fixed(Instant.now(), ZoneId.systemDefault());

  private static final Long APPLICATION_ID = 1L;
  private static final Date APPLICATION_CREATION_DATE =
      Date.from(Instant.now(TEST_CLOCK));

  private static final Long CHANNEL_ID = 1L;
  private static final String CHANNEL_NAME = "Google";

  private static final Long POSITION_ID = 1L;
  private static final String POSITION_NAME = "Developer";

  private static final Long CANDIDATE_ID = 1L;

  private static final Long STATE_A_ID = 1L;
  private static final String STATE_A_NAME = "state.a";
  private static final String STATE_A_LOCALIZED_NAME = "localized.state.a";

  private static final Long STATE_B_ID = 2L;
  private static final String STATE_B_NAME = "state.b";
  private static final String STATE_B_LOCALIZED_NAME = "localized.state.b";

  private static final Long STATE_C_ID = 3L;
  private static final String STATE_C_NAME = "state.c";
  private static final String STATE_C_LOCALIZED_NAME = "localized.state.c";

  private static final Long STATE_HISTORY_ID = 1L;
  private static final Date STATE_HISTORY_CREATION_DATE = Date.from(Instant.now(TEST_CLOCK));
  private static final Short LANGUAGE_SKILL = 5;
  private static final String DESCRIPTION = "description";
  private static final Short RESULT = 67;
  private static final Long OFFERED_MONEY = 0L;
  private static final Long CLAIM = 10L;
  private static final Date DAY_OF_START = Date.from(Instant.now(TEST_CLOCK));
  private static final Date FEEDBACK_DATE = Date.from(Instant.now(TEST_CLOCK));


  @Mock
  private MessageKeyResolver messageKeyResolver;

  @Mock
  private StatesHistoryService statesHistoryService;

  @InjectMocks
  private ApplicationStatesController applicationStatesController;

  private StateHistoryDTO dummyStateHistoryDto;

  private ApplicationDTO dummyApplicationDto;

  private ChannelDTO dummyChannelDto;

  private PositionDTO dummyPositionDto;

  private StateDTO stateDtoA;

  private StateDTO stateDtoB;

  private StateDTO stateDtoC;

  private List<StateHistoryDTO> dummyStateHistoryList;

  @Before
  public void setUpTestData() {
    dummyApplicationDto =
        ApplicationDTO.builder().id(APPLICATION_ID).candidateId(CANDIDATE_ID).channelId(CHANNEL_ID)
            .positionId(POSITION_ID).creationDate(APPLICATION_CREATION_DATE).build();

    dummyChannelDto = ChannelDTO.builder().id(CHANNEL_ID).name(CHANNEL_NAME).build();

    dummyPositionDto = PositionDTO.builder().id(POSITION_ID).name(POSITION_NAME).build();

    stateDtoA = new StateDTO(STATE_A_ID, STATE_A_NAME);
    stateDtoB = new StateDTO(STATE_B_ID, STATE_B_NAME);
    stateDtoC = new StateDTO(STATE_C_ID, STATE_C_NAME);

    List<StateDTO> stateDtoList = Arrays.asList(stateDtoA, stateDtoB, stateDtoC);

    dummyStateHistoryList = new ArrayList<>();

    for (StateDTO stateDto : stateDtoList) {
      dummyStateHistoryList.add(constructDummyStateHistoryDto(stateDto));
    }

    dummyStateHistoryDto = constructDummyStateHistoryDto(stateDtoList.get(0));
  }

  private StateHistoryDTO constructDummyStateHistoryDto(StateDTO stateDto) {
    return StateHistoryDTO.builder()
        .id(STATE_HISTORY_ID)
        .candidateId(CANDIDATE_ID)
        .position(dummyPositionDto)
        .channel(dummyChannelDto)
        .applicationDTO(dummyApplicationDto)
        .description(DESCRIPTION)
        .result(RESULT)
        .offeredMoney(OFFERED_MONEY)
        .claim(CLAIM)
        .feedbackDate(FEEDBACK_DATE)
        .dayOfStart(DAY_OF_START)
        .stateDTO(stateDto)
        .creationDate(STATE_HISTORY_CREATION_DATE)
        .build();
  }

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{applicationStatesController};
  }

  @Test
  public void loadApplicationsRespondsWithEmptyCollectionWhenThereAreNoApplications()
      throws Exception {
    given(statesHistoryService.getStateHistoriesByApplicationId(APPLICATION_ID)).willReturn(
        Collections.emptyList());

    mockMvc.perform(get(REQUEST_URL + APPLICATION_ID.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isEmpty());

    then(statesHistoryService).should().getStateHistoriesByApplicationId(APPLICATION_ID);

    verifyZeroInteractions(messageKeyResolver);
  }

  @Test
  public void loadApplicationsShouldRespondWithSingleElementCollectionWhenThereIsOneApplication()
      throws Exception {
    given(statesHistoryService.getStateHistoriesByApplicationId(APPLICATION_ID))
        .willReturn(Collections.singletonList(dummyStateHistoryDto));

    given(messageKeyResolver
        .resolveMessageOrDefault(eq(APPLICATION_STATE_MESSAGE_KEY_PREFIX + stateDtoA.getName()),
            anyString()))
        .willReturn(STATE_A_LOCALIZED_NAME);

    ResultActions resultActions = mockMvc.perform(get(REQUEST_URL + APPLICATION_ID.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[1]").doesNotExist());

    assertStateHistoryViewResponse(resultActions, 0, dummyStateHistoryDto,
        STATE_A_LOCALIZED_NAME);

    then(statesHistoryService).should().getStateHistoriesByApplicationId(APPLICATION_ID);

    then(messageKeyResolver).should()
        .resolveMessageOrDefault(eq(APPLICATION_STATE_MESSAGE_KEY_PREFIX + stateDtoA.getName()),
            anyString());

    verifyNoMoreInteractions(messageKeyResolver);
  }

  @Test
  public void loadApplicationsShouldRespondWithThreeElementCollectionWhenTherAreThreeApplications()
      throws Exception {
    given(statesHistoryService.getStateHistoriesByApplicationId(APPLICATION_ID))
        .willReturn(dummyStateHistoryList);

    given(messageKeyResolver
        .resolveMessageOrDefault(eq(APPLICATION_STATE_MESSAGE_KEY_PREFIX + stateDtoA.getName()),
            anyString()))
        .willReturn(STATE_A_LOCALIZED_NAME);

    given(messageKeyResolver
        .resolveMessageOrDefault(eq(APPLICATION_STATE_MESSAGE_KEY_PREFIX + stateDtoB.getName()),
            anyString()))
        .willReturn(STATE_B_LOCALIZED_NAME);

    given(messageKeyResolver
        .resolveMessageOrDefault(eq(APPLICATION_STATE_MESSAGE_KEY_PREFIX + stateDtoC.getName()),
            anyString()))
        .willReturn(STATE_C_LOCALIZED_NAME);

    ResultActions resultActions = mockMvc.perform(get(REQUEST_URL + APPLICATION_ID.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[2]").exists())
        .andExpect(jsonPath("$[3]").doesNotExist());

    assertStateHistoryViewResponse(resultActions, 0, dummyStateHistoryList.get(0),
        STATE_A_LOCALIZED_NAME);

    assertStateHistoryViewResponse(resultActions, 1, dummyStateHistoryList.get(1),
        STATE_B_LOCALIZED_NAME);

    assertStateHistoryViewResponse(resultActions, 2, dummyStateHistoryList.get(2),
        STATE_C_LOCALIZED_NAME);

    then(statesHistoryService).should().getStateHistoriesByApplicationId(APPLICATION_ID);

    then(messageKeyResolver).should()
        .resolveMessageOrDefault(eq(APPLICATION_STATE_MESSAGE_KEY_PREFIX + stateDtoA.getName()),
            anyString());

    then(messageKeyResolver).should()
        .resolveMessageOrDefault(eq(APPLICATION_STATE_MESSAGE_KEY_PREFIX + stateDtoB.getName()),
            anyString());

    then(messageKeyResolver).should()
        .resolveMessageOrDefault(eq(APPLICATION_STATE_MESSAGE_KEY_PREFIX + stateDtoC.getName()),
            anyString());

    verifyNoMoreInteractions(messageKeyResolver);
  }

  private void assertStateHistoryViewResponse(ResultActions resultActions, int index,
                                              StateHistoryDTO historyViewDto,
                                              String localizedStateName) throws Exception {
    String basePath = "$[" + index + "].";

    resultActions
        .andExpect(jsonPath(basePath + "id", equalTo(historyViewDto.getId().intValue())))
        .andExpect(jsonPath(basePath + "description", equalTo(historyViewDto.getDescription())))
        .andExpect(jsonPath(basePath + "result", equalTo(historyViewDto.getResult().intValue())))
        .andExpect(jsonPath(basePath + "offeredMoney",
            equalTo(historyViewDto.getOfferedMoney().intValue())))
        .andExpect(jsonPath(basePath + "claim", equalTo(historyViewDto.getClaim().intValue())))
        .andExpect(jsonPath(basePath + "creationDate",
            equalTo(historyViewDto.getCreationDate().getTime())))
        .andExpect(jsonPath(basePath + "feedbackDate",
            equalTo(historyViewDto.getFeedbackDate().toInstant().toEpochMilli())))
        .andExpect(jsonPath(basePath + "dayOfStart",
            equalTo(historyViewDto.getDayOfStart().toInstant().toEpochMilli())))
        .andExpect(jsonPath(basePath + "stateDTO.id",
            equalTo(historyViewDto.getStateDTO().getId().intValue())))
        .andExpect(jsonPath(basePath + "stateDTO.name", equalTo(localizedStateName)))
        .andExpect(jsonPath(basePath + "applicationDTO.id",
            equalTo(historyViewDto.getApplicationDTO().getId().intValue())))
        .andExpect(jsonPath(basePath + "applicationDTO.candidateId",
            equalTo(historyViewDto.getApplicationDTO().getCandidateId().intValue())))
        .andExpect(jsonPath(basePath + "applicationDTO.positionId",
            equalTo(historyViewDto.getApplicationDTO().getPositionId().intValue())))
        .andExpect(jsonPath(basePath + "applicationDTO.channelId",
            equalTo(historyViewDto.getApplicationDTO().getChannelId().intValue())))
        .andExpect(jsonPath(basePath + "channel.id",
            equalTo(historyViewDto.getChannel().getId().intValue())))
        .andExpect(jsonPath(basePath + "channel.name",
            equalTo(historyViewDto.getChannel().getName())))
        .andExpect(jsonPath(basePath + "position.id",
            equalTo(historyViewDto.getPosition().getId().intValue())))
        .andExpect(jsonPath(basePath + "position.name",
            equalTo(historyViewDto.getPosition().getName())));
  }
}
