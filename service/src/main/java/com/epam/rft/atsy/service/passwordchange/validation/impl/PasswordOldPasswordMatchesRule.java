package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import com.epam.rft.atsy.service.security.CustomSecurityContextHolderService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

public class PasswordOldPasswordMatchesRule implements PasswordValidationRule {

  private static final String MESSAGE_KEY = "passwordchange.validation.oldpasswordmatch";

  private CustomSecurityContextHolderService customSecurityContextHolderService;

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public PasswordOldPasswordMatchesRule(
      CustomSecurityContextHolderService customSecurityContextHolderService) {
    bCryptPasswordEncoder = new BCryptPasswordEncoder();

    this.customSecurityContextHolderService = customSecurityContextHolderService;
  }

  @Override
  public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
    Assert.notNull(passwordChangeDTO);
    Assert.notNull(passwordChangeDTO.getOldPassword());

    UserDetails userDetails = customSecurityContextHolderService.getCurrentUserDetailsAdapter();

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
