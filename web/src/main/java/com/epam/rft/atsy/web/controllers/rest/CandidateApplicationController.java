package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.ApplicationService;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Locale;

/**
 * Created by tothd on 2015. 12. 09..
 */
@RestController
@RequestMapping(value = "/secure/applications/{candidateId}")
public class CandidateApplicationController {

    private static final String APPLICATION_STATE= "candidate.table.state.";

    @Resource
    private ApplicationService applicationService;

    @Resource
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<CandidateApplicationDTO> loadApplications(@PathVariable(value = "candidateId") Long candidateId, Locale locale) {
        Collection<CandidateApplicationDTO> applicationStates = applicationService.getStatesByCandidateId(candidateId);

        for(CandidateApplicationDTO candidateApplicationDTO : applicationStates){
            String stateType = candidateApplicationDTO.getStateType();
            stateType=messageSource.getMessage(APPLICATION_STATE+stateType,new Object[]{stateType},locale);
            candidateApplicationDTO.setStateType(stateType);
        }
        return applicationStates;
    }
}
