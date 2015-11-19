package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.persistence.request.SortingRequest;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Locale;

/**
 * Created by mates on 2015. 11. 18..
 */
@RestController
@RequestMapping(value = "/secure/candidate")
public class SingleCandidateController {
    private static final String EMPTY_CANDIDATE_NAME_MESSAGE_KEY = "candidate.error.name.empty";


    private static final Logger LOGGER = LoggerFactory.getLogger(SingleCandidateController.class);
    @Resource
    private CandidateService candidateService;

    @Resource
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> saveOrUpdate(@RequestBody CandidateDTO candidateDTO, BindingResult result, Locale locale) {
        ResponseEntity entity = new ResponseEntity<String>("", HttpStatus.OK);

        if (!result.hasErrors()) {
            candidateService.saveOrUpdate(candidateDTO);
        } else {
            entity = new ResponseEntity<String>(messageSource.getMessage("hiba",
                    null, locale), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
