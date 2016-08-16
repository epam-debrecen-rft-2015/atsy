package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.exception.file.FileValidationException;
import com.epam.rft.atsy.web.mapper.FileValidationRuleMapper;
import com.epam.rft.atsy.web.model.FileBucket;
import com.epam.rft.atsy.web.validator.FileValidator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/secure/candidate/fileUpload")
public class FileUploadController {
  public static final String VALIDATION_SUCCESS_KEY = "validationSuccessKey";
  public static final String VALIDATION_ERROR_KEY = "validationErrorKey";
  private static final String VALIDATION_FILE_SUCCESS = "file.validation.success";
  private static final String FILE = "file";
  private static final String UPLOAD_LOCATION = "catalina.base";
  private static final String CV = "cv";
  public static String cvPath = StringUtils.EMPTY;

  @Autowired
  private FileValidator fileValidator;

  @Resource
  private FileValidationRuleMapper fileUploadValidationRuleMapper;


  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView fileUploadedGet(Model model) {
    FileBucket fileModel = new FileBucket();
    model.addAttribute("file", fileModel);
    return new ModelAndView("redirect:/secure/candidate");
  }

  @RequestMapping(method = RequestMethod.POST)
  public ModelAndView fileUploadedPost(@Validated FileBucket fileBucket) throws IOException {

    ModelAndView modelAndView = new ModelAndView("candidate_create");
    MultipartFile multipartFile = fileBucket.getFile();
    String fileName = multipartFile.getOriginalFilename();

    try {
      fileValidator.validate(multipartFile);
      File folder = new File(System.getProperty(UPLOAD_LOCATION) + File.separator + CV);
      if (!folder.exists()) {
        folder.mkdir();
      } else {
        FileUtils.cleanDirectory(folder);
      }

      String fileFullPath = folder + File.separator + fileName;
      FileCopyUtils.copy(fileBucket.getFile().getBytes(), new File(fileFullPath));
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


}