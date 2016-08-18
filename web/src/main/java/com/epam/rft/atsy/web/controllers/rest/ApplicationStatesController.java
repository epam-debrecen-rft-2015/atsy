package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryViewDTO;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Locale;
import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/secure/applications_states/{applicationId}")
public class ApplicationStatesController {

  private static final String APPLICATION_STATE = "candidate.table.state.";

  @Resource
  private StatesHistoryService statesHistoryService;

  @Resource
  private MessageKeyResolver messageKeyResolver;

  @RequestMapping(method = RequestMethod.GET)
  public Collection<StateHistoryViewDTO> loadApplications(
      @PathVariable(value = "applicationId") Long applicationId, Locale locale) {
    Collection<StateHistoryViewDTO>
        applicationStates =
        statesHistoryService.getStateHistoriesByApplicationId(applicationId);

    for (StateHistoryViewDTO stateHistoryViewDTO : applicationStates) {
      String stateType = stateHistoryViewDTO.getStateDTO().getName();
      stateType =
          messageKeyResolver.resolveMessageOrDefault(APPLICATION_STATE + stateType, stateType);
      stateHistoryViewDTO
          .setStateDTO(new StateDTO(stateHistoryViewDTO.getStateDTO().getId(),stateType));
    }

    return applicationStates;
  }
}
