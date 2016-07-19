package com.epam.rft.atsy.web;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ErrorResponse {
  private final String errorMessage;

  private final Map<String, String> fields = new HashMap<>();

  public  ErrorResponse() {
    this(StringUtils.EMPTY);
  }

  public void addField(String field, String value) {
    fields.put(field, value);
  }
}
