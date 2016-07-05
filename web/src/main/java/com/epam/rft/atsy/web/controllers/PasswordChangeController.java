package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.domain.PasswordHistoryDTO;
import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.web.exception.PasswordValidationException;
import com.epam.rft.atsy.web.passwordchange.validation.PasswordValidator;
import com.epam.rft.atsy.web.security.UserDetailsAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@RequestMapping(path = "/secure/password/manage")
public class PasswordChangeController {
    private static final String VIEW_NAME = "password_change";
    public static final String LOGIN_ERROR_KEY = "loginErrorKey";
    public static final String VALIDATION_SUCCESS_KEY = "validationSuccessKey";
    public static final String VALIDATION_ERROR_KEY = "validationErrorKey";
    public static final String LOGIN_BACKEND_VALIDATION = "login.backend.validation";
    public static final String PASSWORDCHANGE_VALIDATION_SUCCESS = "passwordchange.validation.success";
    private static Logger logger = LoggerFactory.getLogger(PasswordChangeController.class);

    @Resource
    PasswordChangeService passwordChangeService;

    @Resource
    UserService userService;

    @Autowired
    PasswordValidator passwordValidator;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadPage() {
        return new ModelAndView(VIEW_NAME);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView changePassword(@ModelAttribute PasswordChangeDTO passwordChangeDTO, BindingResult bindingResult) {

        ModelAndView model = new ModelAndView(VIEW_NAME);
        if (bindingResult.hasErrors()) {
            model.addObject(LOGIN_ERROR_KEY, LOGIN_BACKEND_VALIDATION);
        } else {
            try {
                UserDetailsAdapter userDetailsAdapter = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                passwordValidator.validate(passwordChangeDTO);

                String newPassword = bCryptPasswordEncoder.encode(passwordChangeDTO.getNewPassword());
                PasswordHistoryDTO passwordHistoryDTO = new PasswordHistoryDTO();
                passwordHistoryDTO.setUserId(userDetailsAdapter.getUserId());
                passwordHistoryDTO.setPassword(newPassword);
                passwordHistoryDTO.setChangeDate(new Date());

                UserDTO user = userService.findUserByName(userDetailsAdapter.getUsername());
                user.setPassword(newPassword);
                userService.saveOrUpdate(user);
                passwordChangeService.saveOrUpdate(passwordHistoryDTO);
                userDetailsAdapter.setPassword(newPassword);
                model.addObject(VALIDATION_SUCCESS_KEY, PASSWORDCHANGE_VALIDATION_SUCCESS);
            } catch (PasswordValidationException e) {
                logger.error(e.getMessage());
                model.addObject(VALIDATION_ERROR_KEY,e.getMessage());
            }
        }
        return model;
    }
}
