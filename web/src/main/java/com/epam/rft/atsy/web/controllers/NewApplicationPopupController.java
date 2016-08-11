package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Controller for a popup window, which appears when we want to add a new application for a
 * candidate.
 */
@Controller
public class NewApplicationPopupController {
  private static final String VIEW_NAME = "new_application_popup";

  @Resource
  private ApplicationsService applicationsService;

  /**
   * Loads the popup window with the input fields to create a new application.
   * @return a ModelAndView object which describes the page
   */
  @RequestMapping(method = RequestMethod.GET, value = "/new_application_popup")
  public ModelAndView loadPage() {
    return new ModelAndView(VIEW_NAME);
  }

  /**
   * Saves or updates and existing application and state.
   * @param stateHistoryDTO contains the user input
   * @param result a BindingResult object used to check if the input is valid
   * @return a string which represents a path to be redirected
   */
  @RequestMapping(method = RequestMethod.POST, value = "/secure/new_application_popup")
  public String saveOrUpdate(@Valid @ModelAttribute StateHistoryDTO stateHistoryDTO,
                             BindingResult result) {
    if (!result.hasErrors()) {
      stateHistoryDTO.setStateDTO(new StateDTO(1L, "newstate"));

      ApplicationDTO applicationDTO = ApplicationDTO.builder()
          .creationDate(new Date())
          .candidateId(stateHistoryDTO.getCandidateId())
          .positionId(stateHistoryDTO.getPosition().getId())
          .channelId(stateHistoryDTO.getChannel().getId())
          .build();

      applicationsService.saveApplication(applicationDTO, stateHistoryDTO);
    }
    return "redirect:/secure/candidate/" + stateHistoryDTO.getCandidateId();
  }
}