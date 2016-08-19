package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.CandidateService;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
public class FileDownloaderController {
  private static final String CONTENT_DISPOSITION = "Content-Disposition";
  private static final String INLINE_FORMAT = "inline; filename=\"";

  @Resource
  private CandidateService candidateService;

  @RequestMapping(value = "/secure/candidate/fileDownload/{candidateId}", method = RequestMethod.GET)
  public void downloadFile(@PathVariable("candidateId") Long candidateId,
                           HttpServletResponse response) throws IOException {

    String path = candidateService.getCVPathByCandidateId(candidateId);
    File file = new File(path);

    String mimeType = URLConnection.guessContentTypeFromName(file.getName());
    if (mimeType == null) {
      mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }

    response.setContentType(mimeType);
    response.setHeader(CONTENT_DISPOSITION, String.format(INLINE_FORMAT + file.getName() + "\""));

    response.setContentLength((int) file.length());
    InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
    FileCopyUtils.copy(inputStream, response.getOutputStream());
  }
}
