package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.CandidateService;
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
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
public class FileDownloadController {

  @Resource
  private CandidateService candidateService;

  @RequestMapping(value = "/secure/candidate/download/{candidateId}", method = RequestMethod.GET)
  public void downloadFile(HttpServletResponse response,
                           @PathVariable("candidateId") Long candidateId) throws IOException {

    String path = candidateService.getCVPathByCandidateId(candidateId);
    File file = new File(path);

    if (!file.exists()) {
      String errorMessage = "Sorry. The file you are looking for does not exist";
      OutputStream outputStream = response.getOutputStream();
      outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
      outputStream.close();
      return;
    }

    String mimeType = URLConnection.guessContentTypeFromName(file.getName());
    if (mimeType == null) {
      mimeType = "application/octet-stream";
    }

    response.setContentType(mimeType);
    response.setHeader("Content-Disposition",
        String.format("inline; filename=\"" + file.getName() + "\""));

    response.setContentLength((int) file.length());
    InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
    FileCopyUtils.copy(inputStream, response.getOutputStream());
  }
}
