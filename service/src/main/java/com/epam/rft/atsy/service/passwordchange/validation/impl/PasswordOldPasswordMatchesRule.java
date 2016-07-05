package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordOldPasswordMatchesRule implements PasswordValidationRule {

    private static final String MESSAGE_KEY = "passwordchange.validation.oldpasswordmatch";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordOldPasswordMatchesRule() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        UserDetails userDetails =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorMessageKey() {
        return MESSAGE_KEY;
    }
}
