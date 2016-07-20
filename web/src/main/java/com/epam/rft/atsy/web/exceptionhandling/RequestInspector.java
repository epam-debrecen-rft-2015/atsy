package com.epam.rft.atsy.web.exceptionhandling;

import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides utility methods for exception handler classes.
 */
public class RequestInspector {
  /**
   * Checks if the specified HTTP request is an AJAX request. Actually this method inspects the
   * contents of the {@code Accept} header. If it contains {@code application/json} then it's
   * recognized as an AJAX request, otherwise as an ordinary HTTP request.
   * @param httpServletRequest the request
   * @return whether the request is an AJAX request or not.
   */
  public static boolean isAjaxRequest(HttpServletRequest httpServletRequest) {
    String acceptHeader = httpServletRequest.getHeader("Accept");

    if (acceptHeader == null) {
      return false;
    }

    return acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE);
  }

  private RequestInspector() {
    // prevent the class from being instantiated
  }
}
