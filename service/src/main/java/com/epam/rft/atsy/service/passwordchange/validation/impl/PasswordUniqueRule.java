package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import com.epam.rft.atsy.service.security.CustomSecurityContextHolderService;
import com.epam.rft.atsy.service.security.UserDetailsAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class PasswordUniqueRule implements PasswordValidationRule {
  private static final String MESSAGE_KEY = "passwordchange.validation.unique";
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private PasswordChangeService passwordChangeService;

  private CustomSecurityContextHolderService customSecurityContextHolderService;

  public PasswordUniqueRule(PasswordChangeService passwordChangeService,
                            CustomSecurityContextHolderService customSecurityContextHolderService) {
    bCryptPasswordEncoder = new BCryptPasswordEncoder();

    this.passwordChangeService = passwordChangeService;
    this.customSecurityContextHolderService = customSecurityContextHolderService;
  }

  @Override
  public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
    UserDetailsAdapter
        userDetails =
        customSecurityContextHolderService.getCurrentUserDetailsAdapter();

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
