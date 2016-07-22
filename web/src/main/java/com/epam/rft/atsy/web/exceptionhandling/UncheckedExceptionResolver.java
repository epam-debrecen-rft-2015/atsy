package com.epam.rft.atsy.web.exceptionhandling;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class handles the unchecked exceptions thrown by the controllers. Registers itself among the
 * handlers and renders a generic error page upon receiving an ordinary HTTP request or sends a JSON
 * response when it detects an AJAX request.
 */
@Component
public class UncheckedExceptionResolver extends AbstractExceptionResolver {
  @Override
  public int getOrder() {
    return HIGHEST_PRECEDENCE + 1;
  }

  @Override
  public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse, Object o,
                                       Exception e) {
    // if e is not an unchecked exception, let other handlers process it
    if (!(e instanceof RuntimeException)) {
      return null;
    }

    httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

    if (!isAjaxRequest(httpServletRequest)) {
      return new ModelAndView(ERROR_VIEW_NAME);
    } else {
      ModelAndView modelAndView = new ModelAndView(jsonView);

      ErrorResponse errorResponse = new ErrorResponse(e.getMessage());

      return errorResponseToJsonModelAndView(errorResponse);
    }
  }
}
