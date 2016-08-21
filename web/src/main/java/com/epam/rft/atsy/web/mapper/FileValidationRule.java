package com.epam.rft.atsy.web.mapper;


import com.epam.rft.atsy.service.exception.file.FileAlreadyExistsValidationException;
import com.epam.rft.atsy.service.exception.file.FileContainsInvalidCharacterValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsEmptyValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsInWrongExtensionValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsMissingValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsTooLargeValidationException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileValidationRule {

  FILE_IS_MISSING("file.is.missing", FileIsMissingValidationException.class),
  FILE_IS_EMPTY("file.is.empty", FileIsEmptyValidationException.class),
  FILE_IS_TOO_LARGE("file.is.too.large", FileIsTooLargeValidationException.class),
  FILE_IS_IN_WRONG_EXTENSION("file.is.in.wrong.extension",
      FileIsInWrongExtensionValidationException.class),
  FILE_CONTAINS_INVALID_CHARACTER("file.contains.invalid.character",
      FileContainsInvalidCharacterValidationException.class),
  FILE_ALREADY_EXISTS("file.already.exists", FileAlreadyExistsValidationException.class);

  private final String messageKey;
  private final Class exceptionClass;
}
