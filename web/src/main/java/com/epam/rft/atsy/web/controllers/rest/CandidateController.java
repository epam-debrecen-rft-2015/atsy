package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.persistence.request.FilterRequest;
import com.epam.rft.atsy.persistence.request.SortingRequest;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
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

/**
 * Created by tothd on 2015. 11. 07..
 */
@RestController
@RequestMapping(value = "/secure/candidates")
public class CandidateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CandidateController.class);
    @Resource
    private CandidateService candidateService;
    @Resource
    private ObjectMapper objectMapper;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<CandidateDTO> loadPage(@RequestParam(value = "filter", required = false) String filter,
                                             @RequestParam("order") String order,
                                             @RequestParam("sort") String sortField) {
        FilterRequest sortingRequest = new FilterRequest();
        sortingRequest.setOrder(SortingRequest.Order.valueOf(StringUtils.upperCase(order)));
        sortingRequest.setFieldName(sortField);
        sortingRequest.setFilters(parseFilters(filter));
        return candidateService.getAllCandidate(sortingRequest);
    }

    private Map<String, String> parseFilters(String filterJson) {
        Map filterMap = new HashMap<>();
        if (StringUtils.isNotBlank(filterJson)) {
            try {
                filterMap = objectMapper.readValue(filterJson, Map.class);
            } catch (IOException e) {
                LOGGER.error("Cannot read filters from json", e);
            }
        }
        return filterMap;
    }
}
