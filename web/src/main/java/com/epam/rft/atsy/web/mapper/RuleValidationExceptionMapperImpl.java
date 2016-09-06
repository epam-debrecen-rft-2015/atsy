package com.epam.rft.atsy.web.mapper;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Set;
import javax.annotation.Resource;

@Component
public class RuleValidationExceptionMapperImpl implements RuleValidationExceptionMapper {

  @Resource(name = "ruleValidationExceptionSet")
  Set<Rule> ruleValidationExceptionSet;

  @Override
  public String getMessageKeyByException(Exception e) throws IllegalArgumentException {
    Assert.notNull(e);
    Class<? extends Exception> exceptionClass = e.getClass();

    for (Rule rule : ruleValidationExceptionSet) {
      if (exceptionClass.equals(rule.getExceptionClass())) {
        return rule.getMessageKey();
      }
    }

    throw new IllegalArgumentException();
  }

}//class
