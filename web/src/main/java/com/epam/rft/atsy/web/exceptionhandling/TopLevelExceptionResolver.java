package com.epam.rft.atsy.web.exceptionhandling;

import com.google.common.collect.ImmutableMap;

import com.epam.rft.atsy.service.exception.DuplicateCandidateException;
import com.epam.rft.atsy.service.exception.DuplicateChannelException;
import com.epam.rft.atsy.service.exception.DuplicatePositionException;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.springframework.context.MessageSource;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class handles exceptions that need special treatment. As the first in the exception resolver
 * chain this class is able to apply a specific handler to the exceptions before they would reach
 * the more generic {@code UncheckedExceptionResolver} and the completely type-agnostic {@code
 * DefaultExceptionResolver}
 * @see UncheckedExceptionResolver
 * @see DefaultExceptionResolver
 */
@Component
public class TopLevelExceptionResolver extends AbstractExceptionResolver {
  private static final String TECHNICAL_ERROR_MESSAGE_KEY = "technical.error.message";

  private final Map<Class<? extends DuplicateRecordException>, String>
      duplicateRecordMessageKeyMap =
      ImmutableMap.<Class<? extends DuplicateRecordException>, String>builder()
          .put(DuplicateChannelException.class, "settings.channels.error.duplicate")
          .put(DuplicateCandidateException.class, "candidate.error.duplicate")
          .put(DuplicatePositionException.class, "settings.positions.error.duplicate")
          .build();

  @Resource
  private MessageSource messageSource;

  @Override
  public int getOrder() {
    return HIGHEST_PRECEDENCE;
  }

  @Override
  public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse, Object o,
                                       Exception e) {
    Locale locale = httpServletRequest.getLocale();

    if (e instanceof DuplicateRecordException) {
      return handleDuplicate(locale, httpServletRequest, httpServletResponse,
          (DuplicateRecordException) e);
    } else if (e instanceof HttpMessageNotReadableException) {
      return handleNotReadable(locale, httpServletRequest, httpServletResponse, e);
    } else {
      return null;
    }
  }

  /**
   * Handles {@code DuplicateRecordException} and its descendants.
   * @param locale the current {@&ode Locale}
   * @param request the request that caused an exception
   * @param response the response that will be sent
   * @param ex the exception to be handled
   * @return {@code ModelAndView} with either the error view or a JSON response
   */
  private ModelAndView handleDuplicate(Locale locale, HttpServletRequest request,
                                       HttpServletResponse response,
                                       DuplicateRecordException ex) {
    if (isAjaxRequest(request)) {
      return new ModelAndView(ERROR_VIEW_NAME);
    }

    String
        messageKey =
        duplicateRecordMessageKeyMap.getOrDefault(ex.getClass(), TECHNICAL_ERROR_MESSAGE_KEY);

    String message = messageSource.getMessage(messageKey, new Object[]{ex.getName()}, locale);

    ErrorResponse errorResponse = new ErrorResponse(message);

    return errorResponseToJsonModelAndView(errorResponse);
  }

  /**
   * Handles {@code HttpMessageNotReadableException}.
   * @param locale the current {@&ode Locale}
   * @param request the request that caused an exception
   * @param response the response that will be sent
   * @param ex the exception to be handled
   * @return {@code ModelAndView} with either the error view or a JSON response
   */
  private ModelAndView handleNotReadable(Locale locale, HttpServletRequest request,
                                         HttpServletResponse response,
                                         Exception ex) {
    if (!isAjaxRequest(request)) {
      return new ModelAndView(ERROR_VIEW_NAME);
    }

    String message = messageSource.getMessage(TECHNICAL_ERROR_MESSAGE_KEY, null, locale);

    ErrorResponse errorResponse = new ErrorResponse(message);

    return errorResponseToJsonModelAndView(errorResponse);
  }
}

