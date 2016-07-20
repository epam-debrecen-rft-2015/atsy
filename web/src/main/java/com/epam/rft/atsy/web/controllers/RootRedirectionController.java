package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.security.CustomSecurityContextHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class RootRedirectionController {

  @Autowired
  private CustomSecurityContextHolderService customSecurityContextHolderService;

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView pageLoad() {

    if (customSecurityContextHolderService.isCurrentUserAuthenticated()) {
      return new ModelAndView("redirect:/secure/welcome");
    } else {
      return new ModelAndView("redirect:/login");
    }

  }
}