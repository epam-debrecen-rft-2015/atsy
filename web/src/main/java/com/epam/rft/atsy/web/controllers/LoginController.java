package com.epam.rft.atsy.web.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

  /**
   * Loads the page.
   * @return the ModelAndView object which describes the page
   */
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView pageLoad() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.isAuthenticated()
        && !(authentication instanceof AnonymousAuthenticationToken)) {
      return new ModelAndView("redirect:/" + VIEW_NAME_WELCOME);
    } else {
      return new ModelAndView(VIEW_NAME_LOGIN);
    }
  }
}