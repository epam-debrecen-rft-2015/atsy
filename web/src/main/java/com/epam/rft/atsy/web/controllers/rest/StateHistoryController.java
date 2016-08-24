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

  private static final String DATE_FORMAT_CONSTANT = "yyyy-MM-dd HH:mm:ss";

  private final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_CONSTANT);

  @Resource
  private StatesHistoryService statesHistoryService;

  @Resource
  private FieldErrorResponseComposer fieldErrorResponseComposer;

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
                && !stateHistoryViewRepresentation.getFeedbackDate().isEmpty() ? dateFormat
                .parse(stateHistoryViewRepresentation.getFeedbackDate()) : null)
            .dayOfStart(stateHistoryViewRepresentation.getDayOfStart() != null
                && !stateHistoryViewRepresentation.getDayOfStart().isEmpty() ? dateFormat
                .parse(stateHistoryViewRepresentation.getDayOfStart()) : null)
            .creationDate(stateHistoryViewRepresentation.getCreationDate() != null
                && !stateHistoryViewRepresentation.getCreationDate().isEmpty() ? dateFormat
                .parse(stateHistoryViewRepresentation.getCreationDate()) : null)
            .stateDTO(StateDTO.builder().id(stateHistoryViewRepresentation.getStateId())
                .name(stateHistoryViewRepresentation.getStateName()).build())
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
