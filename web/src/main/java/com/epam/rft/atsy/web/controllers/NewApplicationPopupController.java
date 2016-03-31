package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.ApplicationService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;

@Controller
public class NewApplicationPopupController {

    @Resource
    private ApplicationService applicationService;

    private static final String VIEW_NAME = "new_application_popup";

    @RequestMapping(method = RequestMethod.GET, value = "/new_application_popup")
    public ModelAndView loadPage() {
        return new ModelAndView(VIEW_NAME);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/secure/new_application_popup")
    public String saveOrUpdate(@ModelAttribute StateDTO candidateDTO, BindingResult result, Locale locale) {
        if (!result.hasErrors()) {
            candidateDTO.setStateType("newstate");
            applicationService.saveState(candidateDTO);
        }
        return "redirect:/secure/candidate/"+candidateDTO.getCandidateId();
    }
}
