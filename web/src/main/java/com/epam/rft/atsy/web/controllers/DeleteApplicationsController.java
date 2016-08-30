package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.CandidateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

//TODO: Add java doc. Add test.
@Controller
@RequestMapping(value = "/secure/deleteApplications")
public class DeleteApplicationsController {

  private static final String VIEW_NAME = "candidates";

  @Resource
  private CandidateService candidateService;

  //TODO: Add java doc.
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView deleteApplications(@RequestParam Long candidateId) {
    candidateService.deletePositionsByCandidate(candidateService.getCandidate(candidateId));
    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
    return modelAndView;
  }

}
