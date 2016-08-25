package com.epam.rft.atsy.web;

import com.epam.rft.atsy.web.exceptionhandling.RestResponse;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class FieldErrorResponseComposer {
  private static final String COMMON_INVALID_INPUT_MESSAGE_KEY = "common.invalid.input";

  @Autowired
  private MessageKeyResolver messageKeyResolver;

  public ResponseEntity<RestResponse> composeResponse(BindingResult bindingResult) {
    String
        invalidInputMessage =
        messageKeyResolver.resolveMessageOrDefault(COMMON_INVALID_INPUT_MESSAGE_KEY);

    RestResponse restResponse = new RestResponse(invalidInputMessage);

    bindingResult.getFieldErrors().forEach(error ->
        restResponse.addField(error.getField(),
            messageKeyResolver.resolveMessageOrDefault(error.getDefaultMessage())));

    return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
  }
}
