package com.epam.rft.atsy.web.exceptionhandling;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * This class stores error messages that can be sent back to the frontend. An
 * instance of this class can hold a single error message providing general information
 * about the error and separate error messages for each malformed field. In absence of errors
 * the {@code errorMessage} is the {@code empty String} and the {@code fields} map is
 * empty.
 */
@Value
@AllArgsConstructor
public class RestResponse {
  /**
   * {@code RestResponse} instance without an error message and with an empty
   * fields map.
   */
  public static final RestResponse NO_ERROR = new RestResponse();

  private final String errorMessage;

  private final Map<String, String> fields = new HashMap<>();

  public RestResponse() {
    this(StringUtils.EMPTY);
  }

  /**
   * Adds a new key-value pair to the map of fields. By default the key is
   * the name of the field containing malformed input and the value is the
   * corresponding detail message.
   * @param field the name of the field
   * @param value the corresponding detail message
   */
  public void addField(String field, String value) {
    fields.put(field, value);
  }
}
