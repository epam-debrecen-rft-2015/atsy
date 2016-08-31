package com.epam.rft.atsy.web.controllers.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.request.FilterRequest;
import com.epam.rft.atsy.service.request.SearchOptions;
import com.epam.rft.atsy.service.request.SortingRequest;
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

  private static final String NAME = "name";
  private static final String ASC = "asc";
  private static final String SORT = "sort";
  private static final String ORDER = "order";
  private static final String FILTER = "filter";

  private static final String CANDIDATE_NAME = "Candidate";
  private static final String CANDIDATE_PHONE = "+36105555555";
  private static final String CANDIDATE_EMAIL = "candidate.a@atsy.com";
  private static final String NON_VALID_FIELD_NAME = "Non valid field name";
  private static final String NON_VALID_CANDIDATE_NAME = "Not a candidate name";

  private static final String JSON_NON_VALID_CANDIDATE_NAME = "{\"name\":\"Not a candidate name\"}";
  private static final String JSON_CANDIDATE_NAME = "{\"name\":\"Candidate\"}";
  private static final String JSON_CANDIDATE_EMAIL_AND_PHONE = "{\"email\":\"candidate.a@atsy.com\", \"phone\":\"+36105555555\"}";
  private static final String JSON_CANDIDATE_NAME_AND_EMAIL_AND_PHONE =
      "{\"name\":\"Candidate\", \"email\":\"candidate.a@atsy.com\", \"phone\":\"+36105555555\"}";

  private static final String EMPTY_JSON = "{}";

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
        .param(ORDER, ASC))
        .andExpect(status().isBadRequest());

    verifyZeroInteractions(candidateService);
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadPageShouldThrowIllegalArgumentExceptionWhenParamSortIsNull() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL)
        .param(SORT, null).param(ORDER, ASC));
  }

  @Test
  public void loadPageShouldRespondInternalServerErrorWhenParamSortIsAnEmptyString() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL)
        .param(SORT, StringUtils.EMPTY).param(ORDER, ASC))
        .andExpect(status().isInternalServerError());

    verifyZeroInteractions(candidateService);
  }

  @Test
  public void loadPageShouldRespondInternalServerErrorWhenParamSortIsNotValid() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL)
        .param(SORT, NON_VALID_FIELD_NAME).param(ORDER, ASC))
        .andExpect(status().isInternalServerError());

    verifyZeroInteractions(candidateService);
  }

  @Test
  public void loadPageShouldRespondClientErrorWhenParamOrderIsMissing() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL)
        .param(SORT, NAME))
        .andExpect(status().isBadRequest());

    verifyZeroInteractions(candidateService);
  }


  @Test(expected = IllegalArgumentException.class)
  public void loadPageShouldThrowIllegalArgumentExceptionWhenParamOrderIsNull() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL)
        .param(SORT, NAME).param(ORDER, null));
  }


  @Test
  public void loadPageShouldRespondInternalServerErrorWhenParamOrderIsAnEmptyString() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL)
        .param(SORT, NAME).param(ORDER, StringUtils.EMPTY))
        .andExpect(status().isInternalServerError());

    verifyZeroInteractions(candidateService);
  }

  @Test
  public void loadPageShouldRespondInternalServerErrorWhenParamOrderIsNotValid() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL)
        .param(SORT, NAME).param(ORDER, NON_VALID_FIELD_NAME))
        .andExpect(status().isInternalServerError());

    verifyZeroInteractions(candidateService);
  }


  @Test
  public void loadPageShouldRespondCandidateCollectionWhenOrderByNameAscWithoutFilters() throws Exception {
    final SearchOptions searchOptions =
        SearchOptions.builder().name(StringUtils.EMPTY).email(StringUtils.EMPTY).phone(StringUtils.EMPTY).build();
    final FilterRequest filterRequest = FilterRequest.builder().order(SortingRequest.Order.ASC)
        .fieldName(SortingRequest.Field.NAME).searchOptions(searchOptions).build();

    ArgumentCaptor<FilterRequest> filterRequestArgumentCaptor = ArgumentCaptor.forClass(FilterRequest.class);
    this.mockMvc.perform(get(REQUEST_URL)
        .param(SORT, NAME).param(ORDER, ASC))
        .andExpect(status().isOk());

    then(candidateService).should().getAllCandidate(filterRequestArgumentCaptor.capture());
    assertThat(filterRequestArgumentCaptor.getValue(), equalTo(filterRequest));

    verifyNoMoreInteractions(candidateService);
  }

  @Test(expected = IllegalArgumentException.class)
  public void loadPageShouldThrowIllegalArgumentExceptionWhenParamFilterIsNull() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL)
        .param(FILTER, null).param(SORT, NAME).param(ORDER, ASC));
  }

  @Test
  public void loadPageShouldRespondCandidateCollectionWhenFilterIsEmptyString() throws Exception {
    final SearchOptions searchOptions =
        SearchOptions.builder().name(StringUtils.EMPTY).email(StringUtils.EMPTY).phone(StringUtils.EMPTY).build();
    final FilterRequest filterRequest = FilterRequest.builder().order(SortingRequest.Order.ASC)
        .fieldName(SortingRequest.Field.NAME).searchOptions(searchOptions).build();

    ArgumentCaptor<FilterRequest> filterRequestArgumentCaptor = ArgumentCaptor.forClass(FilterRequest.class);
    this.mockMvc.perform(get(REQUEST_URL)
        .param(FILTER, StringUtils.EMPTY).param(SORT, NAME).param(ORDER, ASC))
        .andExpect(status().isOk());

    then(candidateService).should().getAllCandidate(filterRequestArgumentCaptor.capture());
    assertThat(filterRequestArgumentCaptor.getValue(), equalTo(filterRequest));

    verifyNoMoreInteractions(candidateService);
  }

  @Test
  public void loadPageShouldRespondCandidateCollectionWhenFilterIsEmptyJson() throws Exception {
    final SearchOptions searchOptions =
        SearchOptions.builder().name(StringUtils.EMPTY).email(StringUtils.EMPTY).phone(StringUtils.EMPTY).build();
    final FilterRequest filterRequest = FilterRequest.builder().order(SortingRequest.Order.ASC)
        .fieldName(SortingRequest.Field.NAME).searchOptions(searchOptions).build();

    ArgumentCaptor<FilterRequest> filterRequestArgumentCaptor = ArgumentCaptor.forClass(FilterRequest.class);
    this.mockMvc.perform(get(REQUEST_URL)
        .param(FILTER, EMPTY_JSON).param(SORT, NAME).param(ORDER, ASC))
        .andExpect(status().isOk());

    then(candidateService).should().getAllCandidate(filterRequestArgumentCaptor.capture());
    assertThat(filterRequestArgumentCaptor.getValue(), equalTo(filterRequest));

    verifyNoMoreInteractions(candidateService);
  }

  @Test
  public void loadPageShouldRespondCandidateCollectionWhenOrderByNameAscWithFilterWhenParsingThrowJsonSyntaxException()
      throws Exception {

    final SearchOptions searchOptions =
        SearchOptions.builder().name(StringUtils.EMPTY).email(StringUtils.EMPTY).phone(StringUtils.EMPTY).build();
    final FilterRequest filterRequest = FilterRequest.builder().order(SortingRequest.Order.ASC)
        .fieldName(SortingRequest.Field.NAME).searchOptions(searchOptions).build();

    ArgumentCaptor<FilterRequest> filterRequestArgumentCaptor = ArgumentCaptor.forClass(FilterRequest.class);
    this.mockMvc.perform(get(REQUEST_URL)
        .param(FILTER, JSON_CANDIDATE_NAME_AND_EMAIL_AND_PHONE + NON_VALID_FIELD_NAME).param(SORT, NAME)
        .param(ORDER, ASC))
        .andExpect(status().isOk());

    then(candidateService).should().getAllCandidate(filterRequestArgumentCaptor.capture());
    assertThat(filterRequestArgumentCaptor.getValue(), equalTo(filterRequest));

    verifyNoMoreInteractions(candidateService);
  }

  @Test
  public void loadPageShouldRespondCandidateCollectionWhenOrderByNameAscWithFilterByNameWhenNameIsNotValid()
      throws Exception {

    final SearchOptions searchOptions =
        SearchOptions.builder().name(NON_VALID_CANDIDATE_NAME).email(StringUtils.EMPTY).phone(StringUtils.EMPTY).build();
    final FilterRequest filterRequest = FilterRequest.builder().order(SortingRequest.Order.ASC)
        .fieldName(SortingRequest.Field.NAME).searchOptions(searchOptions).build();

    ArgumentCaptor<FilterRequest> filterRequestArgumentCaptor = ArgumentCaptor.forClass(FilterRequest.class);
    this.mockMvc.perform(get(REQUEST_URL)
        .param(FILTER, JSON_NON_VALID_CANDIDATE_NAME).param(SORT, NAME).param(ORDER, ASC))
        .andExpect(status().isOk());

    then(candidateService).should().getAllCandidate(filterRequestArgumentCaptor.capture());
    assertThat(filterRequestArgumentCaptor.getValue(), equalTo(filterRequest));

    verifyNoMoreInteractions(candidateService);
  }

  @Test
  public void loadPageShouldRespondCandidateCollectionWhenOrderByNameAscWithFilterByName() throws Exception {
    final SearchOptions searchOptions =
        SearchOptions.builder().name(CANDIDATE_NAME).email(StringUtils.EMPTY).phone(StringUtils.EMPTY).build();
    final FilterRequest filterRequest = FilterRequest.builder().order(SortingRequest.Order.ASC)
        .fieldName(SortingRequest.Field.NAME).searchOptions(searchOptions).build();

    ArgumentCaptor<FilterRequest> filterRequestArgumentCaptor = ArgumentCaptor.forClass(FilterRequest.class);
    this.mockMvc.perform(get(REQUEST_URL)
        .param(FILTER, JSON_CANDIDATE_NAME).param(SORT, NAME).param(ORDER, ASC))
        .andExpect(status().isOk());

    then(candidateService).should().getAllCandidate(filterRequestArgumentCaptor.capture());
    assertThat(filterRequestArgumentCaptor.getValue(), equalTo(filterRequest));

    verifyNoMoreInteractions(candidateService);
  }

  @Test
  public void loadPageShouldRespondCandidateCollectionWhenOrderByNameAscWithFilterByEmailAndPhone() throws Exception {
    final SearchOptions searchOptions =
        SearchOptions.builder().name(StringUtils.EMPTY).email(CANDIDATE_EMAIL).phone(CANDIDATE_PHONE).build();
    final FilterRequest filterRequest = FilterRequest.builder().order(SortingRequest.Order.ASC)
        .fieldName(SortingRequest.Field.NAME).searchOptions(searchOptions).build();

    ArgumentCaptor<FilterRequest> filterRequestArgumentCaptor = ArgumentCaptor.forClass(FilterRequest.class);
    this.mockMvc.perform(get(REQUEST_URL)
        .param(FILTER, JSON_CANDIDATE_EMAIL_AND_PHONE).param(SORT, NAME).param(ORDER, ASC))
        .andExpect(status().isOk());

    then(candidateService).should().getAllCandidate(filterRequestArgumentCaptor.capture());
    assertThat(filterRequestArgumentCaptor.getValue(), equalTo(filterRequest));

    verifyNoMoreInteractions(candidateService);
  }

  @Test
  public void loadPageShouldRespondCandidateCollectionWhenOrderByNameAscWithFilterByNameAndEmailAndPhone()
      throws Exception {

    final SearchOptions searchOptions =
        SearchOptions.builder().name(CANDIDATE_NAME).email(CANDIDATE_EMAIL).phone(CANDIDATE_PHONE).build();
    final FilterRequest filterRequest = FilterRequest.builder().order(SortingRequest.Order.ASC)
        .fieldName(SortingRequest.Field.NAME).searchOptions(searchOptions).build();

    ArgumentCaptor<FilterRequest> filterRequestArgumentCaptor = ArgumentCaptor.forClass(FilterRequest.class);
    this.mockMvc.perform(get(REQUEST_URL)
        .param(FILTER, JSON_CANDIDATE_NAME_AND_EMAIL_AND_PHONE).param(SORT, NAME).param(ORDER, ASC))
        .andExpect(status().isOk());

    then(candidateService).should().getAllCandidate(filterRequestArgumentCaptor.capture());
    assertThat(filterRequestArgumentCaptor.getValue(), equalTo(filterRequest));

    verifyNoMoreInteractions(candidateService);
  }
}
