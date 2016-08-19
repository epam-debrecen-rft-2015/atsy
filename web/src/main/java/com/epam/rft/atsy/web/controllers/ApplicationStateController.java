package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.StateFlowService;
import com.epam.rft.atsy.service.StateService;
import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.web.StateHistoryViewRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
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

  private static final String DATE_FORMAT_CONSTANT = "yyyy-MM-dd HH:mm:ss";

  @Resource
  private StatesHistoryService statesHistoryService;

  @Resource
  private StateFlowService stateFlowService;

  @Resource
  private ApplicationsService applicationsService;

  @Resource
  private CandidateService candidateService;

  @Resource
  private StateService stateService;

  @Resource
  private MessageSource messageSource;

  @Autowired
  private ConverterService converterService;

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView loadPage(@RequestParam Long applicationId,
                               @RequestParam(required = false, name = "state") String clickedState,
                               Locale locale) {
    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
    modelAndView.addObject("applicationId", applicationId);

    List<StateHistoryViewRepresentation>
        stateHistoryViewRepresentations =
        converterService
            .convert(statesHistoryService.getStateHistoriesByApplicationId(applicationId),
                StateHistoryViewRepresentation.class);

    if (clickedState != null) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_CONSTANT);

      StateDTO clickedStateDTO = stateService.getStateDtoByName(clickedState);

      stateHistoryViewRepresentations.add(0, StateHistoryViewRepresentation.builder()
          .creationDate(simpleDateFormat.format(new Date()))
          .stateId(clickedStateDTO.getId())
          .stateName(clickedStateDTO.getName())
          .build());
    }

    ApplicationDTO applicationDTO = applicationsService.getApplication(applicationId);

    CandidateDTO candidateDTO = candidateService.getCandidate(applicationDTO.getCandidateId());

    for (StateHistoryViewRepresentation stateHistoryViewRepresentation : stateHistoryViewRepresentations) {
      stateHistoryViewRepresentation.setLanguageSkill(candidateDTO.getLanguageSkill());
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
