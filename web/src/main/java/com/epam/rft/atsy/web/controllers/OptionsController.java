package com.epam.rft.atsy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for the settings page.
 */
@Controller
@RequestMapping(path = "/secure/settings")
public class OptionsController {
  private static final String VIEW_NAME = "settings";


  /**
   * Loads the settings page.
   * @return a ModelAndView object which contains the name of the page to be loaded
   */
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView loadPage() {
    return new ModelAndView(VIEW_NAME);
  }

}
