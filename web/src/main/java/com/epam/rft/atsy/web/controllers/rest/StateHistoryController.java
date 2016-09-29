package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import com.epam.rft.atsy.web.FieldErrorResponseComposer;
import com.epam.rft.atsy.web.StateHistoryViewRepresentation;
import com.epam.rft.atsy.web.exceptionhandling.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Locale;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/secure/application_state")
public class StateHistoryController {

  private static final String POSITION_NOT_FOUND_MESSAGE_KEY = "position.not.found.error.message";
  private static final String CHANNEL_NOT_FOUND_MESSAGE_KEY = "channel.not.found.error.message";
  private static final String FIELD_POSITION_NAME = "positionName";
  private static final String FIELD_CHANNEL_NAME = "channelName";
  private static final String NEW_STATE = "newstate";

  @Autowired
  private StatesHistoryService statesHistoryService;

  @Autowired
  private ApplicationsService applicationsService;

  @Autowired
  private ChannelService channelService;

  @Autowired
  private PositionService positionService;

  @Autowired
  private FieldErrorResponseComposer fieldErrorResponseComposer;

  /**
   * This method is used to save new states or update the information of the latest state.
   * @param applicationId identifier of the application whose states are viewed and edited
   * @param stateHistoryViewRepresentation this attribute contains all the state information of the
   * given application
   * @return a ModelAndView object filled with the data to stay on the same page and view the states
   * of the same application, but including the latest modification
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity saveOrUpdate(@RequestParam Long applicationId,
                                     @Valid @RequestBody StateHistoryViewRepresentation stateHistoryViewRepresentation,
                                     BindingResult bindingResult, Locale locale) {

    StateHistoryDTO stateHistoryDTO;
    String stateName = stateHistoryViewRepresentation.getStateName();

    if (isNewState(stateName)) {
      validateNewState(stateHistoryViewRepresentation, bindingResult);
    }

    if (bindingResult.hasErrors()) {
      return fieldErrorResponseComposer.composeResponse(bindingResult);
    } else {
      stateHistoryDTO = StateHistoryDTO.builder()
          .id(stateHistoryViewRepresentation.getId())
          .applicationDTO(applicationsService.getApplicationDtoById(applicationId))
          .candidateId(stateHistoryViewRepresentation.getCandidateId())
          .description(stateHistoryViewRepresentation.getDescription())
          .result(stateHistoryViewRepresentation.getResult())
          .offeredMoney(stateHistoryViewRepresentation.getOfferedMoney())
          .claim(stateHistoryViewRepresentation.getClaim())
          .feedbackDate(stateHistoryViewRepresentation.getFeedbackDate())
          .dayOfStart(stateHistoryViewRepresentation.getDayOfStart())
          .dateOfEnter(stateHistoryViewRepresentation.getDateOfEnter())
          .creationDate(null)
          .stateDTO(StateDTO.builder().id(stateHistoryViewRepresentation.getStateId())
              .name(stateHistoryViewRepresentation.getStateName()).build())
          .recommendation(stateHistoryViewRepresentation.getRecommendation() != null ?
              stateHistoryViewRepresentation.getRecommendation() == 1 : null)
          .reviewerName(stateHistoryViewRepresentation.getReviewerName())
          .recommendedPositionLevel(stateHistoryViewRepresentation.getRecommendedPositionLevel())
          .build();

      if (isNewState(stateName)) {
        ChannelDTO
            channelDTO =
            channelService.getChannelDtoByName(stateHistoryViewRepresentation.getChannelName());
        PositionDTO
            positionDTO =
            positionService.getPositionDtoByName(stateHistoryViewRepresentation.getPositionName());

        stateHistoryDTO.getApplicationDTO().setChannelId(channelDTO.getId());
        stateHistoryDTO.getApplicationDTO().setPositionId(positionDTO.getId());

        stateHistoryDTO.setChannel(channelDTO);
        stateHistoryDTO.setPosition(positionDTO);
      }

      statesHistoryService.saveStateHistory(stateHistoryDTO);
      return new ResponseEntity<>(Collections.singletonMap("applicationId", applicationId),
          HttpStatus.OK);
    }
  }

  /**
   * This method is used to pre-validate new states.
   * @param stateHistoryViewRepresentation this attribute contains all the state information of the
   * given application
   * @return a ResponseEntity which contains the HttpStatus code and the error message if it exists
   */
  @RequestMapping(path = "/validate", method = RequestMethod.POST)
  public ResponseEntity validateStateHistoryViewRepresentation(
      @Valid @RequestBody StateHistoryViewRepresentation stateHistoryViewRepresentation,
      BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return fieldErrorResponseComposer.composeResponse(bindingResult);
    }
    return new ResponseEntity(RestResponse.NO_ERROR, HttpStatus.OK);
  }


  protected boolean isNewState(String stateName) {
    return stateName != null && stateName.equals(NEW_STATE);
  }

  protected void validateNewState(StateHistoryViewRepresentation stateHistoryViewRepresentation,
                                  BindingResult bindingResult) {
    if (positionService.getPositionDtoByName(stateHistoryViewRepresentation.getPositionName())
        == null) {
      bindingResult.rejectValue(FIELD_POSITION_NAME, null, POSITION_NOT_FOUND_MESSAGE_KEY);
    }
    if (channelService.getChannelDtoByName(stateHistoryViewRepresentation.getChannelName())
        == null) {
      bindingResult.rejectValue(FIELD_CHANNEL_NAME, null, CHANNEL_NOT_FOUND_MESSAGE_KEY);
    }
  }
}