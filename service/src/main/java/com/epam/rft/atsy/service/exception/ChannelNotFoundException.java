package com.epam.rft.atsy.service.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ChannelNotFoundException extends Exception {

  public ChannelNotFoundException(String message) {
    super(message);
  }
}
