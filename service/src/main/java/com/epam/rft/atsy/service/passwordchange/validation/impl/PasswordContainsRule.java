package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordContainsValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;

import java.util.regex.Pattern;

/**
 * Represent the validation rule which demands the new password to contain at least one letter, one
 * number and one special character.
 */
public class PasswordContainsRule implements PasswordValidationRule {
  private static final Pattern LETTER_PATTERN = Pattern.compile(".*[a-zA-Z]+.*");

  private static final Pattern NUMBER_PATTERN = Pattern.compile(".*[0-9]+.*");

  private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile(".*[!@#$%^&_.,;:-]+.*");

  /**
   * Checks whether the given object satisfies this validation rule.
   * @param passwordChangeDTO the object to be validated
   * @return true if the new password contains at least one letter, number and special character
   */
  @Override
  public void validate(PasswordChangeDTO passwordChangeDTO)
      throws PasswordContainsValidationException {
    String newPassword = passwordChangeDTO.getNewPassword();

    if (!(containsLetters(newPassword) && containsNumbers(newPassword) && containsSpecial(
        newPassword))) {
      throw new PasswordContainsValidationException();
    }
  }

  private boolean containsLetters(String password) {
    return LETTER_PATTERN.matcher(password).matches();
  }

  private boolean containsNumbers(String password) {
    return NUMBER_PATTERN.matcher(password).matches();
  }

  private boolean containsSpecial(String password) {
    return SPECIAL_CHARACTER_PATTERN.matcher(password).matches();
  }

}
