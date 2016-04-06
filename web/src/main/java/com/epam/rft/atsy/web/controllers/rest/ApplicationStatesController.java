package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.ApplicationService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Locale;

@RestController
@RequestMapping(value = "/secure/applications_states/{applicationId}")
public class ApplicationStatesController {

    @Resource
    private ApplicationService applicationService;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<StateDTO> loadApplications(@PathVariable(value = "applicationId") Long applicationId, Locale locale) {
        Collection<StateDTO> applicationStates = applicationService.getStatesByApplicationId(applicationId);
        return applicationStates;
    }
}
