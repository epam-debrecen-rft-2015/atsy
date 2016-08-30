package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.exception.CandidateAlreadyHasCVFileException;
import com.epam.rft.atsy.service.exception.file.FileAlreadyExistsValidationException;
import com.epam.rft.atsy.service.exception.file.FileValidationException;
import com.epam.rft.atsy.web.util.CandidateCVFileHandler;
import com.epam.rft.atsy.web.mapper.FileValidationRuleMapper;
import com.epam.rft.atsy.web.validator.FileValidator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/secure/candidate/fileUpload")
public class FileUploadController {

  private static final String REDIRECT_URL_CANDIDATE_PAGE = "redirect:/secure/candidate";
  private static final String VALIDATION_SUCCESS_KEY = "validationSuccessKey";
  private static final String VALIDATION_ERROR_KEY = "validationErrorKey";
  private static final String VALIDATION_FILE_SUCCESS_MESSAGE_KEY = "file.validation.success";
  private static final String CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE_KEY = "candidate.already.has.cv.file";
  private static final String FILE = "file";

  @Value("${upload_location_cv}")
  private String uploadLocation;

  @Autowired
  private FileValidator fileValidator;

  @Autowired
  private FileValidationRuleMapper fileValidationRuleMapper;

  @Autowired
  private CandidateCVFileHandler candidateCVFileHandler;

  @Autowired
  private CandidateService candidateService;


  @RequestMapping(path = "/{candidateId}", method = RequestMethod.POST)
  public ModelAndView uploadFile(@PathVariable("candidateId") Long candidateId,
                                 @RequestParam(value = "file") final MultipartFile multipartFile,
                                 RedirectAttributes redirectAttributes)
      throws IOException {

    ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_CANDIDATE_PAGE + "/" + candidateId);
    String fileName = multipartFile.getOriginalFilename();

    try {
      CandidateDTO candidateDTO = candidateService.getCandidate(candidateId);
      String candidateCVFilename = candidateDTO.getCvFilename();
      if (StringUtils.isNotBlank(candidateCVFilename)) {
        throw new CandidateAlreadyHasCVFileException();
      }

      fileValidator.validate(multipartFile);
      File file = createFile(candidateDTO, fileName);
      FileCopyUtils.copy(multipartFile.getBytes(), file);

      redirectAttributes.addFlashAttribute(FILE, fileName);
      redirectAttributes.addFlashAttribute(VALIDATION_SUCCESS_KEY, VALIDATION_FILE_SUCCESS_MESSAGE_KEY);

      candidateDTO.setCvFilename(fileName);
      candidateService.saveOrUpdate(candidateDTO);

    } catch (CandidateAlreadyHasCVFileException e) {
      log.error(FileUploadController.class.getName(), e);
      redirectAttributes.addFlashAttribute(VALIDATION_ERROR_KEY, CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE_KEY);
    }
    catch (FileValidationException e) {
      log.error(FileUploadController.class.getName(), e);
      redirectAttributes.addFlashAttribute(VALIDATION_ERROR_KEY,
          fileValidationRuleMapper.getMessageKeyByException(e));
    }

    return modelAndView;
  }

  protected File createFile(CandidateDTO candidateDTO, String filename) throws FileValidationException, IOException {
    if (!candidateCVFileHandler.existCandidateFolderOnFolderLocation(uploadLocation, candidateDTO)) {
      candidateCVFileHandler.createCandidateFolderOnFolderLocation(uploadLocation, candidateDTO);
    }
    File file = candidateCVFileHandler
        .createCVFileFromFolderLocationAndCandidateDtoAndCVFilename(uploadLocation, candidateDTO, filename);
    if (file.exists()) {
      throw new FileAlreadyExistsValidationException();
    }
    return file;
  }

}