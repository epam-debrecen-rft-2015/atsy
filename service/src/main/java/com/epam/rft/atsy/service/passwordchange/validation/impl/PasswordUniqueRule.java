package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.AuthenticationService;
import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.BackendException;
import com.epam.rft.atsy.service.exception.UserNotLoggedInException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import com.epam.rft.atsy.service.security.UserDetailsAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class PasswordUniqueRule implements PasswordValidationRule {
  private static final String MESSAGE_KEY = "passwordchange.validation.unique";
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private PasswordChangeService passwordChangeService;

  private AuthenticationService authenticationService;

  public PasswordUniqueRule(PasswordChangeService passwordChangeService,
                            AuthenticationService authenticationService) {
    bCryptPasswordEncoder = new BCryptPasswordEncoder();

    this.passwordChangeService = passwordChangeService;
    this.authenticationService = authenticationService;
  }

  @Override
  public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
    UserDetailsAdapter userDetails = null;

    try {
      userDetails = authenticationService.getCurrentUserDetails();
    } catch (UserNotLoggedInException e) {
      throw new BackendException(e);
    }

    List<String> oldPasswords = passwordChangeService.getOldPasswords(userDetails.getUserId());

    for (String password : oldPasswords) {
      if (bCryptPasswordEncoder.matches(passwordChangeDTO.getNewPassword(), password)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public String getErrorMessageKey() {
    return MESSAGE_KEY;
  }
}
