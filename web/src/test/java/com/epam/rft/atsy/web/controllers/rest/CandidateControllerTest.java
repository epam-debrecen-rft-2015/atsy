package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.controllers.AbstractControllerTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CandidateControllerTest extends AbstractControllerTest {

  private static final String REQUEST_URL = "/secure/candidates";

  private static final String CANDIDATE_NAME_A = "Candidate A";
  private static final String CANDIDATE_NAME_B = "Candidate B";
  private static final String CANDIDATE_NAME_C = "Candidate C";

  private static final String CANDIDATE_EMAIL_ADDRESS_A = "candidate.a@atsy.com";
  private static final String CANDIDATE_EMAIL_ADDRESS_B = "candidate.b@atsy.com";
  private static final String CANDIDATE_EMAIL_ADDRESS_C = "candidate.c@atsy.com";

  private static final String CANDIDATE_PHONE_NUMBER_A = "+36105555555";
  private static final String CANDIDATE_PHONE_NUMBER_B = "+36106666666";
  private static final String CANDIDATE_PHONE_NUMBER_C = "+36107777777";

  private static final String NAME = "name";
  private static final String EMAIL = "email";
  private static final String PHONE = "phone";
  private static final String TEXT = "text";

  private CandidateDTO candidateA = CandidateDTO.builder()
      .name(CANDIDATE_NAME_A).email(CANDIDATE_EMAIL_ADDRESS_A).phone(CANDIDATE_PHONE_NUMBER_A).build();

  private CandidateDTO candidateB = CandidateDTO.builder()
      .name(CANDIDATE_NAME_B).email(CANDIDATE_EMAIL_ADDRESS_B).phone(CANDIDATE_PHONE_NUMBER_B).build();

  private CandidateDTO candidateC = CandidateDTO.builder()
      .name(CANDIDATE_NAME_C).email(CANDIDATE_EMAIL_ADDRESS_C).phone(CANDIDATE_PHONE_NUMBER_C).build();

  @Mock
  private CandidateService candidateService;
  @InjectMocks
  private CandidateController candidateController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{candidateController};
  }

  @Test
  public void loadPageShouldRespondClientErrorPageWhenSortingIsMissing() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL)
        .param("order", "asc"))
        .andExpect(status().isBadRequest());

    verifyZeroInteractions(candidateService);
  }

  @Test
  public void loadPageShouldRespondClientErrorPageWhenOrderIsMissing() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL)
        .param("sort", "name"))
        .andExpect(status().isBadRequest());

    verifyZeroInteractions(candidateService);
  }


  public void loadPageShouldRespond() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL)
        .param("order", "asc").param("sort", TEXT))
        //.andExpect(status().isInternalServerError())
        .andDo(print());
  }


}
