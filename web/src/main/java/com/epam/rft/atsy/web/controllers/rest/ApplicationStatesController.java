package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.StatesService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateViewHistoryDTO;
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
  public Collection<StateViewHistoryDTO> loadApplications(
      @PathVariable(value = "applicationId") Long applicationId, Locale locale) {
    Collection<StateViewHistoryDTO>
        applicationStates =
        statesService.getStatesByApplicationId(applicationId);

    for (StateViewHistoryDTO stateViewHistoryDTO : applicationStates) {
      String stateType = stateViewHistoryDTO.getStateDTO().getName();
      stateType =
          messageSource.getMessage(APPLICATION_STATE + stateType, new Object[]{stateType}, locale);
      stateViewHistoryDTO.setStateDTO(new StateDTO(stateViewHistoryDTO.getStateDTO().getId(),stateType));
    }

    return applicationStates;
  }
}
