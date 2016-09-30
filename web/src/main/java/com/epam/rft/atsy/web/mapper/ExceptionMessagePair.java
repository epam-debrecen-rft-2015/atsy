package com.epam.rft.atsy.web.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This class represents a message and an exception pair, which is similar to an entry of a map.
 * It's only use is to support storing the rule validation exceptions along with their corresponding
 * exception messages.
 */
@Getter
@AllArgsConstructor
public class ExceptionMessagePair {

  private String messageKey;
  private Class<? extends Exception> exceptionClass;

}
