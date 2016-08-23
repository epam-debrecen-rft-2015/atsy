package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.AuthenticationService;
import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.UserNotLoggedInException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordUniqueValidationException;
import com.epam.rft.atsy.service.security.UserDetailsAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PasswordUniqueRuleTest {

  private static final long USER_ID = 1L;
  private static final String USER_NAME = "test";
  private static final String USER_PASSWORD = "test";

  private static final String PASSWORD_HISTORY_1 = "password1";
  private static final String PASSWORD_HISTORY_2 = "password2";
  private static final String PASSWORD_HISTORY_3 = "password3";
  private static final String NEW_PASSWORD = "passwordnew";

  private static final List<String> EMPTY_PASSWORD_HISTORY = Collections.emptyList();

  private List<String> singleElementPasswordHistory;
  private List<String> threeElementPasswordHistory;

  @Mock
  private PasswordChangeService passwordChangeService;

  @Mock
  private AuthenticationService authenticationService;

  @InjectMocks
  private PasswordUniqueRule passwordUniqueRule;

  @Before
  public void setUp() throws UserNotLoggedInException {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    singleElementPasswordHistory = Collections.singletonList(
        bCryptPasswordEncoder.encode(PASSWORD_HISTORY_1)
    );

    threeElementPasswordHistory = Arrays.asList(
        bCryptPasswordEncoder.encode(PASSWORD_HISTORY_1),
        bCryptPasswordEncoder.encode(PASSWORD_HISTORY_2),
        bCryptPasswordEncoder.encode(PASSWORD_HISTORY_3)
    );

    UserDetailsAdapter
        userDetailsAdapter =
        new UserDetailsAdapter(USER_ID, USER_NAME, USER_PASSWORD);

    given(authenticationService.getCurrentUserDetails())
        .willReturn(userDetailsAdapter);
  }

  @Test
  public void validateShouldBeValidForEmptyPasswordHistory() throws PasswordUniqueValidationException {
    //Given
    PasswordChangeDTO passwordChangeDto = PasswordChangeDTO.builder()
        .newPassword(NEW_PASSWORD)
        .build();

    given(passwordChangeService.getOldPasswords(USER_ID)).willReturn(EMPTY_PASSWORD_HISTORY);

    //When
    passwordUniqueRule.validate(passwordChangeDto);
  }

  @Test(expected = PasswordUniqueValidationException.class)
  public void isValidShouldNotBeValidWhenNewPasswordIsInSingleElementPasswordHistory()
      throws PasswordUniqueValidationException {
    //Given
    PasswordChangeDTO passwordChangeDto = PasswordChangeDTO.builder()
        .newPassword(PASSWORD_HISTORY_1)
        .build();

    given(passwordChangeService.getOldPasswords(USER_ID)).willReturn(singleElementPasswordHistory);

    //When
    passwordUniqueRule.validate(passwordChangeDto);
  }

  @Test
  public void isValidShouldBeValidWhenNewPasswordIsNotInSingleElementPasswordHistory()
      throws PasswordUniqueValidationException {
    //Given
    PasswordChangeDTO passwordChangeDto = PasswordChangeDTO.builder()
        .newPassword(NEW_PASSWORD)
        .build();

    given(passwordChangeService.getOldPasswords(USER_ID)).willReturn(singleElementPasswordHistory);

    //When
    passwordUniqueRule.validate(passwordChangeDto);
  }

  @Test(expected = PasswordUniqueValidationException.class)
  public void isValidShouldNotBeValidWhenNewPasswordIsInThreeElementPasswordHistory()
      throws PasswordUniqueValidationException {
    //Given
    PasswordChangeDTO passwordChangeDto = PasswordChangeDTO.builder()
        .newPassword(PASSWORD_HISTORY_1)
        .build();

    given(passwordChangeService.getOldPasswords(USER_ID)).willReturn(threeElementPasswordHistory);

    //When
    passwordUniqueRule.validate(passwordChangeDto);
  }

  @Test
  public void isValidShouldBeValidWhenNewPasswordIsNotInThreeElementPasswordHistory()
      throws PasswordUniqueValidationException {
    //Given
    PasswordChangeDTO passwordChangeDto = PasswordChangeDTO.builder()
        .newPassword(NEW_PASSWORD)
        .build();

    given(passwordChangeService.getOldPasswords(USER_ID)).willReturn(threeElementPasswordHistory);

    //When
    passwordUniqueRule.validate(passwordChangeDto);
  }

}
