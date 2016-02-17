package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mates on 2015. 11. 18..
 */
@RestController
@RequestMapping(value = "/secure/candidate")
public class SingleCandidateController {
    private static final String DUPLICATE_CANDIDATE_ERROR_KEY = "candidate.error.duplicate";
    private static final String TECHNICAL_ERROR_MESSAGE_KEY = "technical.error.message";
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleCandidateController.class);
    @Resource
    private CandidateService candidateService;

    @Resource
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity saveOrUpdate(@Valid @RequestBody CandidateDTO candidateDTO, BindingResult result, Locale locale) {
        ResponseEntity entity;
        if (!result.hasErrors()) {
            Long candidateId = candidateService.saveOrUpdate(candidateDTO);
            entity = new ResponseEntity<Long>(candidateId, HttpStatus.OK);
        } else {
            Map<String, String> errorMessages =parseValidationErrors(result.getFieldErrors(),locale);
            entity = new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity handleDuplicateException(Locale locale, DuplicateRecordException ex) {
        return new ResponseEntity<String>(messageSource.getMessage(DUPLICATE_CANDIDATE_ERROR_KEY,
                new Object[]{ex.getName()}, locale), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Locale locale, Exception ex) {
        LOGGER.error("Error while saving position changes", ex);
        return new ResponseEntity<String>(messageSource.getMessage(TECHNICAL_ERROR_MESSAGE_KEY,
                null, locale), HttpStatus.BAD_REQUEST);
    }

    private Map<String,String> parseValidationErrors(List<FieldError> fieldErrors, Locale locale){
        Map<String,String> fieldNamesWithError = new HashMap<String,String>();
        for(FieldError fieldError: fieldErrors){
            fieldNamesWithError.put(fieldError.getField(), messageSource.getMessage(fieldError.getDefaultMessage(), new Object[0], locale));
        }
        return fieldNamesWithError;
    }
}
