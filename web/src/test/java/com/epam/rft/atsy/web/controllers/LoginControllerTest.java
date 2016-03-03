package com.epam.rft.atsy.web.controllers;

import static org.hamcrest.MatcherAssert.assertThat;

/*public class LoginControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginControllerTest.class);
    private static final Locale LOCALE = Locale.UK;
    private static final String REDIRECT_PARAM = "redirect";

    @InjectMocks
    LoginController loginController;

    @Mock
    UserService userService;

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
*/