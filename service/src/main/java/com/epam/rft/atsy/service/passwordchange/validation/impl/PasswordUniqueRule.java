package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import com.epam.rft.atsy.service.security.UserDetailsAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

public class PasswordUniqueRule implements PasswordValidationRule {
    private static final String MESSAGE_KEY = "passwordchange.validation.unique";

    @Resource
    private PasswordChangeService passwordChangeService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordUniqueRule() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        UserDetailsAdapter userDetails =
                (UserDetailsAdapter)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<String> oldPasswords = passwordChangeService.getOldPasswords(userDetails.getUserId());

        for (String password : oldPasswords) {
            if (bCryptPasswordEncoder.matches(passwordChangeDTO.getNewPassword(), password)){
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
