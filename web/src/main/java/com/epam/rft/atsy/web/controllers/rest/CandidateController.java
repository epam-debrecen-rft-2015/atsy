package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.handler.CandidateHandlerDTO;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by tothd on 2015. 11. 07..
 */
@RestController
@RequestMapping(value = "/secure/candidates")
public class CandidateController {

    private static final String VIEW_NAME = "candidates";

    @Resource
    CandidateService candidateService;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadPage(@Valid @ModelAttribute CandidateHandlerDTO candidateHandlerDTO) {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);

        Gson gson = new Gson();

        String candidatesListJson=gson.toJson(candidateService.getAllCandidate(candidateHandlerDTO));

        modelAndView.addObject("candidatesList",candidatesListJson);

        return modelAndView;
    }
}
