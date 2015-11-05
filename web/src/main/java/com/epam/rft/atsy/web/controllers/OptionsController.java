package com.epam.rft.atsy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Ikantik.
 */
@Controller
@RequestMapping(path = "/secure/settings")
public class OptionsController {
    private static final String VIEW_NAME = "settings";


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadPage() {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        return modelAndView;
    }

}
