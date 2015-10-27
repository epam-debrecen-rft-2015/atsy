package com.epam.rft.atsy.web.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

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
        given(userDTO.getName()).willReturn("test");
        given(userDTO.getName()).willReturn("test");
        given(userDTO.getPassword()).willReturn("pass1");
        given(userService.login(userDTO)).willReturn(userDTO);
        given(bindingResult.hasErrors()).willReturn(false);
        given(request.getSession()).willReturn(session);
        doNothing().when(session).setAttribute("user", userDTO);
        //when  - Amikor meghívod
        ModelAndView model = loginController.handleLogin(userDTO, LOCALE, bindingResult, request);

        //then  - Amikor ellenőrzöd
        assertThat(model.getViewName(), containsString("redirect:"));
    }

}
