package com.epam.rft.atsy.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for the welcome page.
 */
@RestController
@RequestMapping(value = "/secure/welcome")
public class WelcomeController {
  private static final String VIEW_NAME = "candidates";

  /**
   * Loads the welcome page.
   * @return a ModelAndView object which contains the name of the page to be loaded
   */
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView loadPage() {
    return new ModelAndView(VIEW_NAME);
  }
}
