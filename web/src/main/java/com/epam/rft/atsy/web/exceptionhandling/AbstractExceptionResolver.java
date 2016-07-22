package com.epam.rft.atsy.web.exceptionhandling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;

/**
 * This class is the abstract parent of custom exception resolver classes. Contains the
 * functionality and members shared between the exception resolver classes.
 */
public abstract class AbstractExceptionResolver implements HandlerExceptionResolver, Ordered {
  protected static final String ERROR_VIEW_NAME = "error";

  @Autowired
  protected MappingJackson2JsonView jsonView;

  /**
   * Converts the specified {@code ErrorResponse} to a {@&code ModelAndView} instance that will be
   * sent in JSON format.
   * @param errorResponse the {@code ErrorResponse} object to convert
   * @return a {@code ModelAndView} containing the response data
   * @see org.springframework.web.servlet.view.json.MappingJackson2JsonView
   */
  protected ModelAndView errorResponseToJsonModelAndView(ErrorResponse errorResponse) {
    ModelAndView modelAndView = new ModelAndView(jsonView);

    modelAndView.addObject("errorMessage", errorResponse.getErrorMessage());
    modelAndView.addObject("fields", errorResponse.getFields());

    return modelAndView;
  }

  /**
   * Checks if the specified HTTP request is an AJAX request. Actually this method inspects the
   * contents of the {@code Accept} header. If it contains {@code application/json} then it's
   * recognized as an AJAX request, otherwise as an ordinary HTTP request.
   * @param httpServletRequest the request
   * @return whether the request is an AJAX request or not.
   */
  protected boolean isAjaxRequest(HttpServletRequest httpServletRequest) {
    String acceptHeader = httpServletRequest.getHeader("Accept");

    if (acceptHeader == null) {
      return false;
    }

    return acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE);
  }
}
