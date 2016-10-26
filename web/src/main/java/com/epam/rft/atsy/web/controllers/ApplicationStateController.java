package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.StateFlowService;
import com.epam.rft.atsy.service.StateService;
import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import com.epam.rft.atsy.web.StateHistoryViewRepresentation;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * Manages the page which shows the state history of a certain application of a certain candidate
 * and allows the user to put the application into a new state.
 */
@Controller
@RequestMapping(value = "/secure/application_state")
public class ApplicationStateController {

  private static final String VIEW_NAME = "application_state";
  private static final String APPLICATION_STATE = "candidate.table.state.";
  private static final String STATES_OBJECT_KEY = "states";
  private static final String STATE_FLOW_OBJECT_KEY = "stateflows";
  private static final String NEW_STATE = "newstate";
  private static final String CANDIDATE_OBJECT_KEY = "candidate";

  @Resource
  private StatesHistoryService statesHistoryService;

  @Resource
  private StateFlowService stateFlowService;

  @Resource
  private CandidateService candidateService;

  @Resource
  private StateService stateService;

  @Resource
  private MessageKeyResolver messageKeyResolver;

  @Autowired
  private ConverterService converterService;

  @Autowired
  private ApplicationsService applicationsService;

  @Autowired
  private ChannelService channelService;

  @Autowired
  private PositionService positionService;

  /**
   * Creates the application state page and fills it with all the state information of the given
   * application.
   * @param applicationId determines which application is viewed on the page
   * @param clickedState indicates which state button was pushed
   * @return a ModelAndView object which contains all the state information of the given application
   * and the name of the page that manages the application states
   */
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView loadPage(@RequestParam Long applicationId,
                               @RequestParam(required = false, name = "state") String clickedState) {
    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
    modelAndView.addObject("applicationId", applicationId);

    List<StateHistoryDTO> stateHistory=statesHistoryService.getStateHistoriesByApplicationId(applicationId);

    List<StateHistoryViewRepresentation>
        stateHistoryViewRepresentations =
        converterService.convert(stateHistory,StateHistoryViewRepresentation.class);

    if (clickedState != null) {
      StateDTO clickedStateDTO = stateService.getStateDtoByName(clickedState);

      Assert.notNull(clickedStateDTO);
      if (stateFlowService.isAvailableFromLastState(stateHistory, clickedState)) {
        StateHistoryViewRepresentation representation = StateHistoryViewRepresentation.builder()
            .creationDate(new Date())
            .stateId(clickedStateDTO.getId())
            .stateName(clickedStateDTO.getName())
            .build();

        if (clickedState.equals(NEW_STATE)) {
          ApplicationDTO applicationDTO = applicationsService.getApplicationDtoById(applicationId);
          representation.setChannelName(
              channelService.getChannelDtoById(applicationDTO.getChannelId()).getName());
          representation.setPositionName(
              positionService.getPositionDtoById(applicationDTO.getPositionId()).getName());
        }

        stateHistoryViewRepresentations.add(0, representation);
      }
    }


    CandidateDTO candidateDTO = candidateService.getCandidateByApplicationID(applicationId);

    for (StateHistoryViewRepresentation stateHistoryViewRepresentation : stateHistoryViewRepresentations) {
      stateHistoryViewRepresentation.setLanguageSkill(candidateDTO.getLanguageSkill());
      String stateType = stateHistoryViewRepresentation.getStateName();

      stateType =
          messageKeyResolver
              .resolveMessageOrDefault(
                  APPLICATION_STATE + stateHistoryViewRepresentation.getStateName(),
                  stateType);

      stateHistoryViewRepresentation.setStateFullName(stateType);
    }
    
    modelAndView.addObject(STATE_FLOW_OBJECT_KEY, stateFlowService.getStateFlowDTOByFromStateDTO(
        new StateDTO(stateHistoryViewRepresentations.get(0).getStateId(),
            stateHistoryViewRepresentations.get(0).getStateName())));

    modelAndView.addObject(STATES_OBJECT_KEY, stateHistoryViewRepresentations);
    modelAndView.addObject(CANDIDATE_OBJECT_KEY, candidateDTO);
    return modelAndView;
  }
}
