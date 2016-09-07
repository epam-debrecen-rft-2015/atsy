package com.epam.rft.atsy.web.mapper;

import static org.junit.Assert.assertEquals;

import com.epam.rft.atsy.service.exception.passwordchange.PasswordUniqueValidationException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RuleValidationExceptionMapperImplTest {

  private static final Exception INVALID_EXCEPTION = new Exception();
  private static final Exception VALID_EXCEPTION = new PasswordUniqueValidationException();
  private static final String EXPECTED_MESSAGE_KEY = "unique";

  private static RuleValidationExceptionMapperImpl ruleValidationExceptionMapper;

  @BeforeClass
  public static void setUp() {
    Set<ExceptionMessagePair> exceptionMessagePairSet = new HashSet<>();
    exceptionMessagePairSet
        .add(new ExceptionMessagePair("unique", PasswordUniqueValidationException.class));
    ruleValidationExceptionMapper = new RuleValidationExceptionMapperImpl();
    ruleValidationExceptionMapper.ruleValidationExceptionSet = Collections.unmodifiableSet(
        exceptionMessagePairSet);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getMessageKeyByExceptionShouldThrowIllegalArgumentExceptionWhenExceptionIsNull()
      throws IllegalArgumentException {
    ruleValidationExceptionMapper.getMessageKeyByException(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getMessageKeyByExceptionShouldThrowIllegalArgumentExceptionWhenExceptionIsInvalid()
      throws IllegalArgumentException {
    ruleValidationExceptionMapper.getMessageKeyByException(INVALID_EXCEPTION);
  }

  @Test
  public void getCorrectMessageKeyWhenExceptionIsValid() {
    String
        actualMessageKey =
        ruleValidationExceptionMapper.getMessageKeyByException(VALID_EXCEPTION);
    assertEquals(EXPECTED_MESSAGE_KEY, actualMessageKey);
  }

}
