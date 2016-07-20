package com.epam.rft.atsy.service.passwordchange.validation.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.security.CustomSecurityContextHolderService;
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

@RunWith(MockitoJUnitRunner.class)
public class PasswordUniqueRuleTest {

  public static final long USER_ID = 1L;
  public static final String USER_NAME = "test";
  public static final String USER_PASSWORD = "test";

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
  private CustomSecurityContextHolderService customSecurityContextHolderService;

  @InjectMocks
  private PasswordUniqueRule passwordUniqueRule;

  @Before
  public void setUp() {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    singleElementPasswordHistory = Arrays.asList(
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

    given(customSecurityContextHolderService.getCurrentUserDetailsAdapter())
        .willReturn(userDetailsAdapter);
  }

  @Test
  public void isValidShouldBeValidForEmptyPasswordHistory() {
    //Given
    PasswordChangeDTO passwordChangeDto = PasswordChangeDTO.builder()
        .newPassword(NEW_PASSWORD)
        .build();

    given(passwordChangeService.getOldPasswords(USER_ID)).willReturn(EMPTY_PASSWORD_HISTORY);

    //When
    boolean result = passwordUniqueRule.isValid(passwordChangeDto);

    //Then
    assertTrue(result);
  }

  @Test
  public void isValidShouldNotBeValidWhenNewPasswordIsInSingleElementPasswordHistory() {
    //Given
    PasswordChangeDTO passwordChangeDto = PasswordChangeDTO.builder()
        .newPassword(PASSWORD_HISTORY_1)
        .build();

    given(passwordChangeService.getOldPasswords(USER_ID)).willReturn(singleElementPasswordHistory);

    //When
    boolean result = passwordUniqueRule.isValid(passwordChangeDto);

    //Then
    assertFalse(result);
  }

  @Test
  public void isValidShouldBeValidWhenNewPasswordIsNotInSingleElementPasswordHistory() {
    //Given
    PasswordChangeDTO passwordChangeDto = PasswordChangeDTO.builder()
        .newPassword(NEW_PASSWORD)
        .build();

    given(passwordChangeService.getOldPasswords(USER_ID)).willReturn(singleElementPasswordHistory);

    //When
    boolean result = passwordUniqueRule.isValid(passwordChangeDto);

    //Then
    assertTrue(result);
  }

  @Test
  public void isValidShouldNotBeValidWhenNewPasswordIsInThreeElementPasswordHistory() {
    //Given
    PasswordChangeDTO passwordChangeDto = PasswordChangeDTO.builder()
        .newPassword(PASSWORD_HISTORY_1)
        .build();

    given(passwordChangeService.getOldPasswords(USER_ID)).willReturn(threeElementPasswordHistory);

    //When
    boolean result = passwordUniqueRule.isValid(passwordChangeDto);

    //Then
    assertFalse(result);
  }

  @Test
  public void isValidShouldBeValidWhenNewPasswordIsNotInThreeElementPasswordHistory() {
    //Given
    PasswordChangeDTO passwordChangeDto = PasswordChangeDTO.builder()
        .newPassword(NEW_PASSWORD)
        .build();

    given(passwordChangeService.getOldPasswords(USER_ID)).willReturn(threeElementPasswordHistory);

    //When
    boolean result = passwordUniqueRule.isValid(passwordChangeDto);

    //Then
    assertTrue(result);
  }

}
