package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.exceptionhandling.RestResponse;
import com.epam.rft.atsy.web.helper.CandidateValidatorHelper;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import org.springframework.beans.factory.annotation.Autowired;
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
import javax.validation.Valid;

/**
 * Controller for creating a new candidate.
 */
@RestController
@RequestMapping(value = "/secure/candidate")
public class SingleCandidateController {
  private static final String COMMON_INVALID_INPUT_MESSAGE_KEY = "common.invalid.input";
  private static final String CANDIDATE_ERROR_DUPLICATE_MESSAGE_KEY = "candidate.error.duplicate";

  @Autowired
  private CandidateService candidateService;

  @Autowired
  private MessageKeyResolver messageKeyResolver;

  @Autowired
  private CandidateValidatorHelper candidateValidatorHelper;

  /**
   * Saves or updates and existing candidate.
   * @param candidateDTO an object which wraps the data of a candidate
   * @param result an object used to check if any error occurs
   * @param locale language of the response
   * @return a ResponseEntity object, which contains HTTP status code and error message if it occurs
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity saveOrUpdate(@Valid @RequestBody CandidateDTO candidateDTO,
                                     BindingResult result, Locale locale) {
    if (!result.hasErrors()) {
      Long candidateId = candidateService.saveOrUpdate(candidateDTO);
      return new ResponseEntity<>(Collections.singletonMap("id", candidateId), HttpStatus.OK);
    } else {
      RestResponse restResponse = parseValidationErrors(result.getFieldErrors(), locale);
      return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }
  }

  @RequestMapping(path = "/validate", method = RequestMethod.POST)
  public ResponseEntity validateCandidate(@Valid @RequestBody CandidateDTO candidateDTO,
                                          BindingResult result,
                                          Locale locale) {
    if (result.hasErrors()) {
      return new ResponseEntity(parseValidationErrors(result.getFieldErrors(), locale),
          HttpStatus.BAD_REQUEST);
    }

    // If candidate id is null, it means new candidate would like to be created
    if (candidateDTO.getId() == null) {
      return createResponseEntityForCandidateValidation(
          this.candidateValidatorHelper.isValidNonExistingCandidate(candidateDTO));
    } else {
      return createResponseEntityForCandidateValidation(
          this.candidateValidatorHelper.isValidExistingCandidate(candidateDTO));
    }
  }

  private ResponseEntity createResponseEntityForCandidateValidation(boolean hasNoErrors) {
    if (hasNoErrors) {
      return new ResponseEntity(RestResponse.NO_ERROR, HttpStatus.OK);
    }
    String errorMessage =
        this.messageKeyResolver.resolveMessageOrDefault(CANDIDATE_ERROR_DUPLICATE_MESSAGE_KEY);
    return new ResponseEntity(new RestResponse(errorMessage), HttpStatus.BAD_REQUEST);
  }

  private RestResponse parseValidationErrors(List<FieldError> fieldErrors, Locale locale) {
    String
        errorMessage =
        messageKeyResolver.resolveMessageOrDefault(COMMON_INVALID_INPUT_MESSAGE_KEY);
    RestResponse restResponse = new RestResponse(errorMessage);

    for (FieldError fieldError : fieldErrors) {
      restResponse.addField(fieldError.getField(),
          messageKeyResolver.resolveMessageOrDefault(fieldError.getDefaultMessage()));
    }
    return restResponse;
  }
}