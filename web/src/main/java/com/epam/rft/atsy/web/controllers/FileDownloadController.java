package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.util.CandidateCVFileHandlerUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class FileDownloadController {
  private static final String CONTENT_DISPOSITION = "Content-Disposition";
  private static final String INLINE_FORMAT = "inline; filename=\"";
  private static final String FILE_ERROR_MESSAGE = "fileErrorMessage";
  private static final String FILE_ERROR_MESSAGE_KEY = "file.not.exists.anymore";
  private static final String REDIRECT_CANDIDATE_PAGE = "redirect:/secure/candidate";

  @Value("${upload_location_cv}")
  private String uploadLocation;

  @Autowired
  private CandidateService candidateService;

  @ResponseBody
  @RequestMapping(value = "/secure/candidate/fileDownload/{candidateId}", method = RequestMethod.GET)
  public Object downloadFile(@PathVariable("candidateId") Long candidateId, RedirectAttributes redirectAttrs)
      throws Exception {

    CandidateDTO candidateDTO = candidateService.getCandidate(candidateId);
    String candidateCVFilename = candidateDTO.getCvFilename();
    if (candidateCVFilename != null && StringUtils.isNotEmpty(candidateCVFilename)) {

      try {
        File file = CandidateCVFileHandlerUtil.createCVFileFromFolderLocationAndCandidateDtoAndCVFilename(uploadLocation, candidateDTO, candidateCVFilename);
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(CONTENT_DISPOSITION, String.format(INLINE_FORMAT + candidateCVFilename));
        responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        responseHeaders.setContentLength(file.length());

        return new ResponseEntity<>(inputStreamResource, responseHeaders, HttpStatus.OK);
      } catch (IOException e) {
        redirectAttrs.addFlashAttribute(FILE_ERROR_MESSAGE, FILE_ERROR_MESSAGE_KEY);
      }
    } else {
      redirectAttrs.addFlashAttribute(FILE_ERROR_MESSAGE, FILE_ERROR_MESSAGE_KEY);
    }
    return new ModelAndView(REDIRECT_CANDIDATE_PAGE + "/" + candidateId);
  }
}
