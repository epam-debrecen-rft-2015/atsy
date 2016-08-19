package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.FileUploadingProperties;
import com.epam.rft.atsy.web.model.file.FileStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/secure/candidate")
public class CandidateCreationController {

  private static final String CANDIDATE_OBJECT_KEY = "candidate";
  private static final String VIEW_NAME = "candidate_create";

  @Resource
  private CandidateService candidateService;


  @RequestMapping(method = RequestMethod.GET, path = "/{candidateId}")
  public ModelAndView loadCandidate(@PathVariable(value = "candidateId") Long candidateId,
                                    HttpSession session) {
    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
    modelAndView.addObject(CANDIDATE_OBJECT_KEY, candidateService.getCandidate(candidateId));

    String cvPath = session.getAttribute(FileUploadingProperties.SESSION_PARAM_CV_PATH) == null ?
        null : session.getAttribute(FileUploadingProperties.SESSION_PARAM_CV_PATH).toString();

    String candidateCvPath = candidateService.getCVPathByCandidateId(candidateId);
    if (candidateCvPath == null && cvPath == null) {
      modelAndView.addObject(FileStatus.CV_STATUS, FileStatus.FILE_NOT_EXISTS.getValue());
    } else if (candidateCvPath == null && cvPath != null) {
      modelAndView.addObject(FileStatus.CV_STATUS, FileStatus.FILE_IS_IN_PROGRESS.getValue());
    } else if (candidateCvPath != null) {
      modelAndView.addObject(FileStatus.CV_STATUS, FileStatus.FILE_ALREADY_EXISTS.getValue());
    }
    return modelAndView;
  }

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView loadCandidate() {
    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);

    modelAndView.addObject(CANDIDATE_OBJECT_KEY, new CandidateDTO());
    modelAndView.addObject(FileStatus.CV_STATUS, FileStatus.FILE_NOT_EXISTS.getValue());
    return modelAndView;
  }
}
