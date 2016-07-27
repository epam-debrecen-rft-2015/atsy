package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.web.exceptionhandling.RestResponse;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
public class NewApplicationPopupController {
  private static final String VIEW_NAME = "new_application_popup";
  private static final String COMMON_INVALID_DROPDOWN_MESSAGE_KEY = "common.invalid.dropdown";

  @Resource
  private MessageSource messageSource;
  @Resource
  private ApplicationsService applicationsService;

  @RequestMapping(method = RequestMethod.GET, value = "/new_application_popup")
  public ModelAndView loadPage() {
    return new ModelAndView(VIEW_NAME);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/secure/new_application_popup")
  public Object saveOrUpdate(@Valid @ModelAttribute StateDTO stateDTO, BindingResult result, Locale locale) {
    if (result.hasErrors()) {
      RestResponse restResponse = parseValidationErrors(result.getFieldErrors(), locale);
      return new ResponseEntity(restResponse, HttpStatus.BAD_REQUEST);
    }

    stateDTO.setStateType("newstate");
    stateDTO.setStateIndex(0);
    ApplicationDTO applicationDTO = ApplicationDTO.builder()
        .creationDate(new Date())
        .candidateId(stateDTO.getCandidateId())
        .positionId(stateDTO.getPosition().getId())
        .channelId(stateDTO.getChannel().getId())
        .build();

    applicationsService.saveApplicaton(applicationDTO, stateDTO);
    return "redirect:/secure/candidate/" + stateDTO.getCandidateId();
  }

  private RestResponse parseValidationErrors(List<FieldError> fieldErrors, Locale locale) {
    String errorMessage = messageSource.getMessage(COMMON_INVALID_DROPDOWN_MESSAGE_KEY, null, locale);
    RestResponse restResponse = new RestResponse(errorMessage);

    for (FieldError fieldError : fieldErrors) {
      restResponse.addField(fieldError.getField(),
          messageSource.getMessage(fieldError.getDefaultMessage(), new Object[0], locale));
    }
    return restResponse;
  }
}
