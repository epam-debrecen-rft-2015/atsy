package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.exception.CandidateNotFoundException;
import com.epam.rft.atsy.service.exception.ChannelNotFoundException;
import com.epam.rft.atsy.service.exception.ObjectNotFoundException;
import com.epam.rft.atsy.service.exception.PositionNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
public class NewApplicationPopupController {
  private static final String VIEW_NAME = "new_application_popup";

  @Resource
  private CandidateService candidateService;
  @Resource
  private PositionService positionService;
  @Resource
  private ChannelService channelService;
  @Resource
  private ApplicationsService applicationsService;

  @RequestMapping(method = RequestMethod.GET, value = "/new_application_popup")
  public ModelAndView loadPage() {
    return new ModelAndView(VIEW_NAME);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/secure/new_application_popup")
  public String saveOrUpdate(@Valid @ModelAttribute StateDTO stateDTO, BindingResult result)
      throws ObjectNotFoundException {
    if (result.hasErrors()) {
      throw new IllegalArgumentException();
    }
    validateIdsBeforeExecuteSaveOrUpdate(stateDTO.getCandidateId(), stateDTO.getChannel().getId(),
        stateDTO.getPosition().getId());
    stateDTO.setStateType("newstate");
    stateDTO.setStateIndex(0);

    ApplicationDTO applicationDTO = ApplicationDTO.builder()
        .creationDate(new Date())
        .candidateId(stateDTO.getCandidateId())
        .positionId(stateDTO.getPosition().getId())
        .channelId(stateDTO.getChannel().getId())
        .build();

    applicationsService.saveApplicaton(applicationDTO, stateDTO);
    return "redirect:/secure/candidate/" + stateDTO.getCandidateId();

  }

  private void validateIdsBeforeExecuteSaveOrUpdate(Long candidateId, Long channelId,
                                                    Long positionId)
      throws ObjectNotFoundException {
    Assert.notNull(candidateId);
    Assert.notNull(channelId);
    Assert.notNull(positionId);

    if (candidateService.getCandidate(candidateId) == null) {
      throw new CandidateNotFoundException("Candidate is not found with this id: " + candidateId);
    }
    if (channelService.getChannelById(channelId) == null) {
      throw new ChannelNotFoundException("Channel is not found with this id: " + channelId);
    }
    if (positionService.getPositionById(positionId) == null) {
      throw new PositionNotFoundException("Position is not found with this id: " + positionId);
    }
  }
}
