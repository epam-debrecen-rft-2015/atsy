package com.epam.rft.atsy.web.controllers.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.web.MediaTypes;
import com.epam.rft.atsy.web.controllers.AbstractControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class CandidateApplicationControllerTest extends AbstractControllerTest {
  private static final String REQUEST_URL = "/secure/applications/";

  private static final String APPLICATION_STATE = "candidate.table.state.";

  private static final Long CANDIDATE_ID = 1L;

  private static final Long LAST_STATE_ID = 2L;

  private static final Long APPLICATION_ID = 3L;

  private static final String DEVELOPER_POSITION = "Developer";

  private static final String SYSADMIN_POSITION = "Developer";

  private static final String CREATION_DATE = LocalDate.of(2016, 8, 1).toString();

  private static final String MODIFICATION_DATE = LocalDate.of(2016, 8, 2).toString();

  private static final String RAW_STATE_TYPE = "raw";

  private static final String LOCALIZED_STATE_TYPE = "localized";

  @Mock
  private StatesHistoryService statesHistoryService;

  @Mock
  private MessageSource messageSource;

  @InjectMocks
  private CandidateApplicationController candidateApplicationController;

  private CandidateApplicationDTO developerApplicationDto;

  private CandidateApplicationDTO sysadminApplicationDto;

  private Collection<CandidateApplicationDTO> candidateApplicationCollection;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{candidateApplicationController};
  }

  @Before
  public void setUpTestData() {
    developerApplicationDto =
        CandidateApplicationDTO.builder().lastStateId(LAST_STATE_ID)
            .applicationId(APPLICATION_ID)
            .positionName(DEVELOPER_POSITION).creationDate(CREATION_DATE)
            .modificationDate(MODIFICATION_DATE)
            .stateType(RAW_STATE_TYPE)
            .build();

    sysadminApplicationDto =
        CandidateApplicationDTO.builder().lastStateId(LAST_STATE_ID)
            .applicationId(APPLICATION_ID)
            .positionName(SYSADMIN_POSITION).creationDate(CREATION_DATE)
            .modificationDate(MODIFICATION_DATE)
            .stateType(RAW_STATE_TYPE)
            .build();

    candidateApplicationCollection = Arrays.asList(developerApplicationDto, sysadminApplicationDto);

    given(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
        Matchers.any(Locale.class))).willReturn(LOCALIZED_STATE_TYPE);
  }

  @Test
  public void loadApplicationsShouldRespondWithEmptyCollectionWhenThereAreNoApplications()
      throws Exception {
    given(statesHistoryService.getCandidateApplicationsByCandidateId(CANDIDATE_ID)).willReturn(
        Collections.emptyList());

    mockMvc.perform(
        get(REQUEST_URL + CANDIDATE_ID.toString()).accept(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isEmpty());

    then(statesHistoryService).should().getCandidateApplicationsByCandidateId(CANDIDATE_ID);

    verifyZeroInteractions(messageSource);
  }

  @Test
  public void loadApplicationsShouldRespondWithSingleElementArrayWhenThereIsOnlyOneApplication()
      throws Exception {
    given(statesHistoryService.getCandidateApplicationsByCandidateId(CANDIDATE_ID))
        .willReturn(Collections.singletonList(developerApplicationDto));

    mockMvc.perform(
        get(REQUEST_URL + CANDIDATE_ID.toString()).accept(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[0].lastStateId", equalTo(LAST_STATE_ID.intValue())))
        .andExpect(jsonPath("$[0].applicationId", equalTo(APPLICATION_ID.intValue())))
        .andExpect(jsonPath("$[0].positionName", equalTo(DEVELOPER_POSITION)))
        .andExpect(jsonPath("$[0].creationDate", equalTo(CREATION_DATE)))
        .andExpect(jsonPath("$[0].modificationDate", equalTo(MODIFICATION_DATE)))
        .andExpect(jsonPath("$[0].stateType", equalTo(LOCALIZED_STATE_TYPE)))
        .andExpect(jsonPath("$[1]").doesNotExist());

    then(statesHistoryService).should().getCandidateApplicationsByCandidateId(CANDIDATE_ID);

    then(messageSource).should()
        .getMessage(eq(APPLICATION_STATE + RAW_STATE_TYPE), eq(new Object[]{RAW_STATE_TYPE}),
            any(Locale.class));
  }

  @Test
  public void loadApplicationsShouldRespondWithTwoElementArrayWhenThereAreTwoApplications()
      throws Exception {
    given(statesHistoryService.getCandidateApplicationsByCandidateId(CANDIDATE_ID))
        .willReturn(candidateApplicationCollection);

    mockMvc.perform(
        get(REQUEST_URL + CANDIDATE_ID.toString()).accept(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0]").exists())
        .andExpect(jsonPath("$[0].lastStateId", equalTo(LAST_STATE_ID.intValue())))
        .andExpect(jsonPath("$[0].applicationId", equalTo(APPLICATION_ID.intValue())))
        .andExpect(jsonPath("$[0].positionName", equalTo(DEVELOPER_POSITION)))
        .andExpect(jsonPath("$[0].creationDate", equalTo(CREATION_DATE)))
        .andExpect(jsonPath("$[0].modificationDate", equalTo(MODIFICATION_DATE)))
        .andExpect(jsonPath("$[0].stateType", equalTo(LOCALIZED_STATE_TYPE)))
        .andExpect(jsonPath("$[1]").exists())
        .andExpect(jsonPath("$[1].lastStateId", equalTo(LAST_STATE_ID.intValue())))
        .andExpect(jsonPath("$[1].applicationId", equalTo(APPLICATION_ID.intValue())))
        .andExpect(jsonPath("$[1].positionName", equalTo(SYSADMIN_POSITION)))
        .andExpect(jsonPath("$[1].creationDate", equalTo(CREATION_DATE)))
        .andExpect(jsonPath("$[1].modificationDate", equalTo(MODIFICATION_DATE)))
        .andExpect(jsonPath("$[1].stateType", equalTo(LOCALIZED_STATE_TYPE)))
        .andExpect(jsonPath("$[2]").doesNotExist());

    then(statesHistoryService).should().getCandidateApplicationsByCandidateId(CANDIDATE_ID);

    then(messageSource).should(times(2))
        .getMessage(eq(APPLICATION_STATE + RAW_STATE_TYPE), eq(new Object[]{RAW_STATE_TYPE}),
            any(Locale.class));
  }
}
