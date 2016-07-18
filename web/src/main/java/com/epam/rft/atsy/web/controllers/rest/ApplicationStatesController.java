package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.StatesService;
import com.epam.rft.atsy.service.domain.states.StateViewDTO;

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
  private StatesService statesService;

  @Resource
  private MessageSource messageSource;

  @RequestMapping(method = RequestMethod.GET)
  public Collection<StateViewDTO> loadApplications(
      @PathVariable(value = "applicationId") Long applicationId, Locale locale) {
    Collection<StateViewDTO>
        applicationStates =
        statesService.getStatesByApplicationId(applicationId);

    for (StateViewDTO stateDTO : applicationStates) {
      String stateType = stateDTO.getStateType();
      stateType =
          messageSource.getMessage(APPLICATION_STATE + stateType, new Object[]{stateType}, locale);
      stateDTO.setStateType(stateType);
    }

    return applicationStates;
  }
}
