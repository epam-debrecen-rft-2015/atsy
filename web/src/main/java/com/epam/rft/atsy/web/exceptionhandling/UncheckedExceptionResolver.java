package com.epam.rft.atsy.web.exceptionhandling;

import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.WebUtils;

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

  private static final String TECHNICAL_ERROR_MESSAGE_KEY = "technical.error.message";

  private MappingJackson2JsonView jsonView;

  private MessageKeyResolver messageKeyResolver;

  public UncheckedExceptionResolver(MappingJackson2JsonView jsonView, MessageKeyResolver messageKeyResolver) {
    this.jsonView = jsonView;
    this.messageKeyResolver = messageKeyResolver;
  }

  @Override
  public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse, Object o,
                                       Exception e) {
    // if e is not an unchecked exception, let other handlers process it
    if (!(e instanceof RuntimeException)) {
      return PASS_TO_NEXT_RESOLVER;
    }

    int httpStatusCode = getHttpStatusCodeForException(e.getClass());
    httpServletResponse.setStatus(httpStatusCode);

    if (!RequestInspector.isAjaxRequest(httpServletRequest)) {
      ModelAndView modelAndView = new ModelAndView(ERROR_VIEW_NAME);

      String errorMessage = getErrorMessageForException(e.getClass());

      modelAndView.addObject(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE, httpStatusCode);
      modelAndView.addObject(WebUtils.ERROR_MESSAGE_ATTRIBUTE, errorMessage);
      return modelAndView;
    } else {
      RestResponse restResponse = new RestResponse(e.getMessage());

      ModelAndView modelAndView = new ModelAndView(jsonView);

      modelAndView.addObject("errorMessage", restResponse.getErrorMessage());
      modelAndView.addObject("fields", restResponse.getFields());

      return modelAndView;
    }
  }

  private int getHttpStatusCodeForException(Class<? extends Exception> e) {
    return AnnotationUtils.isAnnotationDeclaredLocally(ResponseStatus.class, e)
             ? AnnotationUtils.findAnnotation(e, ResponseStatus.class).code().value()
             : HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
  }

  private String getErrorMessageForException(Class<? extends Exception> e) {
    String errorMessageKey =
             AnnotationUtils.isAnnotationDeclaredLocally(ResponseStatus.class, e)
               ? AnnotationUtils.findAnnotation(e, ResponseStatus.class).reason()
               : TECHNICAL_ERROR_MESSAGE_KEY;

    return messageKeyResolver.resolveMessageOrDefault(errorMessageKey);
  }
}
