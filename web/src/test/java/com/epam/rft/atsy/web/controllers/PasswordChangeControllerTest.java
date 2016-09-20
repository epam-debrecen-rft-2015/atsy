package com.epam.rft.atsy.web.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.domain.PasswordHistoryDTO;
import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidator;
import com.epam.rft.atsy.service.security.UserDetailsAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.ZonedDateTime;
import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class PasswordChangeControllerTest extends AbstractControllerTest {
  private static final String REQUEST_URL = "/secure/password/manage";

  private static final String VIEW_NAME = "password_change";

  private static final Long USER_ID = 1L;

  private static final String USER_NAME = "Test";

  private static final String USER_PASSWORD = "pass";

  private static final String PASSWORD_VALIDATION_ERROR_MESSAGE_KEY = "error.message.key";

  private static final String VALIDATION_ERROR_KEY = "validationErrorKey";

  private static final String VALIDATION_SUCCESS_KEY = "validationSuccessKey";

  private static final String PASSWORDCHANGE_VALIDATION_SUCCESS =
      "passwordchange.validation.success";

  private static final String PASSWORD_NOT_SET = null;

  @Mock
  private PasswordChangeService passwordChangeService;

  @Mock
  private UserService userService;

  @Mock
  private PasswordValidator passwordValidator;

  @InjectMocks
  private PasswordChangeController passwordChangeController;


  private PasswordChangeDTO passwordChangeDto;

  private UserDetailsAdapter userDetailsAdapter;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{passwordChangeController};
  }

  @Before
  public void setUpTestData() {
    passwordChangeDto =
        PasswordChangeDTO.builder().oldPassword(USER_PASSWORD).newPassword(USER_PASSWORD)
            .newPasswordConfirm(USER_PASSWORD).build();

    userDetailsAdapter =
        new UserDetailsAdapter(USER_ID, USER_PASSWORD, USER_NAME);

    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(userDetailsAdapter, null));
  }

  @Test
  public void loadPageShouldRenderPasswordChangeView() throws Exception {
    mockMvc.perform(get(REQUEST_URL))
        .andExpect(status().isOk())
        .andExpect(view().name(VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX))
        .andExpect(model().size(0));
  }

  @Test
  public void changePasswordShouldRespondWithModelAndViewContainingErrorMessageWhenPasswordValidationFails()
      throws Exception {

    try {
      doThrow(PasswordValidationException.class).when(passwordValidator)
          .validate(passwordChangeDto);
    } catch (PasswordValidationException e) {
      mockMvc.perform(buildPostRequest())
          .andExpect(status().isOk())
          .andExpect(view().name(VIEW_NAME))
          .andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX))
          .andExpect(model().attributeExists(VALIDATION_ERROR_KEY))
          .andExpect(
              model().attribute(VALIDATION_ERROR_KEY, PASSWORD_VALIDATION_ERROR_MESSAGE_KEY));

      then(passwordValidator).should().validate(passwordChangeDto);
      verifyZeroInteractions(passwordChangeService);
      verifyZeroInteractions(userService);
    }
  }

  @Test
  public void changePasswordShouldRespondWithModelAndViewContainingSuccessKeyWhenPasswordValidationSucceeds()
      throws Exception {
    // Given
    UserDTO userDto =
        UserDTO.builder().id(USER_ID).name(USER_NAME).password(PASSWORD_NOT_SET).build();

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    given(userService.findUserByName(USER_NAME)).willReturn(userDto);

    // When
    mockMvc.perform(buildPostRequest())
        .andExpect(status().isOk())
        .andExpect(view().name(VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX))
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
