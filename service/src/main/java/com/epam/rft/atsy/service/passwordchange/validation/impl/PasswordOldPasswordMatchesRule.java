package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordOldPasswordMatchesRule implements PasswordValidationRule {

    public static final String MESSAGE_KEY="passwordchange.validation.oldpasswordmatch";

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword());
    }

    @Override
    public String getErrorMessage() {
        return MESSAGE_KEY;
    }
}
