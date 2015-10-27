package com.epam.rft.atsy.web.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.service.exception.UserNotFoundException;
import com.epam.rft.atsy.web.encryption.EncryptionUtil;


/**
 * Created by tothd on 2015. 10. 26..
 */

public class LoginControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginControllerTest.class);
    private static final Locale LOCALE = Locale.UK;
    @InjectMocks  //Amit tesztelsz
        LoginController loginController;

    @Mock //annak a példány változói, amik @Resource-el vannak annotálva
    UserService userService;

    @Mock
    EncryptionUtil encryptionUtil;

    @Mock
    HttpServletRequest request;

    @Mock
    UserDTO userDTO;

    @Mock // paraméterek amiket átadsz
    BindingResult bindingResult;

    @Mock
    HttpSession session;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void handleLoginTest() throws UserNotFoundException {

        //given - Mit kell tegyen a mock
        ModelAndView myModel = new ModelAndView();
        myModel.setViewName("redirect:http://www.google.com");

        when(userDTO.getName()).thenReturn("test");
        when(userDTO.getPassword()).thenReturn("pass1");
        when(userService.login(userDTO)).thenReturn(userDTO);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(request.getSession()).thenReturn(session);
        doNothing().when(session).setAttribute("user", userDTO);
        //when  - Amikor meghívod
        ModelAndView model = loginController.handleLogin(userDTO, LOCALE, bindingResult, request);

        //then  - Amikor ellenőrzöd
        Assert.assertEquals(model.getViewName(), myModel.getViewName());

    }

}
