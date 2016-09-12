package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.web.messageresolution.MessageSourceRepresenationService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessageSourceController {

  public static final String TEXT_PLAIN_CHARSET_UTF_8 = "text/plain;charset=UTF-8";

  @Autowired
  private MessageSourceRepresenationService messageSourceRepresenationService;

  @RequestMapping(value = "/messages_{language}.properties", produces = TEXT_PLAIN_CHARSET_UTF_8)
  @ResponseBody
  public ResponseEntity<String> getMessages(@PathVariable("language") String language) {
    Validate.notNull(language);

    if (!this.messageSourceRepresenationService.isSupportedLocale(language)) {
      return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }

    String result = this.messageSourceRepresenationService.getLocalePropertiesAsString(language);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

}
