package com.epam.rft.atsy.web.exceptionhandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Represents an exception, which is thrown when a candidate is logically deleted or not found in the database.
 */
@AllArgsConstructor
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "candidate.not.found.error.message")
public class CandidateNotFoundException extends RuntimeException {
  @Getter
  private final Long id;
}
