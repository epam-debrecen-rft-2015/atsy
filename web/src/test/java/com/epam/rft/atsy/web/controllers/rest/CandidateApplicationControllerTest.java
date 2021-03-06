package com.epam.rft.atsy.web.controllers.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.response.PagingResponse;
import com.epam.rft.atsy.web.MediaTypes;
import com.epam.rft.atsy.web.controllers.AbstractControllerTest;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class CandidateApplicationControllerTest extends AbstractControllerTest {
  private static final String REQUEST_URL = "/secure/applications/";

  private static final String URL_PAGE_REQUEST_ENDING = "?pageNumber=1&pageSize=10";

  private static final String APPLICATION_STATE = "candidate.table.state.";

  private static final Long CANDIDATE_ID = 1L;

  private static final Long LAST_STATE_ID = 2L;

  private static final Long APPLICATION_ID = 3L;

  private static final String DEVELOPER_POSITION = "Developer";

  private static final String SYSADMIN_POSITION = "Developer";

  private static final Date CREATION_DATE = asDate(LocalDate.of(2016, 8, 1));

  private static final Date MODIFICATION_DATE = asDate(LocalDate.of(2016, 8, 2));

  private static final String RAW_STATE_TYPE = "raw";

  private static final String LOCALIZED_STATE_TYPE = "localized";

  private static final int PAGE_NUMBER_ZERO = 0;
  private static final int PAGE_SIZE_TEN = 10;

  @Mock
  private ApplicationsService applicationsService;

  @Mock
  private MessageKeyResolver messageKeyResolver;

  @InjectMocks
  private CandidateApplicationController candidateApplicationController;

  private CandidateApplicationDTO developerApplicationDto;

  private CandidateApplicationDTO sysadminApplicationDto;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{candidateApplicationController};
  }

  @Before
  public void setUpTestData() {
    developerApplicationDto =
        CandidateApplicationDTO.builder().lastStateId(LAST_STATE_ID)
            .id(APPLICATION_ID)
            .name(DEVELOPER_POSITION).creationDate(CREATION_DATE)
            .modificationDate(MODIFICATION_DATE)
            .stateType(RAW_STATE_TYPE)
            .build();

    sysadminApplicationDto =
        CandidateApplicationDTO.builder().lastStateId(LAST_STATE_ID)
            .id(APPLICATION_ID)
            .name(SYSADMIN_POSITION).creationDate(CREATION_DATE)
            .modificationDate(MODIFICATION_DATE)
            .stateType(RAW_STATE_TYPE)
            .build();

    given(messageKeyResolver
        .resolveMessageOrDefault(Matchers.anyString(), Matchers.any(Object[].class)))
        .willReturn(LOCALIZED_STATE_TYPE);
  }

  @Test
  public void loadApplicationsShouldRespondWithEmptyCollectionWhenThereAreNoApplications()
      throws Exception {

    given(applicationsService
        .getApplicationsByCandidateId(CANDIDATE_ID, PAGE_NUMBER_ZERO, PAGE_SIZE_TEN))
        .willReturn(new PagingResponse<CandidateApplicationDTO>(0L, Collections.emptyList()));

    mockMvc.perform(
        get(REQUEST_URL + CANDIDATE_ID.toString() + URL_PAGE_REQUEST_ENDING)
            .accept(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.total").isNumber())
        .andExpect(jsonPath("$.total").value(0))
        .andExpect(jsonPath("$.rows").isArray())
        .andExpect(jsonPath("$.rows").isEmpty());

    then(applicationsService).should()
        .getApplicationsByCandidateId(CANDIDATE_ID, PAGE_NUMBER_ZERO, PAGE_SIZE_TEN);

    verifyZeroInteractions(messageKeyResolver);
  }

  @Test
  public void loadApplicationsShouldRespondWithSingleElementArrayWhenThereIsOnlyOneApplication()
      throws Exception {

    given(applicationsService
        .getApplicationsByCandidateId(CANDIDATE_ID, PAGE_NUMBER_ZERO, PAGE_SIZE_TEN)).willReturn(
        new PagingResponse<CandidateApplicationDTO>(1L,
            Collections.singletonList(developerApplicationDto)));

    ResultActions resultActions = mockMvc.perform(
        get(REQUEST_URL + CANDIDATE_ID.toString() + URL_PAGE_REQUEST_ENDING)
            .accept(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.total").isNumber())
        .andExpect(jsonPath("$.total").value(1))
        .andExpect(jsonPath("$.rows").isArray())
        .andExpect(jsonPath("$.rows[0]").exists())
        .andExpect(jsonPath("$.rows[1]").doesNotExist());

    assertApplicationResponse(resultActions, 0, developerApplicationDto);

    then(applicationsService).should()
        .getApplicationsByCandidateId(CANDIDATE_ID, PAGE_NUMBER_ZERO, PAGE_SIZE_TEN);

    then(messageKeyResolver).should()
        .resolveMessageOrDefault(APPLICATION_STATE + RAW_STATE_TYPE, RAW_STATE_TYPE);
  }

  @Test
  public void loadApplicationsShouldRespondWithTwoElementArrayWhenThereAreTwoApplications()
      throws Exception {

    given(applicationsService
        .getApplicationsByCandidateId(CANDIDATE_ID, PAGE_NUMBER_ZERO, PAGE_SIZE_TEN)).willReturn(
        new PagingResponse<CandidateApplicationDTO>(2L,
            Arrays.asList(developerApplicationDto, sysadminApplicationDto)));

    ResultActions resultActions = mockMvc.perform(
        get(REQUEST_URL + CANDIDATE_ID.toString() + URL_PAGE_REQUEST_ENDING)
            .accept(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.total").isNumber())
        .andExpect(jsonPath("$.total").value(2))
        .andExpect(jsonPath("$.rows").isArray())
        .andExpect(jsonPath("$.rows[0]").exists())
        .andExpect(jsonPath("$.rows[1]").exists())
        .andExpect(jsonPath("$.rows[2]").doesNotExist());

    assertApplicationResponse(resultActions, 0, developerApplicationDto);

    assertApplicationResponse(resultActions, 1, sysadminApplicationDto);

    then(applicationsService).should()
        .getApplicationsByCandidateId(CANDIDATE_ID, PAGE_NUMBER_ZERO, PAGE_SIZE_TEN);

    then(messageKeyResolver).should(times(2))
        .resolveMessageOrDefault(APPLICATION_STATE + RAW_STATE_TYPE, RAW_STATE_TYPE);
  }

  private void assertApplicationResponse(ResultActions resultActions, int index,
                                         CandidateApplicationDTO applicationDto) throws Exception {
    String basePath = "$.rows[" + index + "].";

    resultActions
        .andExpect(jsonPath(basePath + "lastStateId",
            equalTo(applicationDto.getLastStateId().intValue())))
        .andExpect(jsonPath(basePath + "id",
            equalTo(applicationDto.getId().intValue())))
        .andExpect(jsonPath(basePath + "name", equalTo(applicationDto.getName())))
        .andExpect(jsonPath(basePath + "creationDate",
            equalTo(applicationDto.getCreationDate().getTime())))
        .andExpect(jsonPath(basePath + "modificationDate",
            equalTo(applicationDto.getModificationDate().getTime())))
        .andExpect(jsonPath(basePath + "stateType", equalTo(LOCALIZED_STATE_TYPE)));
  }

  public static Date asDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }
}
