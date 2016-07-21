package com.epam.rft.atsy.web.exceptionhandling;

import com.google.common.collect.ImmutableMap;

import com.epam.rft.atsy.service.exception.DuplicateCandidateException;
import com.epam.rft.atsy.service.exception.DuplicateChannelException;
import com.epam.rft.atsy.service.exception.DuplicatePositionException;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A global exception handler class for all exceptions thrown by controllers (including the REST
 * ones).
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalControllerExceptionHandler {
  private static final String ERROR_VIEW_NAME = "error";

  private static final String TECHNICAL_ERROR_MESSAGE_KEY = "technical.error.message";

  @Resource
  private MessageSource messageSource;

  @Autowired
  private MappingJackson2JsonView jsonView;

  private final Map<Class<? extends DuplicateRecordException>, String>
      duplicateRecordMessageKeyMap =
      ImmutableMap.<Class<? extends DuplicateRecordException>, String>builder()
          .put(DuplicateChannelException.class, "settings.channels.error.duplicate")
          .put(DuplicateCandidateException.class, "candidate.error.duplicate")
          .put(DuplicatePositionException.class, "settings.positions.error.duplicate")
          .build();

  /**
   * Handles {@code DuplicateRecordException}s (and all its descendants). Sends back
   * a localized error message in a response with {@code HTTP 409 Conflict} status code.
   *
   * @param locale the current locale
   * @param ex the exception to handle
   * @return a {@code ResponseEntity} containing the localized error message
   */
  @ExceptionHandler(DuplicateRecordException.class)
  public ResponseEntity<ErrorResponse> handleDuplicate(Locale locale, DuplicateRecordException ex) {
    String
        messageKey =
        duplicateRecordMessageKeyMap.getOrDefault(ex.getClass(), TECHNICAL_ERROR_MESSAGE_KEY);

    String message = messageSource.getMessage(messageKey, new Object[]{ex.getName()}, locale);

    ErrorResponse errorResponse = new ErrorResponse(message);

    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }


  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ModelAndView handleException(HttpServletRequest request, HttpServletResponse response,
                                      Locale locale, Exception ex) {
    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

    if (!RequestInspector.isAjaxRequest(request)) {
      return new ModelAndView(ERROR_VIEW_NAME);
    } else {
      ModelAndView modelAndView = new ModelAndView(jsonView);

      String message = messageSource.getMessage(TECHNICAL_ERROR_MESSAGE_KEY, null, locale);

      ErrorResponse errorResponse = new ErrorResponse(message);

      modelAndView.addObject("errorMessage", errorResponse.getErrorMessage());
      modelAndView.addObject("fields", errorResponse.getFields());

      return modelAndView;
    }
  }
}
