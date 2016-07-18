package com.epam.rft.atsy.web.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller @RequestMapping("/") public class RootRedirectionController {

    @RequestMapping(method = RequestMethod.GET) public ModelAndView pageLoad() {

        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder
            .getContext().getAuthentication().isAuthenticated()) {

            return new ModelAndView("redirect:/secure/welcome");

        } else {
            return new ModelAndView("redirect:/login");
        }

    }
}
