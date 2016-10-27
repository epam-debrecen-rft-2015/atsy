package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for the login page.
 */
@Controller
@RequestMapping("/login")
public class LoginController {
  private static final String VIEW_NAME_LOGIN = "login";
  private static final String VIEW_NAME_WELCOME = "secure/welcome";

  @Autowired
  private AuthenticationService authenticationService;

  /**
   * Loads the page.
   * @return the ModelAndView object which describes the page
   */
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView pageLoad() {

    if (this.authenticationService.isCurrentUserAuthenticated()) {
      return new ModelAndView("redirect:/" + VIEW_NAME_WELCOME);
    } else {
      return new ModelAndView(VIEW_NAME_LOGIN);
    }
  }
}