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

@RestController
@RequestMapping(value = "/secure/candidates")
public class CandidateController {

  public static final String NAME = "name";
  public static final String EMAIL = "email";
  public static final String PHONE = "phone";

  private static final String EMPTY_STRING = StringUtils.EMPTY;
  private static final Logger LOGGER = LoggerFactory.getLogger(CandidateController.class);
  @Resource
  private CandidateService candidateService;


  @RequestMapping(method = RequestMethod.GET)
  public Collection<CandidateDTO> loadPage(
      @RequestParam(value = "filter", required = false) String filter,
      @RequestParam("order") String order,
      @RequestParam("sort") String sortField) {

    SortingRequest.Order orderDirection = SortingRequest.Order.valueOf(StringUtils.upperCase(order));
    SortingRequest.Field fieldName = SortingRequest.Field.valueOf(StringUtils.upperCase(sortField));

    FilterRequest filterRequest = FilterRequest.builder()
        .order(orderDirection)
        .fieldName(fieldName)
        .searchOptions(parseFilters(filter))
        .build();

    return candidateService.getAllCandidate(filterRequest);
  }

  private SearchOptions parseFilters(String filterJson) {
    if (filterJson != null && StringUtils.isNotEmpty(filterJson) && !filterJson.equals("{}")) {
      try {
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(filterJson);

        String name = (jsonObject.get(NAME) != null) ? jsonObject.get(NAME).getAsString() : EMPTY_STRING;
        String email = (jsonObject.get(EMAIL) != null) ? jsonObject.get(EMAIL).getAsString() : EMPTY_STRING;
        String phone = (jsonObject.get(PHONE) != null) ? jsonObject.get(PHONE).getAsString() : EMPTY_STRING;
        return SearchOptions.builder().name(name).email(email).phone(phone).build();

      } catch (JsonSyntaxException e) {
        LOGGER.error("Cannot read filters from json", e);
      }
    }
    return SearchOptions.builder().name(EMPTY_STRING).email(EMPTY_STRING).phone(EMPTY_STRING).build();
  }
}
