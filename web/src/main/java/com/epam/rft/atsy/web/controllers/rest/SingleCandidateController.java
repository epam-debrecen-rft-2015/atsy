package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.exceptionhandling.RestResponse;
import com.epam.rft.atsy.web.model.file.CVStatusMonitor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/secure/candidate")
public class SingleCandidateController {
  private CVStatusMonitor cvStatusMonitor = CVStatusMonitor.getInstance();
  private static final String COMMON_INVALID_INPUT_MESSAGE_KEY = "common.invalid.input";

  @Resource
  private CandidateService candidateService;

  @Resource
  private MessageSource messageSource;

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity saveOrUpdate(@Valid @RequestBody CandidateDTO candidateDTO,
                                     BindingResult result, Locale locale, HttpSession session) {

    if (!result.hasErrors()) {

      String cvPath =
          session.getAttribute("CVPATH") == null ? null : session.getAttribute("CVPATH").toString();


      if (cvPath != null) {
        if (candidateDTO.getId() == null) {
          candidateDTO.setCvPath(cvPath);
        } else if(candidateService.getCVPathByCandidateId(candidateDTO.getId()) != null) {
          candidateDTO.setCvPath(candidateService.getCVPathByCandidateId(candidateDTO.getId()));
        } else if(candidateService.getCVPathByCandidateId(candidateDTO.getId()) == null) {
          candidateDTO.setCvPath(cvPath);
        } else if (candidateDTO.getId() != null) {
          candidateDTO.setCvPath(candidateService.getCVPathByCandidateId(candidateDTO.getId()));
        }
      } else if (candidateDTO.getId() != null) {
        candidateDTO.setCvPath(candidateService.getCVPathByCandidateId(candidateDTO.getId()));
      }
      Long candidateId = candidateService.saveOrUpdate(candidateDTO);
      session.removeAttribute("CVPATH");
      return new ResponseEntity<>(Collections.singletonMap("id", candidateId), HttpStatus.OK);

    } else {
      RestResponse restResponse = parseValidationErrors(result.getFieldErrors(), locale);

      return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }
  }

  private RestResponse parseValidationErrors(List<FieldError> fieldErrors, Locale locale) {
    String errorMessage = messageSource.getMessage(COMMON_INVALID_INPUT_MESSAGE_KEY, null, locale);

    RestResponse restResponse = new RestResponse(errorMessage);

    for (FieldError fieldError : fieldErrors) {
      restResponse.addField(fieldError.getField(),
          messageSource.getMessage(fieldError.getDefaultMessage(), new Object[0], locale));
    }

    return restResponse;
  }
}
