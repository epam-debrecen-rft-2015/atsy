package com.epam.rft.atsy.web.controllers;

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
  private static final String VIEW_NAME = "login";

  /**
   * Loads the page.
   * @return the ModelAndView object which describes the page
   */
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView pageLoad() {
    return new ModelAndView(VIEW_NAME);
  }

}
