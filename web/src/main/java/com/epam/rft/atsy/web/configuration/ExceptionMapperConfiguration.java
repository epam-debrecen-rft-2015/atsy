package com.epam.rft.atsy.web.configuration;

import com.epam.rft.atsy.service.exception.file.CandidateAlreadyHasCVFileException;
import com.epam.rft.atsy.service.exception.file.FileAlreadyExistsValidationException;
import com.epam.rft.atsy.service.exception.file.FileContainsInvalidCharacterValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsEmptyValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsInWrongExtensionValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsMissingValidationException;
import com.epam.rft.atsy.service.exception.file.FileIsTooLargeValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordAllFieldFilledValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordContainsValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordLengthValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordNewMatchValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordOldMatchValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordUniqueValidationException;
import com.epam.rft.atsy.web.mapper.ExceptionMessagePair;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is responsible for creating the bean that stores all the exception-message pairs. The
 * bean is filled with data here, so if some changes happen among the validation exceptions or the
 * exception messages, this class must be adjusted according to the changes.
 */
@Configuration
public class ExceptionMapperConfiguration {

  /**
   * Creates the bean that stores all the exception-message pairs. After it's filled with the data,
   * the set becomes unmodifiable.
   *
   * @return an unmodifiable Set object filled with all the exception-message pairs.
   */
  @Bean
  public Set<ExceptionMessagePair> ruleValidationExceptionSet() {
    final Set<ExceptionMessagePair> exceptionMessagePairValidationExceptionSet = new HashSet<>();

    // add password validation rule exceptions
    exceptionMessagePairValidationExceptionSet
        .add(new ExceptionMessagePair("passwordchange.validation.allfieldfilled",
            PasswordAllFieldFilledValidationException.class));
    exceptionMessagePairValidationExceptionSet
        .add(new ExceptionMessagePair("passwordchange.validation.contains", PasswordContainsValidationException.class));
    exceptionMessagePairValidationExceptionSet
        .add(new ExceptionMessagePair("passwordchange.validation.length", PasswordLengthValidationException.class));
    exceptionMessagePairValidationExceptionSet
        .add(new ExceptionMessagePair("passwordchange.validation.newpasswordmatch",
            PasswordNewMatchValidationException.class));
    exceptionMessagePairValidationExceptionSet
        .add(new ExceptionMessagePair("passwordchange.validation.oldpasswordmatch",
            PasswordOldMatchValidationException.class));
    exceptionMessagePairValidationExceptionSet
        .add(new ExceptionMessagePair("passwordchange.validation.unique", PasswordUniqueValidationException.class));

    // add file validation rule exceptions
    exceptionMessagePairValidationExceptionSet
        .add(new ExceptionMessagePair("file.is.missing", FileIsMissingValidationException.class));
    exceptionMessagePairValidationExceptionSet
        .add(new ExceptionMessagePair("file.is.empty", FileIsEmptyValidationException.class));
    exceptionMessagePairValidationExceptionSet
        .add(
            new ExceptionMessagePair("file.is.too.large", FileIsTooLargeValidationException.class));
    exceptionMessagePairValidationExceptionSet.add(
        new ExceptionMessagePair("file.is.in.wrong.extension",
            FileIsInWrongExtensionValidationException.class));
    exceptionMessagePairValidationExceptionSet
        .add(new ExceptionMessagePair("file.contains.invalid.character",
            FileContainsInvalidCharacterValidationException.class));
    exceptionMessagePairValidationExceptionSet
        .add(new ExceptionMessagePair("file.already.exists",
            FileAlreadyExistsValidationException.class));
    exceptionMessagePairValidationExceptionSet
        .add(new ExceptionMessagePair("candidate.already.has.cv.file",
            CandidateAlreadyHasCVFileException.class));

    return Collections.unmodifiableSet(exceptionMessagePairValidationExceptionSet);
  }

}
