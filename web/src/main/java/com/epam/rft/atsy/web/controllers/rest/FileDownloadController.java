package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.exceptionhandling.RestResponse;
import com.epam.rft.atsy.web.handler.FileHandler;
import com.epam.rft.atsy.web.handler.FolderHandler;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@RestController
@RequestMapping(path = "/secure/fileDownload")
public class FileDownloadController {
  private static final String CONTENT_DISPOSITION = "Content-Disposition";
  private static final String INLINE_FORMAT = "inline; filename=\"";
  private static final String FILE_ERROR_MESSAGE_KEY = "file.not.exists.anymore";

  @Value("${upload_location_cv}")
  private String uploadLocation;

  @Autowired
  private CandidateService candidateService;

  @Autowired
  private FolderHandler folderHandler;

  @Autowired
  private FileHandler fileHandler;

  @Autowired
  private MessageKeyResolver messageKeyResolver;

  @RequestMapping(path = "/{candidateId}", method = RequestMethod.GET)
  public ResponseEntity downloadFile(@PathVariable("candidateId") Long candidateId) throws Exception {

    CandidateDTO candidateDTO = candidateService.getCandidate(candidateId);
    String candidateCVFilename = candidateDTO.getCvFilename();
    if (StringUtils.isNotBlank(candidateCVFilename)) {

      try {
        File file = fileHandler
            .getFileByParentDirectoryPathAndFolderNameAndFilename(uploadLocation,
                String.valueOf(candidateId), candidateCVFilename);
        if (!file.exists()) {
          throw new IOException();
        }
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(CONTENT_DISPOSITION, String.format(INLINE_FORMAT + candidateCVFilename));
        responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        responseHeaders.setContentLength(file.length());

        return new ResponseEntity<>(inputStreamResource, responseHeaders, HttpStatus.OK);
      } catch (IOException e) {
        String errorMessage = this.messageKeyResolver.resolveMessageOrDefault(FILE_ERROR_MESSAGE_KEY);
        return new ResponseEntity<>(new RestResponse(errorMessage), HttpStatus.BAD_REQUEST);
      }
    } else {
      String errorMessage = this.messageKeyResolver.resolveMessageOrDefault(FILE_ERROR_MESSAGE_KEY);
      return new ResponseEntity<>(new RestResponse(errorMessage), HttpStatus.BAD_REQUEST);
    }
  }

  @RequestMapping(path = "/validate/{candidateId}", method = RequestMethod.GET)
  public ResponseEntity validateFile(@PathVariable("candidateId") Long candidateId) throws Exception {
    CandidateDTO candidateDTO = candidateService.getCandidate(candidateId);
    String candidateCVFilename = candidateDTO.getCvFilename();
    if (StringUtils.isNotBlank(candidateCVFilename)) {

      try {
        File file = fileHandler
            .getFileByParentDirectoryPathAndFolderNameAndFilename(uploadLocation,
                String.valueOf(candidateId), candidateCVFilename);
        if (!file.exists()) {
          throw new IOException();
        }
        return new ResponseEntity(Collections.singletonMap("id", candidateId), HttpStatus.OK);
      } catch (IOException e) {
        String errorMessage = this.messageKeyResolver.resolveMessageOrDefault(FILE_ERROR_MESSAGE_KEY);
        return new ResponseEntity<>(new RestResponse(errorMessage), HttpStatus.BAD_REQUEST);
      }
    } else {
      String errorMessage = this.messageKeyResolver.resolveMessageOrDefault(FILE_ERROR_MESSAGE_KEY);
      return new ResponseEntity<>(new RestResponse(errorMessage), HttpStatus.BAD_REQUEST);
    }
  }
}