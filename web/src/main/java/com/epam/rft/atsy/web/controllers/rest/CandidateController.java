package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.persistence.request.CandidateRequestDTO;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    private CandidateService candidateService;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<CandidateDTO> loadPage(@RequestParam("order") String order, @RequestParam("sort") String sortField) {
        CandidateRequestDTO requestDTO = new CandidateRequestDTO();
        requestDTO.setOrder(CandidateRequestDTO.Order.valueOf(StringUtils.upperCase(order)));
        requestDTO.setFieldName(sortField);
        return candidateService.getAllCandidate(requestDTO);
    }
}
