package com.epam.rft.atsy.web.mapper;


import com.epam.rft.atsy.service.exception.file.FileIsEmptyValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsInWrongExtensionValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsTooLargeValidationException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileValidationRule {

  FILE_IS_TOO_LARGE("file.is.too.large", FileIsTooLargeValidationException.class),
  FILE_IS_EMPTY("file.is.empty", FileIsEmptyValidationException.class),
  FILE_IS_IN_WRONG_EXTENSION("file.is.in.wrong.extension", FileIsInWrongExtensionValidationException.class);

  private final String messageKey;
  private final Class exceptionClass;
}
