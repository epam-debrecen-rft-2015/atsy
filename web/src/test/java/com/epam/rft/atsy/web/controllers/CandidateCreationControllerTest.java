package com.epam.rft.atsy.web.controllers;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CandidateCreationControllerTest extends AbstractControllerTest {
  private static final String VIEW_NAME = "candidate_create";

  private static final String ERROR_VIEW_NAME = "error";

  private static final String CANDIDATE_CREATION_REQUEST_URL = "/secure/candidate/details";

  private static final Long CANDIDATE_ID = 1L;

  private static final Long NON_EXISTENT_CANDIDATE_ID = -1L;

  private static final String EXISTING_CANDIDATE_REQUEST_URL =
      "/secure/candidate/details/" + CANDIDATE_ID.toString();

  private static final String NON_EXISTENT_CANDIDATE_REQUEST_URL =
      "/secure/candidate/details/" + NON_EXISTENT_CANDIDATE_ID.toString();

  private static final String CANDIDATE_OBJECT_KEY = "candidate";

  private CandidateDTO emptyCandidateDto;

  private CandidateDTO persistedCandidateDto;

  @Mock
  private CandidateService candidateService;

  @InjectMocks
  private CandidateCreationController candidateCreationController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{candidateCreationController};
  }

  @Before
  public void setUpTestDate() {
    emptyCandidateDto = CandidateDTO.builder().deleted(false).build();

    persistedCandidateDto = CandidateDTO.builder().id(CANDIDATE_ID).deleted(false).build();
  }

  @Test
  public void loadCandidateShouldRenderCandidateCreationViewWithEmptyDtoWhenRequestUrlIsCandidateCreationRequestUrl()
      throws Exception {
    mockMvc.perform(get(CANDIDATE_CREATION_REQUEST_URL))
        .andExpect(status().isOk())
        .andExpect(view().name(VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX))
        .andExpect(model().attributeExists(CANDIDATE_OBJECT_KEY))
        .andExpect(model().attribute(CANDIDATE_OBJECT_KEY, emptyCandidateDto));
  }

  @Test
  public void loadCandidateShouldRenderCandidateDetailsViewWhenCandidateWithIdInPathParamExists()
      throws Exception {
    given(candidateService.getCandidate(CANDIDATE_ID)).willReturn(persistedCandidateDto);

    mockMvc.perform(get(EXISTING_CANDIDATE_REQUEST_URL))
        .andExpect(status().isOk())
        .andExpect(view().name(VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX))
        .andExpect(model().attributeExists(CANDIDATE_OBJECT_KEY))
        .andExpect(model().attribute(CANDIDATE_OBJECT_KEY, persistedCandidateDto));

    then(candidateService).should().getCandidate(CANDIDATE_ID);
  }

  @Test
  public void loadCandidateShouldRenderErrorViewWhenThereIsNoCandidateWithIdInPathParam()
      throws Exception {
    given(candidateService.getCandidate(NON_EXISTENT_CANDIDATE_ID))
        .willThrow(IllegalArgumentException.class);

    mockMvc.perform(get(NON_EXISTENT_CANDIDATE_REQUEST_URL))
        .andExpect(status().isInternalServerError())
        .andExpect(view().name(ERROR_VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + ERROR_VIEW_NAME + VIEW_SUFFIX));

    then(candidateService).should().getCandidate(NON_EXISTENT_CANDIDATE_ID);
  }
}
