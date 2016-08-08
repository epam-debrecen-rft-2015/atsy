package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.AuthenticationService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.UserNotLoggedInException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordOldMatchValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

public class PasswordOldPasswordMatchesRule implements PasswordValidationRule {
  private AuthenticationService authenticationService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public PasswordOldPasswordMatchesRule(AuthenticationService authenticationService) {
    bCryptPasswordEncoder = new BCryptPasswordEncoder();
    this.authenticationService = authenticationService;
  }

  @Override
  public void validate(PasswordChangeDTO passwordChangeDTO) throws PasswordOldMatchValidationException {
    Assert.notNull(passwordChangeDTO);
    Assert.notNull(passwordChangeDTO.getOldPassword());

    // Keep the original behaviour in catch block
    UserDetails userDetails = null;
    try {
      userDetails = authenticationService.getCurrentUserDetails();
    } catch (UserNotLoggedInException e) {
      e.printStackTrace();
      throw new PasswordOldMatchValidationException();
    }

    if (!(bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword()))) {
      throw new PasswordOldMatchValidationException();
    }
  }
}
