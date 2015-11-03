package com.epam.rft.atsy.web.controllers;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.service.exception.BackendException;
import com.epam.rft.atsy.service.exception.UserNotFoundException;
import com.epam.rft.atsy.web.encryption.EncryptionUtil;

/**
 * Created by Ikantik.
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    private static final String VIEW_NAME = "login";

    @Resource
    private UserService userService;

    @Resource
    private EncryptionUtil encryptionUtil;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView pageLoad() {
        ModelAndView model = new ModelAndView(VIEW_NAME);
        return model;
    }


    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView handleLogin(@Valid @ModelAttribute UserDTO userDTO, Locale userLocale, BindingResult bindingResult, HttpServletRequest request) {

        ModelAndView model = new ModelAndView(VIEW_NAME);

        if (bindingResult.hasErrors()) {
            model.addObject("loginErrorKey", "login.backend.validation");
        } else {
            try {
                userDTO.setPassword(encryptionUtil.passwordHash(userDTO.getPassword()));

                UserDTO foundUser = userService.login(userDTO);

                request.getSession().setAttribute("user", foundUser);

                model.setViewName("redirect:http://www.google.com");

            } catch (UserNotFoundException e) {
                model.addObject("loginErrorKey", "login.backend.auth.failed");
            } catch (BackendException backendException) {
                model.addObject("loginErrorKey", "login.backend.error");
            }
        }
        return model;

    }


}
