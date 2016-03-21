package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.domain.PasswordHistoryDTO;
import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.web.exception.PasswordValidationException;
import com.epam.rft.atsy.web.passwordchange.validation.PasswordValidator;
import com.epam.rft.atsy.web.passwordchange.validation.impl.PasswordValidatorImpl;
import com.epam.rft.atsy.web.security.CustomUserDetailsService;
import com.epam.rft.atsy.web.security.UserDetailsAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping(path = "/secure/password/manage")
public class PasswordChangeController {
    private static final String VIEW_NAME = "password_change";

    @Resource
    PasswordChangeService passwordChangeService;

    @Resource
    UserService userService;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadPage() {
        return new ModelAndView(VIEW_NAME);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView changePassword(@ModelAttribute PasswordChangeDTO passwordChangeDTO, Locale userLocale, BindingResult bindingResult, HttpServletRequest request) {

        ModelAndView model = new ModelAndView(VIEW_NAME);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PasswordValidator passwordValidator = new PasswordValidatorImpl();
        try {
            passwordValidator.validate(passwordChangeDTO);

            PasswordHistoryDTO passwordHistoryDTO = new PasswordHistoryDTO();
            passwordHistoryDTO.setUserId(((UserDetailsAdapter)principal).getUserId());
            passwordHistoryDTO.setPassword(bCryptPasswordEncoder.encode(passwordChangeDTO.getNewPassword()));
            passwordHistoryDTO.setChangeDate(new Date());

            UserDTO user = userService.findUserByName(((UserDetailsAdapter)principal).getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(passwordChangeDTO.getNewPassword()));
            userService.saveOrUpdate(user);

            passwordChangeService.saveOrUpdate(passwordHistoryDTO);
        } catch (PasswordValidationException e) {
            e.printStackTrace();
        }

        return model;
    }
}
