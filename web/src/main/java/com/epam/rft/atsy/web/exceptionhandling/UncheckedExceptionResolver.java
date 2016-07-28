package com.epam.rft.atsy.web.exceptionhandling;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class handles the unchecked exceptions thrown by the controllers. Registered with the
 * highest precedence among the handlers, it renders a generic error page upon receiving an ordinary
 * HTTP request or sends a JSON response when it detects an AJAX request.
 *
 * Maintains a list of exception classes that require a more specific handling and therefore should
 * be passed to resolvers with lower precedence.
 */
public class UncheckedExceptionResolver implements HandlerExceptionResolver {
  private static final ModelAndView PASS_TO_NEXT_RESOLVER = null;

  private static final String ERROR_VIEW_NAME = "error";

  private MappingJackson2JsonView jsonView;

  public UncheckedExceptionResolver(MappingJackson2JsonView jsonView) {
    this.jsonView = jsonView;
  }

  @Override
  public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse, Object o,
                                       Exception e) {
    // if e is not an unchecked exception, let other handlers process it
    if (!(e instanceof RuntimeException)) {
      return PASS_TO_NEXT_RESOLVER;
    }

    httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

    if (!RequestInspector.isAjaxRequest(httpServletRequest)) {
      return new ModelAndView(ERROR_VIEW_NAME);
    } else {
      RestResponse restResponse = new RestResponse(e.getMessage());

      ModelAndView modelAndView = new ModelAndView(jsonView);

      modelAndView.addObject("errorMessage", restResponse.getErrorMessage());
      modelAndView.addObject("fields", restResponse.getFields());

      return modelAndView;
    }
  }
}
