package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.CandidateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by tothd on 2015. 11. 07..
 */
@RestController
@RequestMapping(value = "/secure/welcome")
public class WelcomeController {

    private static final String VIEW_NAME = "candidates";

    @Resource
    CandidateService candidateService;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadPage() {
        return new ModelAndView(VIEW_NAME);
    }
}
