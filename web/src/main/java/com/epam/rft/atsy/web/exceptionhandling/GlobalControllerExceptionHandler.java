package com.epam.rft.atsy.web.exceptionhandling;

import com.google.common.collect.ImmutableMap;

import com.epam.rft.atsy.service.exception.DuplicateCandidateException;
import com.epam.rft.atsy.service.exception.DuplicateChannelException;
import com.epam.rft.atsy.service.exception.DuplicatePositionException;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

  @Autowired
  private MappingJackson2JsonView jsonView;

  private final Map<Class<? extends DuplicateRecordException>, String>
      duplicateRecordMessageKeyMap =
      ImmutableMap.<Class<? extends DuplicateRecordException>, String>builder()
          .put(DuplicateChannelException.class, "settings.channels.error.duplicate")
          .put(DuplicateCandidateException.class, "candidate.error.duplicate")
          .put(DuplicatePositionException.class, "settings.positions.error.duplicate").build();

  @ExceptionHandler(DuplicateRecordException.class)
  public ModelAndView handleDuplicate(Exception ex) {
    String messageKey = duplicateRecordMessageKeyMap.getOrDefault()
  }


}
