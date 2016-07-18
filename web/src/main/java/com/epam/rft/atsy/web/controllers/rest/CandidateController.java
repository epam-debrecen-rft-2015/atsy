package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.request.FilterRequest;
import com.epam.rft.atsy.service.request.SearchOptions;
import com.epam.rft.atsy.service.request.SortingRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController @RequestMapping(value = "/secure/candidates") public class CandidateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CandidateController.class);
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";

    @Resource private CandidateService candidateService;
    @Resource private ObjectMapper objectMapper;

    @RequestMapping(method = RequestMethod.GET) public Collection<CandidateDTO> loadPage(
        @RequestParam(value = "filter", required = false) String filter,
        @RequestParam("order") String order, @RequestParam("sort") String sortField) {

        FilterRequest filterRequest = FilterRequest.builder()
            .order(SortingRequest.Order.valueOf(StringUtils.upperCase(order))).fieldName(sortField)
            .searchOptions(parseFilters(filter)).build();


        return candidateService.getAllCandidate(filterRequest);
    }

    private SearchOptions parseFilters(String filterJson) {
        Map<String, String> filterMap = new HashMap<>();
        if (StringUtils.isNotBlank(filterJson)) {
            try {
                filterMap = objectMapper.readValue(filterJson, Map.class);
            } catch (IOException e) {
                LOGGER.error("Cannot read filters from json", e);
            }
        }


        return SearchOptions.builder().name(filterMap.get(NAME)).email(filterMap.get(EMAIL))
            .phone(filterMap.get(PHONE)).build();
    }
}
