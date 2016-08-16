package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.model.FileStatus;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping(path = "/secure")
public class CandidateCreationController {

  public static final String CANDIDATE_OBJECT_KEY = "candidate";
  private static final String VIEW_NAME = "candidate_create";
  private static final String FILE_STATUS = "cv_file_status_monitor";


  @Resource
  private CandidateService candidateService;

  @RequestMapping(method = RequestMethod.GET, path = "/candidate/{candidateId}")
  public ModelAndView loadCandidate(@PathVariable(value = "candidateId") Long candidateId) {
    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
    modelAndView.addObject(CANDIDATE_OBJECT_KEY, candidateService.getCandidate(candidateId));

    String cvPath = candidateService.getCVPathByCandidateId(candidateId);
    if (cvPath == null || !FileUploadController.cvPath.equals(StringUtils.EMPTY)) {
      modelAndView.addObject(FILE_STATUS, FileStatus.FILE_IS_NOT_EXIST.getStatus());
    } else {
      modelAndView.addObject(FILE_STATUS, FileStatus.FILE_IS_ALREADY_EXIST.getStatus());
    }


    return modelAndView;
  }

  @RequestMapping(method = RequestMethod.GET, path = "/candidate")
  public ModelAndView loadCandidate() {
    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);

    modelAndView.addObject(CANDIDATE_OBJECT_KEY, new CandidateDTO());
    modelAndView.addObject(FILE_STATUS, FileStatus.FILE_IS_NOT_EXIST.getStatus());
    return modelAndView;
  }
}
