package com.epam.rft.atsy.web.exceptionhandling;

import com.google.common.collect.ImmutableMap;

import com.epam.rft.atsy.service.exception.DuplicateCandidateException;
import com.epam.rft.atsy.service.exception.DuplicateChannelException;
import com.epam.rft.atsy.service.exception.DuplicatePositionException;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TopLevelExceptionResolver implements HandlerExceptionResolver, Ordered {
  private static final String ERROR_VIEW_NAME = "error";

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

  @Autowired
  private MappingJackson2JsonView jsonView;

  @Override
  public int getOrder() {
    return HIGHEST_PRECEDENCE;
  }

  @Override
  public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse, Object o,
                                       Exception e) {
    Locale locale = httpServletRequest.getLocale();

    if (duplicateRecordMessageKeyMap.containsKey(e.getClass())) {
      return handleDuplicate(locale, httpServletRequest, httpServletResponse,
          (DuplicateRecordException) e);
    } else if (e.getClass() == HttpMessageNotReadableException.class) {
      return handleNotReadable(locale, httpServletRequest, httpServletResponse, e);
    } else {
      return null;
    }
  }

  private ModelAndView handleDuplicate(Locale locale, HttpServletRequest request,
                                       HttpServletResponse response,
                                       DuplicateRecordException ex) {
    if (!RequestInspector.isAjaxRequest(request)) {
      return new ModelAndView(ERROR_VIEW_NAME);
    }

    String
        messageKey =
        duplicateRecordMessageKeyMap.getOrDefault(ex.getClass(), TECHNICAL_ERROR_MESSAGE_KEY);

    String message = messageSource.getMessage(messageKey, new Object[]{ex.getName()}, locale);

    ErrorResponse errorResponse = new ErrorResponse(message);

    ModelAndView modelAndView = new ModelAndView(jsonView);

    modelAndView.addObject("errorMessage", errorResponse.getErrorMessage());
    modelAndView.addObject("fields", errorResponse.getFields());

    return modelAndView;
  }

  private ModelAndView handleNotReadable(Locale locale, HttpServletRequest request,
                                         HttpServletResponse response,
                                         Exception ex) {
    if (!RequestInspector.isAjaxRequest(request)) {
      return new ModelAndView(ERROR_VIEW_NAME);
    }

    String message = messageSource.getMessage(TECHNICAL_ERROR_MESSAGE_KEY, null, locale);

    ErrorResponse errorResponse = new ErrorResponse(message);

    ModelAndView modelAndView = new ModelAndView(jsonView);

    modelAndView.addObject("errorMessage", errorResponse.getErrorMessage());
    modelAndView.addObject("fields", errorResponse.getFields());

    return modelAndView;
  }
}

