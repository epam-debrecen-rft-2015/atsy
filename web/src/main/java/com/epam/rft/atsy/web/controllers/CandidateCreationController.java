package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.model.file.FileStatus;
import com.epam.rft.atsy.web.util.FileUploadingUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.Paths;
import javax.annotation.Resource;

@Controller
@RequestMapping(path = "/secure/candidate")
public class CandidateCreationController {

  private static final String CANDIDATE_OBJECT_KEY = "candidate";
  private static final String VIEW_NAME = "candidate_create";

  @Resource
  private CandidateService candidateService;


  @RequestMapping(method = RequestMethod.GET, path = "/{candidateId}")
  public ModelAndView loadCandidate(@PathVariable(value = "candidateId") Long candidateId) {

    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
    modelAndView.addObject(CANDIDATE_OBJECT_KEY, candidateService.getCandidate(candidateId));

    String candidateCvPath = null;
    if (candidateId != null) {
      candidateCvPath = candidateService.getCVPathByCandidateId(candidateId);
    }

    if (candidateCvPath == null) {
      modelAndView.addObject(FileStatus.CV_STATUS_MODEL_ATTR_NAME, FileStatus.FILE_NOT_EXISTS);
    } else {
      modelAndView.addObject(FileStatus.CV_STATUS_MODEL_ATTR_NAME, FileStatus.FILE_ALREADY_EXISTS);
      String cvName = Paths.get(candidateCvPath).getFileName().toString();
      modelAndView.addObject(FileUploadingUtil.CV_NAME, cvName);
    }
    return modelAndView;
  }

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView loadCandidate() {
    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
    modelAndView.addObject(CANDIDATE_OBJECT_KEY, new CandidateDTO());
    return modelAndView;
  }
}
