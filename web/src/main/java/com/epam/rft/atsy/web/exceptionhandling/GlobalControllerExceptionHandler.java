package com.epam.rft.atsy.web.exceptionhandling;

import com.google.common.collect.ImmutableMap;

import com.epam.rft.atsy.service.exception.DuplicateCandidateException;
import com.epam.rft.atsy.service.exception.DuplicateChannelException;
import com.epam.rft.atsy.service.exception.DuplicatePositionException;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;

/**
 * A global exception handler class for all exceptions thrown by controllers (including the REST
 * ones).
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
  private static final String ERROR_VIEW_NAME = "error";

  private static final String TECHNICAL_ERROR_MESSAGE_KEY = "technical.error.message";

  @Resource
  private MessageKeyResolver messageKeyResolver;

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
   * Handles {@code DuplicateRecordException}s (and all its descendants). Sends back a localized
   * error message in a response with {@code HTTP 409 Conflict} status code.
   * @param locale the current locale
   * @param ex the exception to handle
   * @return a {@code ResponseEntity} containing the localized error message
   */
  @ExceptionHandler(DuplicateRecordException.class)
  public ResponseEntity<RestResponse> handleDuplicate(Locale locale, DuplicateRecordException ex) {
    String
        messageKey =
        duplicateRecordMessageKeyMap.getOrDefault(ex.getClass(), TECHNICAL_ERROR_MESSAGE_KEY);

    String message = messageKeyResolver.resolveMessageOrDefault(messageKey, ex.getName());

    RestResponse restResponse = new RestResponse(message);

    return new ResponseEntity<>(restResponse, HttpStatus.CONFLICT);
  }
}
