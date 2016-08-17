package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.AuthenticationService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.UserNotLoggedInException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

/**
 * Represent the validation rule which demands the old password to be the same as the typed in
 * password.
 */
public class PasswordOldPasswordMatchesRule implements PasswordValidationRule {

  private static final String MESSAGE_KEY = "passwordchange.validation.oldpasswordmatch";

  private AuthenticationService authenticationService;

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * Creates a new PasswordOldPasswordMatchesRule object that saves the given parameter for later
   * use.
   * @param authenticationService will be used to check if the user is logged in
   */
  public PasswordOldPasswordMatchesRule(
      AuthenticationService authenticationService) {
    bCryptPasswordEncoder = new BCryptPasswordEncoder();

    this.authenticationService = authenticationService;
  }

  /**
   * Checks whether the given object satisfies this validation rule.
   * @param passwordChangeDTO the object to be validated
   * @return true if the typed in password and the old password match
   */
  @Override
  public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
    Assert.notNull(passwordChangeDTO);
    Assert.notNull(passwordChangeDTO.getOldPassword());

    UserDetails userDetails = null;
    try {
      userDetails = authenticationService.getCurrentUserDetails();
    } catch (UserNotLoggedInException e) {
      return false;
    }

    return bCryptPasswordEncoder
        .matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword());
  }

  @Override
  public String getErrorMessageKey() {
    return MESSAGE_KEY;
  }
}
