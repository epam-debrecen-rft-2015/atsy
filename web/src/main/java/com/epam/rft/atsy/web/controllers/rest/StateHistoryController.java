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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

  private static final String COMMON_INVALID_INPUT_MESSAGE_KEY = "common.invalid.input";

  @Resource
  private StatesHistoryService statesHistoryService;

  @Resource
  private MessageSource messageSource;

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity saveOrUpdate(@RequestBody Long applicationId,
                                     @Valid @ModelAttribute StateHistoryViewRepresentation stateHistoryViewRepresentation,
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
            .creationDate(stateHistoryViewRepresentation.getCreationDate() != null
                && !stateHistoryViewRepresentation.getCreationDate().isEmpty() ? simpleDateFormat
                .parse(stateHistoryViewRepresentation.getCreationDate()) : null)
            .stateDTO(StateDTO.builder().id(stateHistoryViewRepresentation.getStateId())
                .name(stateHistoryViewRepresentation.getStateName()).build())
            .build();

      } catch (ParseException e) {
        //TODO: what should we do about this?
        e.printStackTrace();
      }

      //TODO: remove this, once done
      System.out.println(stateHistoryDTO);

      statesHistoryService.saveStateHistory(stateHistoryDTO, applicationId);

      return new ResponseEntity<>(Collections.singletonMap("applicationId", applicationId),
          HttpStatus.OK);
    }
  }
}
