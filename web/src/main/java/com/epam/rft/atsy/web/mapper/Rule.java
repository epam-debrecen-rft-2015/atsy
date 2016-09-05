package com.epam.rft.atsy.web.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Rule {

  private String messageKey;
  private Class<?> exceptionClass;

}
