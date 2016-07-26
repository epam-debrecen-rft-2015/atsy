package com.epam.rft.atsy.web.exceptionhandling;

import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Arrays;
import java.util.List;
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
@Component
public class UncheckedExceptionResolver implements HandlerExceptionResolver, Ordered {
  private static final ModelAndView PASS_TO_NEXT_RESOLVER = null;

  private static final String ERROR_VIEW_NAME = "error";

  @Autowired
  private MappingJackson2JsonView jsonView;

  /**
   * The list of exceptions that should not be handled but passed.
   */
  private final List<Class<? extends RuntimeException>> skippedExceptionList = Arrays.asList(
      DuplicateRecordException.class);

  @Override
  public int getOrder() {
    // Spring will place this resolver as the first one in the list of resolvers.
    return HIGHEST_PRECEDENCE;
  }

  @Override
  public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse, Object o,
                                       Exception e) {
    if (!shouldHandleException(e)) {
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

  /**
   * Determnines whether the given excpetion should be handled by this resolver.
   * @param e the exception that was passed to the resolver
   * @return {@code true} if the exception should be handled, {@code false} otherwise
   */
  private boolean shouldHandleException(Exception e) {
    // if e is not an unchecked exception, let other handlers process it
    if (!(e instanceof RuntimeException)) {
      return false;
    }

    return skippedExceptionList.stream().noneMatch(ex -> ex.isInstance(e));
  }
}
