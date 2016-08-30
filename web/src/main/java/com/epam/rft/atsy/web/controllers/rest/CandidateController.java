package com.epam.rft.atsy.web.controllers.rest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.request.FilterRequest;
import com.epam.rft.atsy.service.request.SearchOptions;
import com.epam.rft.atsy.service.request.SortingRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import javax.annotation.Resource;

/**
 * REST controller used to retrieve information from the stored candidates in JSON format.
 * e.g.: It fills the table with data on the welcome page.
 */
@RestController
@RequestMapping(value = "/secure/candidates")
public class CandidateController {

  public static final String NAME = "name";
  public static final String EMAIL = "email";
  public static final String PHONE = "phone";

  private static final String EMPTY_JSON = "{}";
  private static final Logger LOGGER = LoggerFactory.getLogger(CandidateController.class);
  @Resource
  private CandidateService candidateService;

  /**
   * Loads the page according to the given parameters.
   * @param filter Non-required parameter which represents the requested filtering
   * @param order required parameter which represents the requested ordering (ascending/descending)
   * @param sortField required parameter which represents which field is used in sorting
   * @return A collection of CandidateDTO objects which is sorted and filtered according to the
   * given parameters
   */
  @RequestMapping(method = RequestMethod.GET)
  public Collection<CandidateDTO> loadPage(
      @RequestParam(value = "filter", required = false) String filter,
      @RequestParam("order") String order,
      @RequestParam("sort") String sortField) {

    SortingRequest.Order
        orderDirection =
        SortingRequest.Order.valueOf(StringUtils.upperCase(order));
    SortingRequest.Field fieldName = SortingRequest.Field.valueOf(StringUtils.upperCase(sortField));

    FilterRequest filterRequest = FilterRequest.builder()
        .order(orderDirection)
        .fieldName(fieldName)
        .searchOptions(parseFilters(filter))
        .build();

    return candidateService.getAllCandidate(filterRequest);
  }

  private SearchOptions parseFilters(String filterJson) {
    if (StringUtils.isNotEmpty(filterJson) && !filterJson.equals(EMPTY_JSON)) {
      try {
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(filterJson);

        String name = getValueFromJsonObjectByMemberName(jsonObject, NAME);
        String email = getValueFromJsonObjectByMemberName(jsonObject, EMAIL);
        String phone = getValueFromJsonObjectByMemberName(jsonObject, PHONE);
        return SearchOptions.builder().name(name).email(email).phone(phone).build();

      } catch (JsonSyntaxException e) {
        LOGGER.error("Cannot read filters from json", e);
      }
    }
    return SearchOptions.builder().name(StringUtils.EMPTY).email(StringUtils.EMPTY)
        .phone(StringUtils.EMPTY).build();
  }

  private String getValueFromJsonObjectByMemberName(JsonObject jsonObject, String memberName) {
    if (jsonObject.get(memberName) != null) {
      return jsonObject.get(memberName).getAsString();
    }
    return StringUtils.EMPTY;
  }
}
