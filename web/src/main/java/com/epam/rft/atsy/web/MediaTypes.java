package com.epam.rft.atsy.web;

import org.springframework.http.MediaType;

import java.nio.charset.Charset;

public class MediaTypes {
  public static final MediaType TEXT_PLAIN_UTF8 =
      new MediaType("text", "plain", Charset.forName("UTF-8"));

  public static final MediaType APPLICATION_JSON_UTF8 =
      new MediaType("application", "json", Charset.forName("UTF-8"));

  private MediaTypes() {
    // Private to prevent instantiating.
  }
}
