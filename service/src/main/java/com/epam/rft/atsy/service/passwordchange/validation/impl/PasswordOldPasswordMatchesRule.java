package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

public class PasswordOldPasswordMatchesRule implements PasswordValidationRule {

    private static final String MESSAGE_KEY = "passwordchange.validation.oldpasswordmatch";

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordOldPasswordMatchesRule() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        Assert.notNull(passwordChangeDTO);
        Assert.notNull(passwordChangeDTO.getOldPassword());

        UserDetails userDetails =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userDetails == null) {
            return false;
        }

        return bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword());
    }

    @Override
    public String getErrorMessageKey() {
        return MESSAGE_KEY;
    }
}
