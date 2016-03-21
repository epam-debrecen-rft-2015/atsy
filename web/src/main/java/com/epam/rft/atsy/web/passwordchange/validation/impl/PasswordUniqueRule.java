package com.epam.rft.atsy.web.passwordchange.validation.impl;

import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.impl.PasswordChangeServiceImpl;
import com.epam.rft.atsy.web.passwordchange.validation.PasswordValidationRule;
import com.epam.rft.atsy.web.security.UserDetailsAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class PasswordUniqueRule implements PasswordValidationRule {

    @Resource
    private PasswordChangeService passwordChangeService;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static final String MESSAGE_KEY="Ezt a jelszót már használta!";

    @Override
    public boolean isValid(PasswordChangeDTO passwordChangeDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String newEncPass=bCryptPasswordEncoder.encode(passwordChangeDTO.getNewPassword());
        List<String> oldPasswords = new ArrayList<>();
        oldPasswords = passwordChangeService.isUnique(((UserDetailsAdapter)principal).getUserId());
        for(String pass:oldPasswords){
            if(pass.equals(newEncPass)){
                return false;
            }
        }
        return true;
    }

    @Override
    public String getErrorMessage() {
        return MESSAGE_KEY;
    }
}
