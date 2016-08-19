package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.AuthenticationService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.UserNotLoggedInException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordOldMatchValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

/**
 * Represent the validation rule which demands the old password to be the same as the typed in
 * password.
 */
@Slf4j
public class PasswordOldPasswordMatchesRule implements PasswordValidationRule {

  private static final String MESSAGE_KEY = "passwordchange.validation.oldpasswordmatch";

  private AuthenticationService authenticationService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * Creates a new PasswordOldPasswordMatchesRule object that saves the given parameter for later
   * use.
   * @param authenticationService will be used to check if the user is logged in
   */
  public PasswordOldPasswordMatchesRule(AuthenticationService authenticationService) {
    bCryptPasswordEncoder = new BCryptPasswordEncoder();
    this.authenticationService = authenticationService;
  }

  /**
   * Checks whether the given object satisfies this validation rule.
   * @param passwordChangeDTO the object to be validated
   * @return true if the typed in password and the old password match
   */
  @Override
  public void validate(PasswordChangeDTO passwordChangeDTO)
      throws PasswordOldMatchValidationException {
    Assert.notNull(passwordChangeDTO);
    Assert.notNull(passwordChangeDTO.getOldPassword());

    // Keep the original behaviour in catch block
    UserDetails userDetails;
    try {
      userDetails = authenticationService.getCurrentUserDetails();
    } catch (UserNotLoggedInException e) {
      log.error("User is not logged in: ", e);
      throw new PasswordOldMatchValidationException();
    }

    if (!(bCryptPasswordEncoder
        .matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword()))) {
      throw new PasswordOldMatchValidationException();
    }
  }
}
