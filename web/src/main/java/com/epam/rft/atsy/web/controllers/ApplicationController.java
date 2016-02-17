package com.epam.rft.atsy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/secure/application")
public class ApplicationController {

    private static final String VIEW_NAME = "application";

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadPage(@RequestParam Long candidateId) {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        modelAndView.addObject("candidateId",candidateId);
        return modelAndView;
    }
}
