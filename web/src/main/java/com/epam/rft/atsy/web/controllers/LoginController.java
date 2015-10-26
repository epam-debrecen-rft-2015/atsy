package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.service.exception.UserNotFoundException;
import com.epam.rft.atsy.service.impl.UserServiceImpl;
import com.epam.rft.atsy.web.encryption.EncryptionUtil;
import com.sun.deploy.net.HttpRequest;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import javax.annotation.Resource;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * Created by Ikantik.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    MessageSource messageSource;

    @Resource
    UserService userService;

    @Resource
    EncryptionUtil encryptionUtil;



    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView pageLoad() {
        ModelAndView model = new ModelAndView("login");
        return model;
    }


    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView handleLogin(@Valid @ModelAttribute UserDTO userDTO,Locale userLocale,BindingResult bindingResult,HttpServletRequest request){


        ModelAndView model = new ModelAndView();

        if (bindingResult.hasErrors()) {
            model.addObject("loginError",messageSource.getMessage("login.error",null,userLocale));
            model.setViewName("loginView");
        }else{
            try {
                userDTO.setPassword(encryptionUtil.passwordHash(userDTO.getPassword()));

                UserDTO foundUser=userService.login(userDTO);

                request.getSession().setAttribute("user",foundUser);

                model.setViewName("redirect:http://www.google.com");

            } catch (UserNotFoundException e) {
                model.addObject("loginError",messageSource.getMessage("login.error",null,userLocale));
                model.setViewName("loginView");
            }

        }



        return model;

    }




}
