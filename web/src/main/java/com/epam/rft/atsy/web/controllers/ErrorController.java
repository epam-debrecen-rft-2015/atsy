package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

/**
 * Controller for the error page.
 */
@Controller
@RequestMapping(value = "/error")
public class ErrorController {
  private static final String VIEW_NAME_ERROR = "error";

  private static final String TECHNICAL_ERROR_MESSAGE_KEY = "technical.error.message";

  @Autowired
  private MessageKeyResolver messageKeyResolver;

  private final Map<Integer, String>
      errorMessageKeyMap =
      ImmutableMap.<Integer, String>builder()
          .put(HttpServletResponse.SC_BAD_REQUEST, TECHNICAL_ERROR_MESSAGE_KEY)
          .put(HttpServletResponse.SC_NOT_FOUND, "http.not.found.error.message")
          .put(HttpServletResponse.SC_HTTP_VERSION_NOT_SUPPORTED, "http.version.not.supported.error.message")
          .build();

  /**
   * Loads the custom error page.
   * @return the ModelAndView object which describes the page
   */
  @RequestMapping(method = RequestMethod.GET, path = "/{httpStatusCode}")
  public ModelAndView pageLoad(@PathVariable(value = "httpStatusCode") int httpStatusCode) {
    ModelAndView modelAndView = new ModelAndView(VIEW_NAME_ERROR);

    String errorMessageKey = errorMessageKeyMap.getOrDefault(httpStatusCode, TECHNICAL_ERROR_MESSAGE_KEY);
    String errorMessage = messageKeyResolver.resolveMessageOrDefault(errorMessageKey);

    modelAndView.addObject(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE, httpStatusCode);
    modelAndView.addObject(WebUtils.ERROR_MESSAGE_ATTRIBUTE, errorMessage);
    return modelAndView;
  }
}
