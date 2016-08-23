package com.epam.rft.atsy.service.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PositionNotFoundException extends Exception {

  public PositionNotFoundException(String message) {
    super(message);
  }
}
