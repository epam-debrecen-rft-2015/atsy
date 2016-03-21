package com.epam.rft.atsy.web.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.web.passwordchange.validation.PasswordValidationRule;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordOldPasswordMatchesRule implements PasswordValidationRule {

    public static final String MESSAGE_KEY="A régi jelszó nem egyezik!";

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getPassword();
            return bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(),username);
        } else {
            String username = principal.toString();
        }
        return false;
    }

    @Override
    public String getErrorMessage() {
        return MESSAGE_KEY;
    }
}
