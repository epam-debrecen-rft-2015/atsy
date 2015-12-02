package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.service.exception.BackendException;
import com.epam.rft.atsy.service.exception.UserNotFoundException;
import com.epam.rft.atsy.web.encryption.EncryptionUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;


/**
 * Created by tothd on 2015. 10. 26..
 */

public class LoginControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginControllerTest.class);
    private static final Locale LOCALE = Locale.UK;
    private static final String REDIRECT_PARAM = "redirect";

    @InjectMocks
    LoginController loginController;

    @Mock
    UserService userService;

    @Mock
    EncryptionUtil encryptionUtil;

    @Mock
    HttpServletRequest request;

    @Mock
    UserDTO userDTO;

    @Mock
    BindingResult bindingResult;

    @Mock
    HttpSession session;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadPageTest(){
        given(request.getSession()).willReturn(session);
        ModelAndView model = loginController.pageLoad(request);

        assertThat(model.getViewName(), is(equalToIgnoringCase("login")));
    }

    @Test
    public void handleLoginTest() throws UserNotFoundException {

        given(userDTO.getName()).willReturn("test");
        given(userDTO.getPassword()).willReturn("pass1");
        given(userService.login(userDTO)).willReturn(userDTO);
        given(bindingResult.hasErrors()).willReturn(false);
        given(request.getSession()).willReturn(session);
        doNothing().when(session).setAttribute("user", userDTO);
        //when
        ModelAndView model = loginController.handleLogin(userDTO, LOCALE, bindingResult, request);

        //then
        assertThat(model.getViewName(), containsString("redirect:"));
    }

    @Test
    public void handleLoginTestWithBackendException() throws UserNotFoundException {

        given(userDTO.getName()).willReturn("test");
        given(userDTO.getPassword()).willReturn("pass1");
        given(userService.login(userDTO)).willThrow(BackendException.class);
        given(bindingResult.hasErrors()).willReturn(false);
        given(request.getSession()).willReturn(session);
        doNothing().when(session).setAttribute("user", userDTO);
        //when
        ModelAndView model = loginController.handleLogin(userDTO, LOCALE, bindingResult, request);

        //then
        assertThat(model.getViewName(),is(equalToIgnoringCase("login")));
    }

    @Test
    public void handleLoginTestWithUserNotFoundException() throws UserNotFoundException {

        given(userDTO.getName()).willReturn("test");
        given(userDTO.getPassword()).willReturn("pass1");
        given(userService.login(userDTO)).willThrow(UserNotFoundException.class);
        given(bindingResult.hasErrors()).willReturn(false);
        given(request.getSession()).willReturn(session);
        doNothing().when(session).setAttribute("user", userDTO);
        //when
        ModelAndView model = loginController.handleLogin(userDTO, LOCALE, bindingResult, request);

        //then
        assertThat(model.getViewName(),is(equalToIgnoringCase("login")));
    }

    @Test
    public void handleLoginTestWithEmptyName() throws UserNotFoundException {

        given(userDTO.getName()).willReturn("");
        given(userDTO.getPassword()).willReturn("pass1");
        given(userService.login(userDTO)).willReturn(userDTO);
        given(bindingResult.hasErrors()).willReturn(true);
        given(request.getSession()).willReturn(session);
        doNothing().when(session).setAttribute("user", userDTO);
        //when
        ModelAndView model = loginController.handleLogin(userDTO, LOCALE, bindingResult, request);

        //then
        assertThat(model.getViewName(),is(equalToIgnoringCase("login")));
    }

    @Test
    public void handleLoginTestWithEmptyPassword() throws UserNotFoundException {

        given(userDTO.getName()).willReturn("test");
        given(userDTO.getPassword()).willReturn("");
        given(userService.login(userDTO)).willReturn(userDTO);
        given(bindingResult.hasErrors()).willReturn(true);
        given(request.getSession()).willReturn(session);
        doNothing().when(session).setAttribute("user", userDTO);
        //when
        ModelAndView model = loginController.handleLogin(userDTO, LOCALE, bindingResult, request);

        //then
        assertThat(model.getViewName(),is(equalToIgnoringCase("login")));
    }

    @Test
    public void resolveRedirectStringUtilsIsNotBlank() throws UserNotFoundException{
        given(userDTO.getName()).willReturn("test");
        given(userDTO.getPassword()).willReturn("pass1");
        given(userService.login(userDTO)).willReturn(userDTO);
        given(bindingResult.hasErrors()).willReturn(false);
        given(request.getSession()).willReturn(session);
        given(request.getParameter(REDIRECT_PARAM)).willReturn("anything");
        doNothing().when(session).setAttribute("user", userDTO);
        //when
        ModelAndView model = loginController.handleLogin(userDTO, LOCALE, bindingResult, request);

        //then
        assertThat(model.getViewName(), containsString("redirect:"));

    }
}
