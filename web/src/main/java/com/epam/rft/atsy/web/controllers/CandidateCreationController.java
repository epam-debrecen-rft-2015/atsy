package com.epam.rft.atsy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by mates on 2015. 11. 17..
 */
@Controller
@RequestMapping(path = "/secure/newcandidate")
public class CandidateCreationController {

    private static final String VIEW_NAME = "candidate_create";


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadPage() {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        return modelAndView;
    }
}
