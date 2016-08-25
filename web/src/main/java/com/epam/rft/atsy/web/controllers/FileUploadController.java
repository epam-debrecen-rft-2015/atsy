package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.exception.file.FileAlreadyExistsValidationException;
import com.epam.rft.atsy.service.exception.file.FileValidationException;
import com.epam.rft.atsy.web.mapper.FileValidationRuleMapper;
import com.epam.rft.atsy.web.model.file.FileBucket;
import com.epam.rft.atsy.web.validator.FileValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
  private static final String VALIDATION_FILE_SUCCESS = "file.validation.success";
  private static final String FILE = "file";

  @Value("${upload_location_cv}")
  private String uploadLocation;

  @Autowired
  private FileValidator fileValidator;

  @Autowired
  private FileValidationRuleMapper fileUploadValidationRuleMapper;

  @Autowired
  private CandidateService candidateService;


  @RequestMapping(path = "/{candidateId}", method = RequestMethod.POST)
  public ModelAndView upload(@Validated FileBucket fileBucket,
                             @PathVariable("candidateId") Long candidateId,
                             RedirectAttributes redirectAttributes)
      throws IOException {

    ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_CANDIDATE_PAGE + "/" + candidateId);
    MultipartFile multipartFile = fileBucket.getFile();
    String fileName = multipartFile.getOriginalFilename();

    try {
      fileValidator.validate(multipartFile);
      File file = createFile(fileName);

      FileCopyUtils.copy(fileBucket.getFile().getBytes(), file);
      redirectAttributes.addFlashAttribute(FILE, fileName);
      redirectAttributes.addFlashAttribute(VALIDATION_SUCCESS_KEY, VALIDATION_FILE_SUCCESS);

      CandidateDTO candidateDTO = candidateService.getCandidate(candidateId);
      candidateDTO.setCvFilename(fileName);
      candidateService.saveOrUpdate(candidateDTO);

    } catch (FileValidationException e) {
      log.error(FileUploadController.class.getName(), e);
      redirectAttributes.addFlashAttribute(VALIDATION_ERROR_KEY,
          fileUploadValidationRuleMapper.getMessageKeyByException(e));
    }

    return modelAndView;
  }

  private File createFile(String fileName) throws FileAlreadyExistsValidationException {
    String fileNameWithFullPath = uploadLocation + File.separator + fileName;
    File file = new File(fileNameWithFullPath);
    if (file.exists()) {
      throw new FileAlreadyExistsValidationException();
    }
    return file;
  }

}