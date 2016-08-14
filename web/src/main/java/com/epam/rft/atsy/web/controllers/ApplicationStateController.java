package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.StateFlowService;
import com.epam.rft.atsy.service.StateService;
import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.web.StateHistoryViewRepresentation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/secure/application_state")
public class ApplicationStateController {

  private static final String VIEW_NAME = "application_state";
  private static final String APPLICATION_STATE = "candidate.table.state.";
  private static final String STATES_OBJECT_KEY = "states";
  private static final String STATE_FLOW_OBJECT_KEY = "stateflows";

  private final static Type
      STATE_HISTORY_VIEW_REPRESENTATION_LIST_TYPE =
      new TypeToken<List<StateHistoryViewRepresentation>>() {
      }.getType();

  @Resource
  private StatesHistoryService statesHistoryService;

  @Resource
  private StateFlowService stateFlowService;

  @Resource
  private StateService stateService;

  @Resource
  private MessageSource messageSource;

  @Resource
  private ModelMapper modelMapper;

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView loadPage(@RequestParam Long applicationId,
                               @RequestParam(required = false, name = "state") String clickedState,
                               Locale locale) {
    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
    modelAndView.addObject("applicationId", applicationId);

    List<StateHistoryViewRepresentation>
        stateHistoryViewRepresentations =
        modelMapper.map(statesHistoryService.getStateHistoriesByApplicationId(applicationId),
            STATE_HISTORY_VIEW_REPRESENTATION_LIST_TYPE);

    if (clickedState != null) {
      StateDTO clickedStateDTO = stateService.getStateDtoByName(clickedState);

      stateHistoryViewRepresentations.add(0, StateHistoryViewRepresentation.builder()
          .stateId(clickedStateDTO.getId())
          .stateName(clickedStateDTO.getName())
          .build());
    }

    for (StateHistoryViewRepresentation stateHistoryViewRepresentation : stateHistoryViewRepresentations) {
      String stateType = stateHistoryViewRepresentation.getStateName();
      stateType =
          messageSource
              .getMessage(APPLICATION_STATE + stateHistoryViewRepresentation.getStateName(),
                  new Object[]{stateType}, locale);
      stateHistoryViewRepresentation.setStateFullName(stateType);
    }

    modelAndView.addObject(STATE_FLOW_OBJECT_KEY, stateFlowService.getStateFlowDTOByFromStateDTO(
        new StateDTO(stateHistoryViewRepresentations.get(0).getStateId(),
            stateHistoryViewRepresentations.get(0).getStateName())));
    modelAndView.addObject(STATES_OBJECT_KEY, stateHistoryViewRepresentations);
    return modelAndView;
  }
}
