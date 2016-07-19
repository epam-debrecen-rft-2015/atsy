package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.StatesService;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import org.springframework.context.MessageSource;
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
  private StatesService statesService;

  @Resource
  private MessageSource messageSource;

  @RequestMapping(method = RequestMethod.GET)
  public Collection<CandidateApplicationDTO> loadApplications(
      @PathVariable(value = "candidateId") Long candidateId, Locale locale) {
    Collection<CandidateApplicationDTO>
        applicationStates =
        statesService.getCandidateApplicationsByCandidateId(candidateId);

    for (CandidateApplicationDTO candidateApplicationDTO : applicationStates) {
      String stateType = candidateApplicationDTO.getStateType();
      stateType =
          messageSource.getMessage(APPLICATION_STATE + stateType, new Object[]{stateType}, locale);
      candidateApplicationDTO.setStateType(stateType);
    }
    return applicationStates;
  }
}
