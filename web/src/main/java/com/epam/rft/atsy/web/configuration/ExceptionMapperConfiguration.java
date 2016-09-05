package com.epam.rft.atsy.web.configuration;

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
import com.epam.rft.atsy.web.mapper.Rule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ExceptionMapperConfiguration {

  @Bean
  public Set<Rule> ruleValidationExceptionSet() {
    final Set<Rule> ruleValidationExceptionSet = new HashSet<>();

    // add password validation rule exceptions
    ruleValidationExceptionSet
        .add(new Rule("allfieldfilled", PasswordAllFieldFilledValidationException.class));
    ruleValidationExceptionSet.add(new Rule("contains", PasswordContainsValidationException.class));
    ruleValidationExceptionSet.add(new Rule("length", PasswordLengthValidationException.class));
    ruleValidationExceptionSet
        .add(new Rule("newpasswordmatch", PasswordNewMatchValidationException.class));
    ruleValidationExceptionSet
        .add(new Rule("oldpasswordmatch", PasswordOldMatchValidationException.class));
    ruleValidationExceptionSet.add(new Rule("unique", PasswordUniqueValidationException.class));

    // add file validation rule exceptions
    ruleValidationExceptionSet
        .add(new Rule("file.is.missing", FileIsMissingValidationException.class));
    ruleValidationExceptionSet.add(new Rule("file.is.empty", FileIsEmptyValidationException.class));
    ruleValidationExceptionSet
        .add(new Rule("file.is.too.large", FileIsTooLargeValidationException.class));
    ruleValidationExceptionSet.add(
        new Rule("file.is.in.wrong.extension", FileIsInWrongExtensionValidationException.class));
    ruleValidationExceptionSet.add(new Rule("file.contains.invalid.character",
        FileContainsInvalidCharacterValidationException.class));
    ruleValidationExceptionSet
        .add(new Rule("file.already.exists", FileAlreadyExistsValidationException.class));

    return ruleValidationExceptionSet;
  }


}
