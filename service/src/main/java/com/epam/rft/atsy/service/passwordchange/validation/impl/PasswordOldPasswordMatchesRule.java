package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.BackendException;
import com.epam.rft.atsy.service.exception.UserNotLoggedInException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import com.epam.rft.atsy.service.security.SpringSecurityAuthenticationService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

public class PasswordOldPasswordMatchesRule implements PasswordValidationRule {

  private static final String MESSAGE_KEY = "passwordchange.validation.oldpasswordmatch";

  private SpringSecurityAuthenticationService springSecurityAuthenticationService;

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public PasswordOldPasswordMatchesRule(
      SpringSecurityAuthenticationService springSecurityAuthenticationService) {
    bCryptPasswordEncoder = new BCryptPasswordEncoder();

    this.springSecurityAuthenticationService = springSecurityAuthenticationService;
  }

  @Override
  public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
    Assert.notNull(passwordChangeDTO);
    Assert.notNull(passwordChangeDTO.getOldPassword());

    UserDetails userDetails = null;
    try {
      userDetails = springSecurityAuthenticationService.getCurrentUserDetails();
    } catch (UserNotLoggedInException e) {
      throw new BackendException(e);
    }

    if (userDetails == null) {
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
