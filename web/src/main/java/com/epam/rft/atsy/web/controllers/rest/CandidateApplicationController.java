package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Locale;
import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/secure/applications/{candidateId}")
public class CandidateApplicationController {

  private static final String APPLICATION_STATE = "candidate.table.state.";

  @Resource
  private StatesHistoryService statesHistoryService;

  @Resource
  private MessageKeyResolver messageKeyResolver;

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
          messageKeyResolver.resolveMessageOrDefault(APPLICATION_STATE + stateType, stateType);

      candidateApplicationDTO.setStateType(stateType);
    }
    return applicationStates;
  }
}
