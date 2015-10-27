package com.epam.rft.asty.controller;

import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.web.controllers.LoginController;
import com.epam.rft.atsy.web.encryption.EncryptionUtil;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static org.mockito.Mockito.when;


/**
 * Created by tothd on 2015. 10. 26..
 */

public class LoginControllerTest {

    private static final Logger LOGGER= LoggerFactory.getLogger(LoginControllerTest.class);

    @InjectMocks
    UserService userService;

    @InjectMocks
    EncryptionUtil encryptionUtil;

    @InjectMocks
    LoginController loginController;

    @Mock
    HttpServletRequest request;

    @Mock
    UserDTO userDTO;

    @Mock
    Locale userLocale;

    @Mock
    BindingResult bindingResult;



    @BeforeMethod
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void handleLoginTest(){

        //given
        ModelAndView myModel = new ModelAndView();
        myModel.setViewName("redirect:http://www.google.com");

        //when
        when(userDTO.getName()).thenReturn("test");
        when(userDTO.getPassword()).thenReturn("pass1");

        when(bindingResult.hasErrors()).thenReturn(false);

        when(request.getSession().getAttribute("user")).thenReturn("foundUser");


        ModelAndView model=loginController.handleLogin(userDTO, userLocale, bindingResult, request);


        //then
        Assert.assertEquals(model.getViewName(),myModel.getViewName());



    }

}
