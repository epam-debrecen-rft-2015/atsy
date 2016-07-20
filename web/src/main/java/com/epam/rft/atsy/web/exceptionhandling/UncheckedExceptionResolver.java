package com.epam.rft.atsy.web.exceptionhandling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class handles the unchecked exceptions thrown by the controllers. Registers itself among the
 * handlers and renders a generic error page upon receiving an ordinary HTTP request or sends a JSON
 * response when it detects an AJAX request.
 */
@Component
public class UncheckedExceptionResolver implements HandlerExceptionResolver, Ordered {
  private static final String ERROR_VIEW_NAME = "error";

  @Autowired
  private MappingJackson2JsonView jsonView;

  @Override
  public int getOrder() {
    // places the handler after the ExceptionHandlerExceptionResolver
    return LOWEST_PRECEDENCE;
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

    if (!RequestInspector.isAjaxRequest(httpServletRequest)) {
      return new ModelAndView(ERROR_VIEW_NAME);
    } else {
      ModelAndView modelAndView = new ModelAndView(jsonView);

      ErrorResponse errorResponse = new ErrorResponse(e.getMessage());

      modelAndView.addObject("errorMessage", errorResponse.getErrorMessage());
      modelAndView.addObject("fields", errorResponse.getFields());

      return modelAndView;
    }
  }
}
