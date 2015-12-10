package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.ApplicationService;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * Created by tothd on 2015. 12. 09..
 */
@RestController
@RequestMapping(value = "/secure/applications/{candidateId}")
public class CandidateApplicationController {


    @Resource
    private ApplicationService applicationService;


    @RequestMapping(method = RequestMethod.GET)
    public Collection<CandidateApplicationDTO> loadApplications(@PathVariable(value = "candidateId") Long candidateId) {
        return applicationService.getStatesByCandidateId(candidateId);
    }
}
