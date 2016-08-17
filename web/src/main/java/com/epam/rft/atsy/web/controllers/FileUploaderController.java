package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.exception.file.FileIsAlreadyExistValidationException;
import com.epam.rft.atsy.service.exception.file.FileValidationException;
import com.epam.rft.atsy.web.FileUploadingProperties;
import com.epam.rft.atsy.web.mapper.FileValidationRuleMapper;
import com.epam.rft.atsy.web.model.file.FileBucket;
import com.epam.rft.atsy.web.model.file.FileStatus;
import com.epam.rft.atsy.web.validator.FileValidator;
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
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/secure/candidate/fileUpload")
public class FileUploaderController {

  private static final String VIEW_NAME = "candidate_create";
  private static final String REDIRECT_URL_CANDIDATE_PAGE = "redirect:/secure/candidate";
  private static final String VALIDATION_SUCCESS_KEY = "validationSuccessKey";
  private static final String VALIDATION_ERROR_KEY = "validationErrorKey";
  private static final String VALIDATION_FILE_SUCCESS = "file.validation.success";
  private static final String FILE = "file";

  @Autowired
  private FileValidator fileValidator;

  @Resource
  private FileValidationRuleMapper fileUploadValidationRuleMapper;

  @PostConstruct
  public void init() {
    if (!FileUploadingProperties.FOLDER_CV.exists()) {
      FileUploadingProperties.FOLDER_CV.mkdir();
    }
  }

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView uploadFile() {
    return new ModelAndView(REDIRECT_URL_CANDIDATE_PAGE);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ModelAndView uploadFile(@Validated FileBucket fileBucket, HttpSession session)
      throws IOException {

    ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
    MultipartFile multipartFile = fileBucket.getFile();
    String fileName = multipartFile.getOriginalFilename();

    try {
      fileValidator.validate(multipartFile);
      File file = createFile(fileName);

      FileCopyUtils.copy(fileBucket.getFile().getBytes(), file);
      session.setAttribute(FileUploadingProperties.SESSION_PARAM_CV_PATH, file.getPath());
      modelAndView.addObject(FILE, fileName);
      modelAndView.addObject(VALIDATION_SUCCESS_KEY, VALIDATION_FILE_SUCCESS);

    } catch (FileValidationException e) {
      log.error(FileUploaderController.class.getName(), e);
      session.removeAttribute(FileUploadingProperties.SESSION_PARAM_CV_PATH);
      modelAndView.addObject(FileStatus.CV_STATUS, FileStatus.FILE_IS_NOT_EXIST.getValue());
      modelAndView.addObject(VALIDATION_ERROR_KEY,
          fileUploadValidationRuleMapper.getMessageKeyByException(e));
    }

    return modelAndView;
  }

  @RequestMapping(path = "/{candidateId}", method = RequestMethod.POST)
  public ModelAndView uploadFileByCandidateId(@Validated FileBucket fileBucket,
                                              @PathVariable("candidateId") Long candidateId,
                                              RedirectAttributes redirectAttributes,
                                              HttpSession session)
      throws IOException {

    ModelAndView modelAndView = new ModelAndView(REDIRECT_URL_CANDIDATE_PAGE + "/" + candidateId);
    MultipartFile multipartFile = fileBucket.getFile();
    String fileName = multipartFile.getOriginalFilename();

    try {
      fileValidator.validate(multipartFile);
      File file = createFile(fileName);

      FileCopyUtils.copy(fileBucket.getFile().getBytes(), file);
      session.setAttribute(FileUploadingProperties.SESSION_PARAM_CV_PATH, file.getPath());
      redirectAttributes.addFlashAttribute(FILE, fileName);
      redirectAttributes.addFlashAttribute(VALIDATION_SUCCESS_KEY, VALIDATION_FILE_SUCCESS);
      redirectAttributes.addFlashAttribute(FileStatus.CV_STATUS,
          FileStatus.FILE_IS_ALREADY_EXIST.getValue());

    } catch (FileValidationException e) {
      log.error(FileUploaderController.class.getName(), e);
      session.removeAttribute(FileUploadingProperties.SESSION_PARAM_CV_PATH);
      redirectAttributes
          .addFlashAttribute(FileStatus.CV_STATUS, FileStatus.FILE_IS_NOT_EXIST.getValue());
      redirectAttributes.addFlashAttribute(VALIDATION_ERROR_KEY,
              fileUploadValidationRuleMapper.getMessageKeyByException(e));
    }

    return modelAndView;
  }

  private File createFile(String fileName) throws FileIsAlreadyExistValidationException {
    String fileNameWithFullPath = FileUploadingProperties.FOLDER_CV + File.separator + fileName;
    File file = new File(fileNameWithFullPath);
    if (file.exists()) {
      throw new FileIsAlreadyExistValidationException();
    }
    return file;
  }

}