package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.exception.CandidateAlreadyHasCVFileException;
import com.epam.rft.atsy.service.exception.file.FileValidationException;
import com.epam.rft.atsy.web.exceptionhandling.RestResponse;
import com.epam.rft.atsy.web.handler.FileHandler;
import com.epam.rft.atsy.web.handler.FolderHandler;
import com.epam.rft.atsy.web.mapper.RuleValidationExceptionMapper;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import com.epam.rft.atsy.web.validator.FileValidator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/secure/candidate/fileUpload")
public class FileUploadController {

  private static final String CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE_KEY = "candidate.already.has.cv.file";
  private static final String FILE = "file";
  private static final String STATUS_CODE = "statusCode";

  @Value("${upload_location_cv}")
  private String uploadLocation;

  @Autowired
  private FileValidator fileValidator;

  @Autowired
  private RuleValidationExceptionMapper ruleValidationExceptionMapper;

  @Autowired
  private FolderHandler folderHandler;

  @Autowired
  private FileHandler fileHandler;

  @Autowired
  private CandidateService candidateService;

  @Autowired
  private MessageKeyResolver messageKeyResolver;

  @RequestMapping(path = "/{candidateId}", method = RequestMethod.POST)
  public ResponseEntity uploadFile(@PathVariable("candidateId") Long candidateId,
                                   HttpServletRequest httpServletRequest)
      throws IOException, ServletException {

    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest;
    MultipartFile multipartFile = multipartHttpServletRequest.getFile(FILE);

    if (multipartFile == null) {
      return new ResponseEntity(Collections.singletonMap("id", candidateId), HttpStatus.OK);
    }

    String fileName = multipartFile.getOriginalFilename();

    try {
      CandidateDTO candidateDTO = candidateService.getCandidate(candidateId);
      String candidateCVFilename = candidateDTO.getCvFilename();
      if (StringUtils.isNotBlank(candidateCVFilename)) {
        throw new CandidateAlreadyHasCVFileException();
      }

      fileValidator.validate(multipartFile);
      File file = createFile(String.valueOf(candidateId), fileName);
      FileCopyUtils.copy(multipartFile.getBytes(), file);

      candidateDTO.setCvFilename(fileName);
      candidateId = candidateService.saveOrUpdate(candidateDTO);
      return new ResponseEntity<>(Collections.singletonMap("id", candidateId), HttpStatus.OK);
    } catch (CandidateAlreadyHasCVFileException e) {
      log.error(FileUploadController.class.getName(), e);
      String
          errorMessageKey =
          this.messageKeyResolver.resolveMessageOrDefault(CANDIDATE_ALREADY_HAS_CV_FILE_MESSAGE_KEY);
      return new ResponseEntity<>(new RestResponse(errorMessageKey), HttpStatus.BAD_REQUEST);
    } catch (FileValidationException e) {
      log.error(FileUploadController.class.getName(), e);
      String errorMessageKey =
          this.messageKeyResolver.resolveMessageOrDefault(ruleValidationExceptionMapper.getMessageKeyByException(e));
      return new ResponseEntity(new RestResponse(errorMessageKey), HttpStatus.BAD_REQUEST);
    }
  }

  @RequestMapping(path = "/validate", method = RequestMethod.POST)
  public ResponseEntity validateFile(HttpServletRequest httpServletRequest) {

    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest;
    MultipartFile multipartFile = multipartHttpServletRequest.getFile(FILE);

    if (multipartFile == null) {
      return new ResponseEntity(Collections.singletonMap(STATUS_CODE, Long.valueOf(HttpStatus.OK.value())),
          HttpStatus.OK);
    }
    try {
      fileValidator.validate(multipartFile);
    } catch (FileValidationException e) {
      return new ResponseEntity(Collections.singletonMap(STATUS_CODE, Long.valueOf(HttpStatus.BAD_REQUEST.value())),
          HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity(Collections.singletonMap(STATUS_CODE, Long.valueOf(HttpStatus.OK.value())),
        HttpStatus.OK);

  }

  protected File createFile(String folderName, String filename) throws IOException {
    if (!folderHandler.existFolderNameInParentDirectoryPath(String.valueOf(folderName), uploadLocation)) {
      folderHandler.createFolderInParentDirectoryPath(String.valueOf(folderName), uploadLocation);
    }
    return fileHandler.getFileByParentDirectoryPathAndFolderNameAndFilename(uploadLocation, folderName, filename);
  }

}