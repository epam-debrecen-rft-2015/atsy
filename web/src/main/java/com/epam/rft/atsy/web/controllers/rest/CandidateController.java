package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.persistence.request.CandidateRequestDTO;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;

/**
 * Created by tothd on 2015. 11. 07..
 */
@RestController
@RequestMapping(value = "/secure/candidates")
public class CandidateController {

    @Resource
    CandidateService candidateService;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<CandidateDTO> loadPage(@Valid @RequestBody CandidateRequestDTO candidateRequestDTO) {
        return candidateService.getAllCandidate(candidateRequestDTO);
    }
}
