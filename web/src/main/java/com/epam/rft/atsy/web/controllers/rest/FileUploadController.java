package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import com.epam.rft.atsy.service.exception.file.CandidateAlreadyHasCVFileException;
import com.epam.rft.atsy.service.exception.file.FileUploadNotAllowedException;
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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/secure/fileUpload")
public class FileUploadController {

  private static final String FILE_PARAMETER_NAME = "file";

  private static final String STATE_NAME_CV = "cv";

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
  private StatesHistoryService statesHistoryService;

  @Autowired
  private MessageKeyResolver messageKeyResolver;

  @RequestMapping(path = "/candidate/{candidateId}", method = RequestMethod.POST)
  public ResponseEntity uploadFileForCandidate(@PathVariable("candidateId") Long candidateId,
                                               HttpServletRequest httpServletRequest)
      throws IOException, ServletException {

    return uploadFile(candidateId, candidateId, httpServletRequest);
  }

  @RequestMapping(path = "/stateHistory/{applicationId}", method = RequestMethod.POST)
  public ResponseEntity uploadFileForState(@PathVariable("applicationId") Long applicationId,
                                           HttpServletRequest httpServletRequest) throws IOException, ServletException {

    try {
      List<StateHistoryDTO> stateHistoryViewRepresentations =
              this.statesHistoryService.getStateHistoriesByApplicationId(applicationId);
      String currentStateName = stateHistoryViewRepresentations.get(0).getStateDTO().getName();
      if (!currentStateName.equals(STATE_NAME_CV)) {
        throw new FileUploadNotAllowedException();
      }

      Long candidateId = this.candidateService.getCandidateByApplicationID(applicationId).getId();
      return uploadFile(candidateId, applicationId, httpServletRequest);

    } catch (FileValidationException e) {
      log.error(FileUploadController.class.getName(), e);
      return getResponseEntityWithFileErrorMessageByException(e);
    }
  }

  @RequestMapping(path = "/validate", method = RequestMethod.POST)
  public ResponseEntity validateFile(HttpServletRequest httpServletRequest) {

    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest;
    MultipartFile multipartFile = multipartHttpServletRequest.getFile(FILE_PARAMETER_NAME);

    if (multipartFile == null) {
      return new ResponseEntity(RestResponse.NO_ERROR, HttpStatus.OK);
    }
    try {
      fileValidator.validate(multipartFile);
    } catch (FileValidationException e) {
      log.error(FileUploadController.class.getName(), e);
      return getResponseEntityWithFileErrorMessageByException(e);
    }
    return new ResponseEntity(RestResponse.NO_ERROR, HttpStatus.OK);
  }

  private ResponseEntity uploadFile(Long candidateId, Long returnedId, HttpServletRequest httpServletRequest)
      throws IOException {
    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest;
    MultipartFile multipartFile = multipartHttpServletRequest.getFile(FILE_PARAMETER_NAME);

    if (multipartFile == null) {
      return new ResponseEntity(Collections.singletonMap("id", returnedId), HttpStatus.OK);
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
      candidateService.saveOrUpdate(candidateDTO);
      return new ResponseEntity<>(Collections.singletonMap("id", returnedId), HttpStatus.OK);
    } catch (FileValidationException e) {
      log.error(FileUploadController.class.getName(), e);
      return getResponseEntityWithFileErrorMessageByException(e);
    }
  }

  private File createFile(String folderName, String filename) throws IOException {
    if (!folderHandler.existFolderNameInParentDirectoryPath(String.valueOf(folderName), uploadLocation)) {
      folderHandler.createFolderInParentDirectoryPath(String.valueOf(folderName), uploadLocation);
    }
    return fileHandler.getFileByParentDirectoryPathAndFolderNameAndFilename(uploadLocation, folderName, filename);
  }

  private ResponseEntity getResponseEntityWithFileErrorMessageByException(Exception e) {
    String
        errorMessage =
        this.messageKeyResolver.resolveMessageOrDefault(ruleValidationExceptionMapper.getMessageKeyByException(e));
    return new ResponseEntity<>(new RestResponse(errorMessage), HttpStatus.BAD_REQUEST);
  }

}