package com.epam.rft.atsy.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for the position managing page.
 */
@Controller
@RequestMapping(path = "/secure/positions/manage")
public class ManagePositionsController {

  private static final String VIEW_NAME = "manage_positions";

  /**
   * Loads the page.
   * @return the ModelAndView object which describes the page
   */
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView loadPage() {
    return new ModelAndView(VIEW_NAME);
  }
}
