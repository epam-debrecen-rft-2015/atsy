package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.StatesService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Locale;

@Controller
public class NewApplicationPopupController {

    @Resource
    private StatesService statesService;

    @Resource
    private ApplicationsService applicationsService;

    private static final String VIEW_NAME = "new_application_popup";

    @RequestMapping(method = RequestMethod.GET, value = "/new_application_popup")
    public ModelAndView loadPage() {
        return new ModelAndView(VIEW_NAME);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/secure/new_application_popup")
    public String saveOrUpdate(@ModelAttribute StateDTO stateDTO, BindingResult result, Locale locale) {
        if (!result.hasErrors()) {
            stateDTO.setStateType("newstate");
            stateDTO.setStateIndex(0);
            ApplicationDTO applicationDTO = new ApplicationDTO();
            applicationDTO.setCreationDate(new Date());
            applicationDTO.setCandidateId(stateDTO.getCandidateId());
            applicationDTO.setPositionId(stateDTO.getPosition().getPositionId());
            Long applicationId = applicationsService.saveOrUpdate(applicationDTO);
            statesService.saveState(stateDTO, applicationId);
        }
        return "redirect:/secure/candidate/"+stateDTO.getCandidateId();
    }
}
