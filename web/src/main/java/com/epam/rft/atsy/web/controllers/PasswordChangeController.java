package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.web.exception.PasswordValidationException;
import com.epam.rft.atsy.web.passwordchange.validation.PasswordValidator;
import com.epam.rft.atsy.web.passwordchange.validation.impl.PasswordValidatorImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
@RequestMapping(path = "/secure/password/manage")
public class PasswordChangeController {
    private static final String VIEW_NAME = "password_change";


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadPage() {
        return new ModelAndView(VIEW_NAME);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView changePassword(@ModelAttribute PasswordChangeDTO passwordChangeDTO, Locale userLocale, BindingResult bindingResult, HttpServletRequest request) {

        ModelAndView model = new ModelAndView(VIEW_NAME);

        PasswordValidator passwordValidator = new PasswordValidatorImpl();
        try {
            passwordValidator.validate(passwordChangeDTO);
        } catch (PasswordValidationException e) {
            e.printStackTrace();
        }

        return model;
    }
}
