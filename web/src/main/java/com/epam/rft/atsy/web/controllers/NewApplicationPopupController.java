package com.epam.rft.atsy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by mates on 2015. 12. 03..
 */
@Controller
@RequestMapping(value = "/new_application_popup")
public class NewApplicationPopupController {

    private static final String VIEW_NAME = "new_application_popup";

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadPage() {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        return modelAndView;
    }
}
