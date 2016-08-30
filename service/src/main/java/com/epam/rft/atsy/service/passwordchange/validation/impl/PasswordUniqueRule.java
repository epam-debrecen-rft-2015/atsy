package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.AuthenticationService;
import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.BackendException;
import com.epam.rft.atsy.service.exception.UserNotLoggedInException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordUniqueValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import com.epam.rft.atsy.service.security.UserDetailsAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * Represent the validation rule which demands the new password to differ from the previously given
 * passwords of the same user. If there are no previous passwords, this rule is always satisfied.
 */
public class PasswordUniqueRule implements PasswordValidationRule {
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private PasswordChangeService passwordChangeService;

  private AuthenticationService authenticationService;

  /**
   * Creates a new PasswordUniqueRule object that saves the given parameters for later use.
   * @param passwordChangeService will be used to get password information about the user
   * @param authenticationService will be used to check if the user is logged in
   */
  public PasswordUniqueRule(PasswordChangeService passwordChangeService,
                            AuthenticationService authenticationService) {
    bCryptPasswordEncoder = new BCryptPasswordEncoder();

    this.passwordChangeService = passwordChangeService;
    this.authenticationService = authenticationService;
  }

  /**
   * Checks whether the given object satisfies this validation rule.
   * @param passwordChangeDTO the object to be validated
   * @return true if the new password is unique among the previous passwords of the same user
   */
  @Override
  public void validate(PasswordChangeDTO passwordChangeDTO) throws PasswordUniqueValidationException {
    UserDetailsAdapter userDetails = null;

    try {
      userDetails = authenticationService.getCurrentUserDetails();
    } catch (UserNotLoggedInException e) {
      throw new BackendException(e);
    }

    List<String> oldPasswords = passwordChangeService.getOldPasswords(userDetails.getUserId());

    for (String password : oldPasswords) {
      if (bCryptPasswordEncoder.matches(passwordChangeDTO.getNewPassword(), password)) {
        throw new PasswordUniqueValidationException();
      }
    }
  }
}
