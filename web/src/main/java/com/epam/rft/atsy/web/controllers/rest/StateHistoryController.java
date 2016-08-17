package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import com.epam.rft.atsy.web.StateHistoryViewRepresentation;
import com.epam.rft.atsy.web.exceptionhandling.RestResponse;
import org.springframework.context.MessageSource;
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

  private static final String COMMON_INVALID_INPUT_MESSAGE_KEY = "common.invalid.input";

  @Resource
  private StatesHistoryService statesHistoryService;

  @Resource
  private MessageSource messageSource;

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity saveOrUpdate(@RequestParam Long applicationId,
                                     @Valid @RequestBody StateHistoryViewRepresentation stateHistoryViewRepresentation,
                                     BindingResult bindingResult, Locale locale) {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_CONSTANT);

    StateHistoryDTO stateHistoryDTO = null;

    if (bindingResult.hasErrors()) {
      String
          invalidInputMessage =
          messageSource.getMessage(COMMON_INVALID_INPUT_MESSAGE_KEY, null, locale);

      RestResponse restResponse = new RestResponse(invalidInputMessage);

      bindingResult.getFieldErrors().forEach(error -> {
        restResponse.addField(error.getField(), messageSource.getMessage(error.getDefaultMessage(),
            new Object[0], locale));
      });

      return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
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
                && !stateHistoryViewRepresentation.getFeedbackDate().isEmpty() ? simpleDateFormat
                .parse(stateHistoryViewRepresentation.getFeedbackDate()) : null)
            .dayOfStart(stateHistoryViewRepresentation.getDayOfStart() != null
                && !stateHistoryViewRepresentation.getDayOfStart().isEmpty() ? simpleDateFormat
                .parse(stateHistoryViewRepresentation.getDayOfStart()) : null)
            .creationDate(null)
            .stateDTO(StateDTO.builder().id(stateHistoryViewRepresentation.getStateId())
                .name(stateHistoryViewRepresentation.getStateName()).build())
            .recommendation(stateHistoryViewRepresentation.getRecommendation() != null ?
                stateHistoryViewRepresentation.getRecommendation() == 1 : null)
            .build();

      } catch (ParseException e) {
        //TODO: what should we do about this?
        e.printStackTrace();
      }

      statesHistoryService.saveStateHistory(stateHistoryDTO, applicationId);

      return new ResponseEntity<>(Collections.singletonMap("applicationId", applicationId),
          HttpStatus.OK);
    }
  }
}
