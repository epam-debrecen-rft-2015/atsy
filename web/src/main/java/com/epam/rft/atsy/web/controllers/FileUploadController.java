package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.exception.file.FileIsAlreadyExistValidationException;
import com.epam.rft.atsy.service.exception.file.FileValidationException;
import com.epam.rft.atsy.web.mapper.FileValidationRuleMapper;
import com.epam.rft.atsy.web.model.FileBucket;
import com.epam.rft.atsy.web.validator.FileValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/secure/candidate")
public class FileUploadController {
  private static final String VIEW_NAME = "candidate_create";
  private static final String VALIDATION_SUCCESS_KEY = "validationSuccessKey";
  private static final String VALIDATION_ERROR_KEY = "validationErrorKey";
  private static final String VALIDATION_FILE_SUCCESS = "file.validation.success";
  private static final String FILE = "file";
  private static final String UPLOAD_LOCATION = "catalina.base";
  private static final String CV = "cv";
  public static String cvPath = StringUtils.EMPTY;

  @Autowired
  private FileValidator fileValidator;

  @Resource
  private FileValidationRuleMapper fileUploadValidationRuleMapper;


  @RequestMapping(path = "/fileUpload", method = RequestMethod.POST)
  public ModelAndView uploadFile(@Validated FileBucket fileBucket) throws IOException {

    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
    MultipartFile multipartFile = fileBucket.getFile();
    String fileName = multipartFile.getOriginalFilename();

    try {
      fileValidator.validate(multipartFile);
      File folder = new File(System.getProperty(UPLOAD_LOCATION) + File.separator + CV);
      if (!folder.exists()) {
        folder.mkdir();
      }

      String fileFullPath = folder + File.separator + fileName;
      File file = new File(fileFullPath);
      if (file.exists()) {
        throw new FileIsAlreadyExistValidationException();
      }

      FileCopyUtils.copy(fileBucket.getFile().getBytes(), file);
      cvPath = fileFullPath;
      modelAndView.addObject(FILE, fileName);
      modelAndView.addObject(VALIDATION_SUCCESS_KEY, VALIDATION_FILE_SUCCESS);

    } catch (FileValidationException e) {
      log.error(FileUploadController.class.getName(), e);
      cvPath = StringUtils.EMPTY;
      modelAndView.addObject(VALIDATION_ERROR_KEY,
          fileUploadValidationRuleMapper.getMessageKeyByException(e));
    }

    return modelAndView;
  }

  @RequestMapping(path = "/fileUpload/{candidateId}", method = RequestMethod.POST)
  public ModelAndView uploadFileByCandidateId(@Validated FileBucket fileBucket,
                                              @PathVariable("candidateId") Long candidateId,
                                              RedirectAttributes redirectAttributes)
      throws IOException {

    ModelAndView modelAndView = new ModelAndView("redirect:/secure/candidate/" + candidateId);
    MultipartFile multipartFile = fileBucket.getFile();
    String fileName = multipartFile.getOriginalFilename();

    try {
      fileValidator.validate(multipartFile);
      File folder = new File(System.getProperty(UPLOAD_LOCATION) + File.separator + CV);
      if (!folder.exists()) {
        folder.mkdir();
      }

      String fileFullPath = folder + File.separator + fileName;
      File file = new File(fileFullPath);
      if (file.exists()) {
        throw new FileIsAlreadyExistValidationException();
      }

      FileCopyUtils.copy(fileBucket.getFile().getBytes(), file);
      cvPath = fileFullPath;
      redirectAttributes.addFlashAttribute(FILE, fileName);
      redirectAttributes.addFlashAttribute(VALIDATION_SUCCESS_KEY, VALIDATION_FILE_SUCCESS);

    } catch (FileValidationException e) {
      log.error(FileUploadController.class.getName(), e);
      cvPath = StringUtils.EMPTY;
      redirectAttributes.addFlashAttribute(VALIDATION_ERROR_KEY, fileUploadValidationRuleMapper.getMessageKeyByException(e));
    }

    return modelAndView;
  }


}