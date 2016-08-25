package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import com.epam.rft.atsy.web.FieldErrorResponseComposer;
import com.epam.rft.atsy.web.StateHistoryViewRepresentation;
import com.epam.rft.atsy.web.exceptionhandling.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Locale;
import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/secure/application_state")
public class StateHistoryController {

  private static final String DATE_FORMAT_CONSTANT = "yyyy-MM-dd HH:mm";

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_CONSTANT);

  @Resource
  private StatesHistoryService statesHistoryService;

  @Resource
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

    StateHistoryDTO stateHistoryDTO = null;

    if (bindingResult.hasErrors()) {
      return fieldErrorResponseComposer.composeResponse(bindingResult);
    } else {
      try {
        stateHistoryDTO = StateHistoryDTO.builder()
            .id(stateHistoryViewRepresentation.getId())
            .candidateId(stateHistoryViewRepresentation.getCandidateId())
            .languageSkill(stateHistoryViewRepresentation.getLanguageSkill())
            .description(stateHistoryViewRepresentation.getDescription())
            .result(stateHistoryViewRepresentation.getResult())
            .offeredMoney(stateHistoryViewRepresentation.getOfferedMoney())
            .claim(stateHistoryViewRepresentation.getClaim())
            .feedbackDate(stateHistoryViewRepresentation.getFeedbackDate() != null
                && !stateHistoryViewRepresentation.getFeedbackDate().isEmpty() ? DATE_FORMAT
                .parse(stateHistoryViewRepresentation.getFeedbackDate()) : null)
            .dayOfStart(stateHistoryViewRepresentation.getDayOfStart() != null
                && !stateHistoryViewRepresentation.getDayOfStart().isEmpty() ? DATE_FORMAT
                .parse(stateHistoryViewRepresentation.getDayOfStart()) : null)
            .creationDate(null)
            .stateDTO(StateDTO.builder().id(stateHistoryViewRepresentation.getStateId())
                .name(stateHistoryViewRepresentation.getStateName()).build())
            .recommendation(stateHistoryViewRepresentation.getRecommendation() != null ?
                stateHistoryViewRepresentation.getRecommendation() == 1 : null)
            .reviewerName(stateHistoryViewRepresentation.getReviewerName())
            .recommendedPositionLevel(stateHistoryViewRepresentation.getRecommendedPositionLevel())
            .build();

      } catch (ParseException e) {
        RestResponse restResponse = new RestResponse(e.getMessage());

        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
      }

      statesHistoryService.saveStateHistory(stateHistoryDTO, applicationId);

      return new ResponseEntity<>(Collections.singletonMap("applicationId", applicationId),
          HttpStatus.OK);
    }
  }
}
