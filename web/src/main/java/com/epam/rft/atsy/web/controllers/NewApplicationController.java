package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for a popup window, which appears when we want to add a new application for a
 * candidate.
 */
@Controller
public class NewApplicationController {
  private static final String VIEW_NAME = "application";
  private static final String COMMON_INVALID_INPUT_MESSAGE_KEY = "common.invalid.input";
  private static final String COMMON_INVALID_INPUT_MESSAGE_NAME = "errorMessage";

  @Autowired
  private ApplicationsService applicationsService;

  @Autowired
  private MessageKeyResolver messageKeyResolver;

  /**
   * Loads the popup window with the input fields to create a new application.
   * @return a ModelAndView object which contains the name of the page to be loaded
   */
  @RequestMapping(method = RequestMethod.GET, value = "/new_application")
  public ModelAndView loadPage() {
    return new ModelAndView(VIEW_NAME);
  }

  /**
   * Saves or updates and existing application and state.
   * @param stateHistoryDTO contains the user input
   * @param result an object used to check if any error occurs
   * @return a string which represents a path to be redirected
   */
  @RequestMapping(method = RequestMethod.POST, value = "/secure/new_application")
  public String saveOrUpdate(@Valid @ModelAttribute StateHistoryDTO stateHistoryDTO,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
    if (!result.hasErrors()) {
      stateHistoryDTO.setStateDTO(new StateDTO(1L, "newstate"));

      ApplicationDTO applicationDTO = ApplicationDTO.builder()
          .creationDate(new Date())
          .candidateId(stateHistoryDTO.getCandidateId())
          .positionId(stateHistoryDTO.getPosition().getId())
          .channelId(stateHistoryDTO.getChannel().getId())
          .deleted(false)
          .build();

      applicationsService.saveApplication(applicationDTO, stateHistoryDTO);
      return "redirect:/secure/candidate/" + stateHistoryDTO.getCandidateId();
    }

    redirectAttributes.addFlashAttribute(COMMON_INVALID_INPUT_MESSAGE_NAME, this.messageKeyResolver.resolveMessageOrDefault(COMMON_INVALID_INPUT_MESSAGE_KEY));
    result.getFieldErrors().forEach(error ->
        redirectAttributes.addFlashAttribute(error.getField() + "ErrorMessage",
            messageKeyResolver.resolveMessageOrDefault(error.getDefaultMessage())));

    return "redirect:/secure/application?candidateId=" + stateHistoryDTO.getCandidateId();
  }
}
