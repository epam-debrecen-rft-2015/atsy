package com.epam.rft.atsy.web.controllers.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.request.CandidateFilterRequest;
import com.epam.rft.atsy.web.controllers.AbstractControllerTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CandidateControllerTest extends AbstractControllerTest {

  private static final String REQUEST_URL = "/secure/candidates";

  private static final String NAME = "candidate.name";
  private static final String ASC = "asc";
  private static final String SORT_NAME = "sortName";
  private static final String SORT_ORDER = "sortOrder";

  private static final String PAGE_SIZE = "pageSize";
  private static final String PAGE_NUMBER = "pageNumber";
  private static final String DEFAULT_PAGE_SIZE_ = "10";
  private static final String DEFAULT_PAGE_NUMBER = "0";
  private static final int DEFAULT_PAGE_SIZE_PARAM_ = 10;
  private static final int DEFAULT_PAGE_NUMBER_PARAM = 0;

  private static final String FILTER = "filter";

  private static final String CANDIDATE_NAME = "Candidate";
  private static final String CANDIDATE_PHONE = "+36105555555";
  private static final String CANDIDATE_EMAIL = "candidate.a@atsy.com";

  private static final String CANDIDATE_C_PHONE = "+36107777777";
  private static final String CANDIDATE_C_EMAIL = "candidate.c@atsy.com";
  private static final String CANDIDATE_C_POSITION = "Fejlesztő";

  private static final String NON_VALID_CANDIDATE_NAME = "Not a candidate name";

  private static final String JSON_NON_VALID_CANDIDATE_NAME = "{\"name\":\"Not a candidate name\"}";
  private static final String JSON_CANDIDATE_NAME = "{\"name\":\"Candidate\"}";
  private static final String
      JSON_CANDIDATE_EMAIL_AND_PHONE =
      "{\"email\":\"candidate.a@atsy.com\", \"phone\":\"+36105555555\"}";
  private static final String JSON_CANDIDATE_NAME_AND_EMAIL_AND_PHONE =
      "{\"name\":\"Candidate\", \"email\":\"candidate.a@atsy.com\", \"phone\":\"+36105555555\"}";

  private static final String
      JSON_CANDIDATE_NAME_AND_EMAIL_AND_PHONE_AND_POSITION =
      "{\"name\":\"Candidate\", \"email\":\"candidate.c@atsy.com\", \"phone\":\"+36107777777\", \"position\":\"Fejlesztő\"}";

  private static final CandidateFilterRequest
      DEFAULT_CANDIDATE_FILTER_REQUEST =
      CandidateFilterRequest.builder().candidateName("").candidateEmail("").candidatePhone("")
          .candiadtePositions("").sortName(NAME).sortOrder(ASC)
          .pageNumber(DEFAULT_PAGE_NUMBER_PARAM).pageSize(DEFAULT_PAGE_SIZE_PARAM_).build();
  public static final String CANDIDATE_POSITION = "Fejlesztő";

  @Mock
  private CandidateService candidateService;
  @InjectMocks
  private CandidateController candidateController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{candidateController};
  }

  @Test
  public void loadPageShouldRespondClientErrorWhenParamSortIsMissing() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL)
        .param(SORT_ORDER, ASC))
        .andExpect(status().isBadRequest());

    verifyZeroInteractions(candidateService);
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadPageShouldThrowIllegalArgumentExceptionWhenParamSortIsNull() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL)
        .param(SORT_NAME, null).param(SORT_ORDER, ASC));
  }

  @Test
  public void loadPageShouldRespondClientErrorWhenParamOrderIsMissing() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL)
        .param(SORT_NAME, NAME))
        .andExpect(status().isBadRequest());

    verifyZeroInteractions(candidateService);
  }


  @Test(expected = IllegalArgumentException.class)
  public void loadPageShouldThrowIllegalArgumentExceptionWhenParamOrderIsNull() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL)
        .param(SORT_NAME, NAME).param(SORT_ORDER, null));
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadPageShouldThrowIllegalArgumentExceptionWhenParamFilterIsNull() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL)
        .param(FILTER, null).param(SORT_NAME, NAME).param(SORT_ORDER, ASC));
  }

  @Test
  public void loadPageShouldRespondCandidateCollectionWhenOrderByNameAscWithoutFilters()
      throws Exception {

    ArgumentCaptor<CandidateFilterRequest>
        candidateFilterRequestArgumentCaptor =
        ArgumentCaptor.forClass(CandidateFilterRequest.class);
    this.mockMvc.perform(get(REQUEST_URL).param(SORT_NAME, NAME).param(SORT_ORDER, ASC)
        .param(PAGE_SIZE, DEFAULT_PAGE_SIZE_).param(PAGE_NUMBER, DEFAULT_PAGE_NUMBER))
        .andExpect(status().isOk());

    then(candidateService).should()
        .getCandidatesByFilterRequest(candidateFilterRequestArgumentCaptor.capture());
    assertThat(candidateFilterRequestArgumentCaptor.getValue(),
        equalTo(DEFAULT_CANDIDATE_FILTER_REQUEST));

    verifyNoMoreInteractions(candidateService);
  }

  @Test
  public void loadPageShouldRespondCandidateCollectionWhenFilterIsEmptyString() throws Exception {
    ArgumentCaptor<CandidateFilterRequest>
        candidateFilterRequestArgumentCaptor =
        ArgumentCaptor.forClass(CandidateFilterRequest.class);
    this.mockMvc.perform(get(REQUEST_URL).param(FILTER, StringUtils.EMPTY).param(SORT_NAME, NAME)
        .param(SORT_ORDER, ASC)
        .param(PAGE_SIZE, DEFAULT_PAGE_SIZE_).param(PAGE_NUMBER, DEFAULT_PAGE_NUMBER))
        .andExpect(status().isOk());

    then(candidateService).should()
        .getCandidatesByFilterRequest(candidateFilterRequestArgumentCaptor.capture());
    assertThat(candidateFilterRequestArgumentCaptor.getValue(),
        equalTo(DEFAULT_CANDIDATE_FILTER_REQUEST));

    verifyNoMoreInteractions(candidateService);
  }


  @Test
  public void loadPageShouldRespondCandidateCollectionWhenOrderByNameAscWithFilterByNameWhenNameIsNotValid()
      throws Exception {

    CandidateFilterRequest candidateFilterRequest = DEFAULT_CANDIDATE_FILTER_REQUEST;
    candidateFilterRequest.setCandidateName(NON_VALID_CANDIDATE_NAME);

    ArgumentCaptor<CandidateFilterRequest>
        candidateFilterRequestArgumentCaptor =
        ArgumentCaptor.forClass(CandidateFilterRequest.class);
    this.mockMvc.perform(
        get(REQUEST_URL).param(FILTER, JSON_NON_VALID_CANDIDATE_NAME).param(SORT_NAME, NAME)
            .param(SORT_ORDER, ASC)
            .param(PAGE_SIZE, DEFAULT_PAGE_SIZE_).param(PAGE_NUMBER, DEFAULT_PAGE_NUMBER))
        .andExpect(status().isOk());

    then(candidateService).should()
        .getCandidatesByFilterRequest(candidateFilterRequestArgumentCaptor.capture());
    assertThat(candidateFilterRequestArgumentCaptor.getValue(),
        equalTo(candidateFilterRequest));

    verifyNoMoreInteractions(candidateService);
  }

  @Test
  public void loadPageShouldRespondCandidateCollectionWhenOrderByNameAscWithFilterByName()
      throws Exception {

    CandidateFilterRequest candidateFilterRequest = DEFAULT_CANDIDATE_FILTER_REQUEST;
    candidateFilterRequest.setCandidateName(CANDIDATE_NAME);

    ArgumentCaptor<CandidateFilterRequest>
        candidateFilterRequestArgumentCaptor =
        ArgumentCaptor.forClass(CandidateFilterRequest.class);
    this.mockMvc.perform(
        get(REQUEST_URL).param(FILTER, JSON_CANDIDATE_NAME).param(SORT_NAME, NAME)
            .param(SORT_ORDER, ASC)
            .param(PAGE_SIZE, DEFAULT_PAGE_SIZE_).param(PAGE_NUMBER, DEFAULT_PAGE_NUMBER))
        .andExpect(status().isOk());

    then(candidateService).should()
        .getCandidatesByFilterRequest(candidateFilterRequestArgumentCaptor.capture());
    assertThat(candidateFilterRequestArgumentCaptor.getValue(),
        equalTo(candidateFilterRequest));

    verifyNoMoreInteractions(candidateService);
  }

  @Test
  public void loadPageShouldRespondCandidateCollectionWhenOrderByNameAscWithFilterByEmailAndPhone()
      throws Exception {

    CandidateFilterRequest candidateFilterRequest = DEFAULT_CANDIDATE_FILTER_REQUEST;
    candidateFilterRequest.setCandidateEmail(CANDIDATE_EMAIL);
    candidateFilterRequest.setCandidatePhone(CANDIDATE_PHONE);

    ArgumentCaptor<CandidateFilterRequest>
        candidateFilterRequestArgumentCaptor =
        ArgumentCaptor.forClass(CandidateFilterRequest.class);
    this.mockMvc.perform(
        get(REQUEST_URL).param(FILTER, JSON_CANDIDATE_EMAIL_AND_PHONE).param(SORT_NAME, NAME)
            .param(SORT_ORDER, ASC)
            .param(PAGE_SIZE, DEFAULT_PAGE_SIZE_).param(PAGE_NUMBER, DEFAULT_PAGE_NUMBER))
        .andExpect(status().isOk());

    then(candidateService).should()
        .getCandidatesByFilterRequest(candidateFilterRequestArgumentCaptor.capture());
    assertThat(candidateFilterRequestArgumentCaptor.getValue(),
        equalTo(candidateFilterRequest));

    verifyNoMoreInteractions(candidateService);
  }

  @Test
  public void loadPageShouldRespondCandidateCollectionWhenOrderByNameAscWithFilterByNameAndEmailAndPhone()
      throws Exception {

    CandidateFilterRequest candidateFilterRequest = DEFAULT_CANDIDATE_FILTER_REQUEST;
    candidateFilterRequest.setCandidateName(CANDIDATE_NAME);
    candidateFilterRequest.setCandidateEmail(CANDIDATE_EMAIL);
    candidateFilterRequest.setCandidatePhone(CANDIDATE_PHONE);

    ArgumentCaptor<CandidateFilterRequest>
        candidateFilterRequestArgumentCaptor =
        ArgumentCaptor.forClass(CandidateFilterRequest.class);
    this.mockMvc.perform(
        get(REQUEST_URL).param(FILTER, JSON_CANDIDATE_NAME_AND_EMAIL_AND_PHONE)
            .param(SORT_NAME, NAME)
            .param(SORT_ORDER, ASC)
            .param(PAGE_SIZE, DEFAULT_PAGE_SIZE_).param(PAGE_NUMBER, DEFAULT_PAGE_NUMBER))
        .andExpect(status().isOk());

    then(candidateService).should()
        .getCandidatesByFilterRequest(candidateFilterRequestArgumentCaptor.capture());
    assertThat(candidateFilterRequestArgumentCaptor.getValue(),
        equalTo(candidateFilterRequest));

    verifyNoMoreInteractions(candidateService);
  }

  @Test
  public void loadPageShouldRespondCandidateCollectionWhenOrderByNameAscWithFilterByNameAndEmailAndPhoneAndPosition()
      throws Exception {

    CandidateFilterRequest candidateFilterRequest = DEFAULT_CANDIDATE_FILTER_REQUEST;
    candidateFilterRequest.setCandidateName(CANDIDATE_NAME);
    candidateFilterRequest.setCandidateEmail(CANDIDATE_C_EMAIL);
    candidateFilterRequest.setCandidatePhone(CANDIDATE_C_PHONE);
    candidateFilterRequest.setCandiadtePositions(CANDIDATE_C_POSITION);

    ArgumentCaptor<CandidateFilterRequest>
        candidateFilterRequestArgumentCaptor =
        ArgumentCaptor.forClass(CandidateFilterRequest.class);
    this.mockMvc.perform(
        get(REQUEST_URL).param(FILTER, JSON_CANDIDATE_NAME_AND_EMAIL_AND_PHONE_AND_POSITION)
            .param(SORT_NAME, NAME)
            .param(SORT_ORDER, ASC)
            .param(PAGE_SIZE, DEFAULT_PAGE_SIZE_).param(PAGE_NUMBER, DEFAULT_PAGE_NUMBER))
        .andExpect(status().isOk());

    then(candidateService).should()
        .getCandidatesByFilterRequest(candidateFilterRequestArgumentCaptor.capture());
    assertThat(candidateFilterRequestArgumentCaptor.getValue(),
        equalTo(candidateFilterRequest));

    verifyNoMoreInteractions(candidateService);
  }
}
