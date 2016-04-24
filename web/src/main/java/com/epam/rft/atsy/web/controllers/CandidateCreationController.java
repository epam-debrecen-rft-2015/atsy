package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping(path = "/secure")
public class CandidateCreationController {

    private static final String VIEW_NAME = "candidate_create";
    public static final String CANDIDATE_OBJECT_KEY = "candidate";
    @Resource
    private CandidateService candidateService;

    @RequestMapping(method = RequestMethod.GET, path = "/candidate/{candidateId}")
    public ModelAndView loadCandidate(@PathVariable(value = "candidateId") Long candidateId) {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);

        modelAndView.addObject(CANDIDATE_OBJECT_KEY, candidateService.getCandidate(candidateId));

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/candidate")
    public ModelAndView loadCandidate() {
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);

        modelAndView.addObject(CANDIDATE_OBJECT_KEY, new CandidateDTO());

        return modelAndView;
    }
}
