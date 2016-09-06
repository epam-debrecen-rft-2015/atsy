package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Locale;
import javax.annotation.Resource;

/**
 * A REST controller that is responsible for providing all the state information of the candidate
 * applications.
 */
@RestController
@RequestMapping(value = "/secure/applications_states/{applicationId}")
public class ApplicationStatesController {

  private static final String APPLICATION_STATE = "candidate.table.state.";

  @Resource
  private StatesHistoryService statesHistoryService;

  @Resource
  private MessageKeyResolver messageKeyResolver;

  /**
   * Returns a collections that contains all the state histories of the given application, using the
   * specified language.
   * @param applicationId the identifier of the application whose state history will be given back
   * @param locale specifies the language to use
   * @return all the state histories of the given application
   */
  @RequestMapping(method = RequestMethod.GET)
  public Collection<StateHistoryDTO> loadApplications(
      @PathVariable(value = "applicationId") Long applicationId, Locale locale) {
    Collection<StateHistoryDTO>
        applicationStates =
        statesHistoryService.getStateHistoriesByApplicationId(applicationId);

    for (StateHistoryDTO stateHistoryDTO : applicationStates) {
      String stateType = stateHistoryDTO.getStateDTO().getName();
      stateType =
          messageKeyResolver.resolveMessageOrDefault(APPLICATION_STATE + stateType, stateType);
      stateHistoryDTO
          .setStateDTO(new StateDTO(stateHistoryDTO.getStateDTO().getId(), stateType));
    }

    return applicationStates;
  }
}
