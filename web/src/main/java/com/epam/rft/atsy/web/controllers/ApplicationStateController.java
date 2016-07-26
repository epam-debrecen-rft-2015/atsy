package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.StatesService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryViewDTO;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Locale;
import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/secure/application_state")
public class ApplicationStateController {

  private static final String VIEW_NAME = "application_state";
  private static final String APPLICATION_STATE = "candidate.table.state.";
  private static final String STATES_OBJECT_KEY = "states";
  @Resource
  private StatesService statesService;

  @Resource
  private MessageSource messageSource;

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView loadPage(@RequestParam Long applicationId, Locale locale) {
    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
    modelAndView.addObject("applicationId", applicationId);

    Collection<StateHistoryViewDTO>
        applicationStates =
        statesService.getStatesByApplicationId(applicationId);

    for (StateHistoryViewDTO stateHistoryViewDTO : applicationStates) {
      String stateType = stateHistoryViewDTO.getStateDTO().getName();
      stateType =
          messageSource.getMessage(APPLICATION_STATE + stateType, new Object[]{stateType}, locale);
      stateHistoryViewDTO
          .setStateDTO(new StateDTO(stateHistoryViewDTO.getStateDTO().getId(),stateType));
    }

    modelAndView.addObject(STATES_OBJECT_KEY, applicationStates);
    return modelAndView;
  }
}
