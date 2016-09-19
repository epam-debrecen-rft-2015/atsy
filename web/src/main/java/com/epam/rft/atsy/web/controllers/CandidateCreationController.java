package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * This controller is used to give information to the page that handles the data of the candidates.
 * It either gives back an empty DTO object to create a new candidate or a filled DTO to show or
 * modify the data of a given candidate.
 */
@Controller
@RequestMapping(path = "/secure/candidate")
public class CandidateCreationController {

  private static final String CANDIDATE_OBJECT_KEY = "candidate";
  private static final String VIEW_NAME = "candidate_create";

  @Autowired
  private CandidateService candidateService;

  /**
   * Loads the candidate with the given ID and gives back its information.
   * @param candidateId the identifier of the candidate whose data should be given back
   * @return a ModelAndView object which contains the all candidate information and the name of the
   * page that handles the candidate data
   */
  @RequestMapping(method = RequestMethod.GET, path = "/{candidateId}")
  public ModelAndView loadCandidate(@PathVariable(value = "candidateId") Long candidateId) {
    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
    modelAndView.addObject(CANDIDATE_OBJECT_KEY, candidateService.getCandidate(candidateId));
    return modelAndView;
  }

  /**
   * Creates an empty DTO object that can be filled with the data of a new candidate.
   * @return a ModelAndView object which contains an empty DTO for the new candidate and the name of
   * the page that handles the candidate data
   */
  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView loadCandidate() {
    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
    modelAndView.addObject(CANDIDATE_OBJECT_KEY, new CandidateDTO());
    return modelAndView;
  }
}
