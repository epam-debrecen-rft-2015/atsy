package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Locale;
import javax.annotation.Resource;

/**
 * A REST controller that is responsible for providing every information about the applications of
 * the candidates.
 */
@RestController
@RequestMapping(value = "/secure/applications/{candidateId}")
public class CandidateApplicationController {

  private static final String APPLICATION_STATE = "candidate.table.state.";

  @Resource
  private StatesHistoryService statesHistoryService;

  @Resource
  private MessageSource messageSource;

  /**
   * Loads and returns all applications of the candidate with the specified identifier, using the
   * specified language.
   * @param candidateId the identifier of the candidate whose applications will be given back
   * @param locale specifies to language to use
   * @return a collection with all applications of the specified candidate
   */
  @RequestMapping(method = RequestMethod.GET)
  public Collection<CandidateApplicationDTO> loadApplications(
      @PathVariable(value = "candidateId") Long candidateId, Locale locale) {
    Collection<CandidateApplicationDTO>
        applicationStates =
        statesHistoryService
            .getCandidateApplicationsByCandidateIdOrderByModificationDateDesc(candidateId);

    for (CandidateApplicationDTO candidateApplicationDTO : applicationStates) {
      String stateType = candidateApplicationDTO.getStateType();
      stateType =
          messageSource.getMessage(APPLICATION_STATE + stateType, new Object[]{stateType}, locale);
      candidateApplicationDTO.setStateType(stateType);
    }
    return applicationStates;
  }
}
