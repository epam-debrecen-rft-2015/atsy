package com.epam.rft.atsy.web.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.domain.PasswordHistoryDTO;
import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.service.exception.PasswordValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidator;
import com.epam.rft.atsy.service.security.CustomUserDetailsService;
import com.epam.rft.atsy.service.security.UserDetailsAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class PasswordChangeControllerTest extends AbstractControllerTest {

  @Configuration
  @EnableWebSecurity
  public static class TestConfiguration extends WebSecurityConfigurerAdapter {

    /*
     * Not sure if the following three configuration methods are necessary or not
     * but I've copied them here hoping that they'd help.
     */
    @Bean
    public CustomUserDetailsService customUserDetailsService() {
      CustomUserDetailsService service = new CustomUserDetailsService();

      return service;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(customUserDetailsService())
          .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
          .antMatchers("/login").permitAll()
          .antMatchers("/secure/**").authenticated()
          .and().formLogin().loginPage("/login").defaultSuccessUrl("/secure/welcome")
          .failureUrl("/login?error=true")
          .and().exceptionHandling().accessDeniedPage("/login?error=true")
          .and().logout().invalidateHttpSession(true).logoutSuccessUrl("/login?logout")
          .and().csrf().disable();
    }

    @Bean
    public UserService userService() {
      return mock(UserService.class);
    }

    @Bean
    public PasswordValidator passwordValidator() {
      return mock(PasswordValidator.class);
    }

    @Bean
    public PasswordChangeService passwordChangeService() {
      return mock(PasswordChangeService.class);
    }

    @Bean
    public PasswordChangeController passwordChangeController() {
      return new PasswordChangeController();
    }
  }

  private static final String REQUEST_URL = "/secure/password/manage";

  private static final String VIEW_NAME = "password_change";

  private static final Long USER_ID = 1L;

  private static final String USER_NAME = "test";

  private static final String USER_PASSWORD = "pass3";

  private static final String PASSWORD_VALIDATION_ERROR_MESSAGE_KEY = "error.message.key";

  private static final String VALIDATION_ERROR_KEY = "validationErrorKey";

  private static final String VALIDATION_SUCCESS_KEY = "validationSuccessKey";

  private static final String PASSWORDCHANGE_VALIDATION_SUCCESS =
      "passwordchange.validation.success";

  private static final String PASSWORD_NOT_SET = null;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private PasswordChangeService passwordChangeService;

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordValidator passwordValidator;

  private PasswordChangeDTO passwordChangeDto;

  private static UserDetailsAdapter userDetailsAdapter;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[0];
  }

  @Override
  public void setUp() {
    Mockito.reset(passwordChangeService, userService, passwordValidator);

    objectMapper = new ObjectMapper();

    mockMvc =
        MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity())
            .build();

    passwordChangeDto =
        PasswordChangeDTO.builder().oldPassword(USER_PASSWORD).newPassword(USER_PASSWORD)
            .newPasswordConfirm(USER_PASSWORD).build();

    // According to the comments on the PR this should be avoided.
    //SecurityContextHolder.getContext()
    //        .setAuthentication(new UsernamePasswordAuthenticationToken(userDetailsAdapter, null));
  }

  @Test
  public void loadPageShouldRenderPasswordChangeView() throws Exception {
    // please see the alwaysExpect expectation on the mockMvc object
    mockMvc.perform(get(REQUEST_URL).with(user(USER_NAME).password(USER_PASSWORD)))
        .andExpect(model().size(0));
  }

  @Test
  public void changePasswordShouldRespondWithModelAndViewContainingErrorMessageWhenPasswordValidationFails()
      throws Exception {
    given(passwordValidator.validate(passwordChangeDto))
        .willThrow(new PasswordValidationException(PASSWORD_VALIDATION_ERROR_MESSAGE_KEY));

    mockMvc.perform(buildPostRequest().with(csrf()).with(user(USER_NAME).password(USER_PASSWORD)))
        .andExpect(authenticated())
        .andExpect(model().attributeExists(VALIDATION_ERROR_KEY))
        .andExpect(model().attribute(VALIDATION_ERROR_KEY, PASSWORD_VALIDATION_ERROR_MESSAGE_KEY));

    then(passwordValidator).should().validate(passwordChangeDto);

    verifyZeroInteractions(passwordChangeService);

    verifyZeroInteractions(userService);
  }

  @Test
  @WithMockUser(username = "test", password = "pass3")
  public void changePasswordShouldRespondWithModelAndViewContainingSuccessKeyWhenPasswordValidationSucceeds()
      throws Exception {
    // Given
    UserDTO userDto =
        UserDTO.builder().id(USER_ID).name(USER_NAME).password(PASSWORD_NOT_SET).build();

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    given(passwordValidator.validate(passwordChangeDto)).willReturn(true);

    given(userService.findUserByName(USER_NAME)).willReturn(userDto);

    // When
    mockMvc.perform(buildPostRequest().with(csrf()))//.with(user(USER_NAME).password(USER_PASSWORD)))
        .andDo(print())
        .andExpect(model().attributeExists(VALIDATION_SUCCESS_KEY))
        .andExpect(model().attribute(VALIDATION_SUCCESS_KEY, PASSWORDCHANGE_VALIDATION_SUCCESS));

    // Then
    then(userService).should().findUserByName(USER_NAME);

    assertTrue(bCryptPasswordEncoder.matches(USER_PASSWORD, userDto.getPassword()));

    then(userService).should().saveOrUpdate(userDto);

    ArgumentCaptor<PasswordHistoryDTO> historyCaptor =
        ArgumentCaptor.forClass(PasswordHistoryDTO.class);

    verify(passwordChangeService).saveOrUpdate(historyCaptor.capture());

    PasswordHistoryDTO historyDto = historyCaptor.getValue();

    assertThat(historyDto.getUserId(), equalTo(USER_ID));
    assertTrue(bCryptPasswordEncoder.matches(USER_PASSWORD, historyDto.getPassword()));
    assertThat(historyDto.getChangeDate(), greaterThan(currentDateMinus(5)));

    assertTrue(bCryptPasswordEncoder.matches(USER_PASSWORD, userDetailsAdapter.getPassword()));
  }


  private MockHttpServletRequestBuilder buildPostRequest() {
    return post(REQUEST_URL)
        .param("oldPassword", passwordChangeDto.getOldPassword())
        .param("newPassword", passwordChangeDto.getNewPassword())
        .param("newPasswordConfirm", passwordChangeDto.getNewPassword());
  }

  private Date currentDateMinus(long seconds) {
    return Date.from(ZonedDateTime.now().minusSeconds(seconds).toInstant());
  }
}
