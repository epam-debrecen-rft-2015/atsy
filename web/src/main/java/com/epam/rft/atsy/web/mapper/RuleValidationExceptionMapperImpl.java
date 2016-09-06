package com.epam.rft.atsy.web.mapper;

import com.epam.rft.atsy.web.configuration.ExceptionMapperConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Set;
import javax.annotation.Resource;

/**
 * Implementation of the {@link RuleValidationExceptionMapper} interface. Only user of the bean that
 * is configured in the {@link ExceptionMapperConfiguration} class. Adds no extra functionality over
 * the interface.
 */
@Component
public class RuleValidationExceptionMapperImpl implements RuleValidationExceptionMapper {

  /**
   * Only package private instead or private to make the testing of this class easier.
   */
  @Resource(name = "exceptionMessagePairValidationExceptionSet")
  Set<ExceptionMessagePair> exceptionMessagePairValidationExceptionSet;

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessageKeyByException(Exception e) throws IllegalArgumentException {
    Assert.notNull(e);
    Class<? extends Exception> exceptionClass = e.getClass();

    for (ExceptionMessagePair exceptionMessagePair : exceptionMessagePairValidationExceptionSet) {
      if (exceptionClass.equals(exceptionMessagePair.getExceptionClass())) {
        return exceptionMessagePair.getMessageKey();
      }
    }

    throw new IllegalArgumentException();
  }

}//class
