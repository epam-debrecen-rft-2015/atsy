package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.model.file.CVStatusMonitor;
import com.epam.rft.atsy.web.model.file.FileStatus;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping(path = "/secure/candidate")
public class CandidateCreationController {

  private CVStatusMonitor cvStatusMonitor = CVStatusMonitor.getInstance();
  private static final String CANDIDATE_OBJECT_KEY = "candidate";
  private static final String VIEW_NAME = "candidate_create";

  @Resource
  private CandidateService candidateService;


  @RequestMapping(method = RequestMethod.GET, path = "/{candidateId}")
  public ModelAndView loadCandidate(@PathVariable(value = "candidateId") Long candidateId) {
    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
    modelAndView.addObject(CANDIDATE_OBJECT_KEY, candidateService.getCandidate(candidateId));

    String candidateCvPath = candidateService.getCVPathByCandidateId(candidateId);
    if (candidateCvPath == null && cvStatusMonitor.isActualCVPathEmpty()) {
      modelAndView.addObject(CVStatusMonitor.CV_STATUS, FileStatus.FILE_IS_NOT_EXIST.getValue());
    } else if (candidateCvPath == null && !cvStatusMonitor.isActualCVPathEmpty()) {
      modelAndView.addObject(CVStatusMonitor.CV_STATUS, FileStatus.FILE_IS_IN_PROGRESS.getValue());
    } else if (candidateCvPath != null && cvStatusMonitor.isActualCVPathEmpty()) {
      modelAndView.addObject(CVStatusMonitor.CV_STATUS, FileStatus.FILE_IS_ALREADY_EXIST.getValue());
    }
    return modelAndView;
  }

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView loadCandidate() {
    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);

    modelAndView.addObject(CANDIDATE_OBJECT_KEY, new CandidateDTO());
    modelAndView.addObject(CVStatusMonitor.CV_STATUS, FileStatus.FILE_IS_NOT_EXIST.getValue());
    return modelAndView;
  }
}
