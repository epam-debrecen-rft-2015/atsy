package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.web.messageresolution.MessageSourceRepresentationService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.epam.rft.atsy.web.controllers.MessageSourceController.TEXT_PLAIN_CHARSET_UTF_8;

/**
 * Controller that loads the actual message source.
 */
@Controller
@RequestMapping(produces = TEXT_PLAIN_CHARSET_UTF_8)
public class MessageSourceController {

  public static final String TEXT_PLAIN_CHARSET_UTF_8 = "text/plain;charset=UTF-8";

  @Autowired
  private MessageSourceRepresentationService messageSourceRepresenationService;

  /**
   * Returns {@code ResponseEntity} that stores the {@code HttpStatus} and
   * the filtered key-value pairs from the property file if the property file exists.
   *
   * @param language that decides which property file will be read
   * @return {@code ResponseEntity} that stores the {@code HttpStatus} and
   * the filtered key-value pairs from the property file if the property file exists
   * @throws IllegalArgumentException If the {@code language} is null
   */
  @RequestMapping(path = "/messages_{language}.properties")
  @ResponseBody
  public ResponseEntity<String> getMessages(@PathVariable("language") String language) {
    Assert.notNull(language);

    if (!this.messageSourceRepresenationService.isSupportedLocale(language)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    String result = this.messageSourceRepresenationService.getLocalePropertiesAsString(language);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  /**
   * This method is necessary because the jquery-i18n-plugin needs a default messages.properties file.
   * With this method we can avoid the 400 or 404 HTTP statuses.
   * @return an empty {@code String}
   */
  @RequestMapping(path = "/messages.properties")
  @ResponseBody
  public ResponseEntity<String> getDefaultMessages() {
    return new ResponseEntity<>(StringUtils.EMPTY, HttpStatus.OK);
  }
}
