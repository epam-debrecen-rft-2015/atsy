package com.epam.rft.atsy.web.mapper;

@FunctionalInterface
public interface RuleValidationExceptionMapper {

  String getMessageKeyByException(Exception e) throws IllegalArgumentException;

}
