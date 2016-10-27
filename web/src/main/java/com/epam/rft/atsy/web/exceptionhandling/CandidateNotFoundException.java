package com.epam.rft.atsy.web.exceptionhandling;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "candidate.not.found.error.message")
public class CandidateNotFoundException extends Exception {
}
