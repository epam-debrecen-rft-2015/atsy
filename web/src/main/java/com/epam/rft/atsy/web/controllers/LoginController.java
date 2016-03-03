package com.epam.rft.atsy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {
    private static final String VIEW_NAME = "login";

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView pageLoad(HttpServletRequest request) {
        ModelAndView model = new ModelAndView(VIEW_NAME);
        return model;
    }

}
