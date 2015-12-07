package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.ApplicationService;
import com.epam.rft.atsy.service.domain.states.NewStateDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Created by mates on 2015. 12. 07..
 */
@Controller
@RequestMapping(value = "/secure/new_application_popup")
public class PopupController {
    @Resource
    private ApplicationService applicationService;

    @Resource
    private MessageSource messageSource;


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity saveOrUpdate(@ModelAttribute NewStateDTO candidateDTO, BindingResult result, Locale locale) {
        ResponseEntity entity;
        if (!result.hasErrors()) {
            Long stateId =  applicationService.saveState(candidateDTO);
            entity = new ResponseEntity<Long>(stateId, HttpStatus.OK);
        } else {

            entity = new ResponseEntity<List<FieldError>>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
