package com.epam.rft.atsy.web.controllers.rest;

import com.google.common.base.MoreObjects;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.request.CandidateFilterRequest;
import com.epam.rft.atsy.service.request.SearchOptions;
import com.epam.rft.atsy.service.response.PagingResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import javax.annotation.Resource;

/**
 * REST controller used to retrieve information from the stored candidates in JSON format.
 * e.g.: It fills the table with data on the welcome page.
 */
@RestController
@RequestMapping(value = "/secure/candidates")
public class CandidateController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CandidateController.class);

  private static final SearchOptions
      EMPTY_SERCH_OPTIONS =
      SearchOptions.builder().name(StringUtils.EMPTY).email(StringUtils.EMPTY)
          .phone(StringUtils.EMPTY).build();

  @Resource
  private CandidateService candidateService;

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