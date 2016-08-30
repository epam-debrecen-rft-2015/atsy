package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * A controller which redirects from the root page to another page.
 */
@Controller
@RequestMapping("/")
public class RootRedirectionController {

  @Autowired
  private AuthenticationService authenticationService;

  /**
   * This method redirects the user to the welcome page if he/she logged in, otherwise it redirects
   * to the login page.
   * @return a ModelAndView object which contains the name of the page to be loaded
   */
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView pageLoad() {

    if (authenticationService.isCurrentUserAuthenticated()) {
      return new ModelAndView("redirect:/secure/welcome");
    } else {
      return new ModelAndView("redirect:/login");
    }

  }
}