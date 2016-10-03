package com.epam.rft.atsy.web.controllers.rest;

import com.google.common.base.MoreObjects;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.request.CandidateFilterRequest;
import com.epam.rft.atsy.service.request.SearchOptions;
import com.epam.rft.atsy.service.response.PagingResponse;
import com.epam.rft.atsy.web.controllers.LogicallyDeletableAbstractController;
import com.epam.rft.atsy.web.exceptionhandling.RestResponse;
import com.epam.rft.atsy.web.helper.CandidateValidatorHelper;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;

/**
 * REST controller used to retrieve information from the stored candidates in JSON format.
 * e.g.: It fills the table with data on the welcome page.
 */
@RestController
@RequestMapping(path = "/secure/candidate")
public class CandidateController extends LogicallyDeletableAbstractController<CandidateDTO> {

  private static final String COMMON_INVALID_INPUT_MESSAGE_KEY = "common.invalid.input";
  private static final String CANDIDATE_ERROR_DUPLICATE_MESSAGE_KEY = "candidate.error.duplicate";
  private static final Logger LOGGER = LoggerFactory.getLogger(CandidateController.class);

  private static final SearchOptions
      EMPTY_SERCH_OPTIONS =
      SearchOptions.builder().name(StringUtils.EMPTY).email(StringUtils.EMPTY)
          .phone(StringUtils.EMPTY).build();

  private CandidateService candidateService;
  private CandidateValidatorHelper candidateValidatorHelper;

  @Autowired
  public CandidateController(
      CandidateService candidateService,
      MessageKeyResolver messageKeyResolver, CandidateValidatorHelper candidateValidatorHelper) {
    super(candidateService, messageKeyResolver);
    this.candidateService = candidateService;
    this.candidateValidatorHelper = candidateValidatorHelper;
  }

  /**
   * Loads the page according to the given parameters.
   * @param filter Non-required parameter which represents how the result should be filtered
   * @param sortName required parameter which represents which field is used in sorting
   * @param sortOrder required parameter which represents the requested ordering
   * (ascending/descending)
   * @param pageSize required parameter which represents the size of the {@code Page}
   * @param pageNumber required parameter which represents the number of the {@code Page}
   * @return a paging response containing the appropriete {@code CandidateDTOs}
   */
  @RequestMapping(method = RequestMethod.GET)
  public PagingResponse<CandidateDTO> loadPage(
      @RequestParam(value = "filter", required = false) String filter,
      @RequestParam(value = "sortName", required = true) String sortName,
      @RequestParam(value = "sortOrder", required = true) String sortOrder,
      @RequestParam(value = "pageSize", required = true) int pageSize,
      @RequestParam(value = "pageNumber", required = true) int pageNumber) {

    //It is required because bootstraps first page is 1
    pageNumber--;

    SearchOptions searchOptions = parseFilters(filter);
    String name = MoreObjects.firstNonNull(searchOptions.getName(), StringUtils.EMPTY);
    String email = MoreObjects.firstNonNull(searchOptions.getEmail(), StringUtils.EMPTY);
    String phone = MoreObjects.firstNonNull(searchOptions.getPhone(), StringUtils.EMPTY);
    String position = MoreObjects.firstNonNull(searchOptions.getPosition(), StringUtils.EMPTY);

    CandidateFilterRequest
        candidateFilterRequest =
        CandidateFilterRequest.builder().sortName(sortName).sortOrder(sortOrder).pageSize(pageSize)
            .pageNumber(pageNumber).candidateName(name).candidateEmail(email).candidatePhone(phone)
            .candiadtePositions(position).build();

    return candidateService.getCandidatesByFilterRequest(candidateFilterRequest);
  }

  /**
   * Saves or updates and existing candidate.
   * @param candidateDTO an object which wraps the data of a candidate
   * @param result an object used to check if any error occurs
   * @param locale language of the response
   * @return a ResponseEntity object, which contains HTTP status code and error message if it occurs
   */
  @Override
  public ResponseEntity saveOrUpdate(@Valid @RequestBody CandidateDTO candidateDTO,
                                     BindingResult result, Locale locale) {
    if (!result.hasErrors()) {
      Long candidateId = candidateService.saveOrUpdate(candidateDTO);
      return new ResponseEntity<>(Collections.singletonMap("id", candidateId),
          HttpStatus.OK);
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

  private SearchOptions parseFilters(String filterJSON) {
    ObjectMapper mapper = new ObjectMapper();

    if (filterJSON == null) {
      return EMPTY_SERCH_OPTIONS;
    }

    try {
      SearchOptions result = mapper.readValue(filterJSON, SearchOptions.class);
      return result;
    } catch (IOException e) {
      LOGGER.error("Cannot read filters from json", e);
      return EMPTY_SERCH_OPTIONS;
    }
  }
}