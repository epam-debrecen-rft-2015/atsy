package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryViewDTO;
import org.springframework.context.MessageSource;
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
  private MessageSource messageSource;

  @RequestMapping(method = RequestMethod.GET)
  public Collection<StateHistoryViewDTO> loadApplications(
      @PathVariable(value = "applicationId") Long applicationId, Locale locale) {

    Collection<StateHistoryViewDTO> applicationStates =
        statesHistoryService.getStateHistoriesByApplicationId(applicationId);

    for (StateHistoryViewDTO stateHistoryViewDTO : applicationStates) {
      String stateType = stateHistoryViewDTO.getStateDTO().getName();

      String localizedStateName =
          messageSource.getMessage(APPLICATION_STATE + stateType, null, locale);

      stateHistoryViewDTO
          .setStateDTO(new StateDTO(stateHistoryViewDTO.getStateDTO().getId(), localizedStateName));
    }

    return applicationStates;
  }
}
