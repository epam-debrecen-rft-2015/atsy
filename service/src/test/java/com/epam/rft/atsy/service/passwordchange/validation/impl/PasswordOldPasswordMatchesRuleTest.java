package com.epam.rft.atsy.service.passwordchange.validation.impl;

import static org.mockito.BDDMockito.given;

import com.epam.rft.atsy.service.AuthenticationService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.UserNotLoggedInException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordOldMatchValidationException;
import com.epam.rft.atsy.service.security.UserDetailsAdapter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class PasswordOldPasswordMatchesRuleTest {

  private static final Long USER_ID = 1L;
  private static final String USER_NAME = "Test";

  private static final String NEW_PASSWORD = "new password";
  private static final String NEW_PASSWORD_CONFIRM = NEW_PASSWORD;
  private static final String PASSWORD_CHANGE_DTO_OLD_PASSWORD = "password";
  private static final String PASSWORD_CHANGE_DTO_DIFFERENT_OLD_PASSWORD = "differentpassword";

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Mock
  private AuthenticationService authenticationService;

  @InjectMocks
  private PasswordOldPasswordMatchesRule passwordOldPasswordMatchesRule;

  @Before
  public void setUp() throws UserNotLoggedInException {

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    String oldPasswordHash = bCryptPasswordEncoder.encode(PASSWORD_CHANGE_DTO_OLD_PASSWORD);

    UserDetailsAdapter
        userDetailsAdapter =
        new UserDetailsAdapter(USER_ID, oldPasswordHash, USER_NAME);

    given(authenticationService.getCurrentUserDetails())
        .willReturn(userDetailsAdapter);

  }

  @Test
  public void isValidShouldBeValidWhenOldPasswordMatches()
      throws PasswordOldMatchValidationException {
    // Given
    PasswordChangeDTO
        passwordChangeDTO =
        PasswordChangeDTO.builder().newPassword(NEW_PASSWORD)
            .newPasswordConfirm(NEW_PASSWORD_CONFIRM).oldPassword(PASSWORD_CHANGE_DTO_OLD_PASSWORD)
            .build();

    // When
    passwordOldPasswordMatchesRule.validate(passwordChangeDTO);
  }

  @Test(expected = PasswordOldMatchValidationException.class)
  public void isValidShouldNotBeValidWhenOldPasswordsAreDifferent()
      throws PasswordOldMatchValidationException {
    // Given
    PasswordChangeDTO
        passwordChangeDTO =
        PasswordChangeDTO.builder().newPassword(NEW_PASSWORD)
            .newPasswordConfirm(NEW_PASSWORD_CONFIRM)
            .oldPassword(PASSWORD_CHANGE_DTO_DIFFERENT_OLD_PASSWORD).build();

    // When
    passwordOldPasswordMatchesRule.validate(passwordChangeDTO);
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidShouldNotBeValidWhenPasswordChangeDTOIsNull()
      throws PasswordOldMatchValidationException {
    // When
    passwordOldPasswordMatchesRule.validate(null);
  }

  @Test(expected = PasswordOldMatchValidationException.class)
  public void isValidShouldNotBeValidWhenUserIsNotLoggedIn()
      throws UserNotLoggedInException, PasswordOldMatchValidationException {
    // Given
    PasswordChangeDTO
        passwordChangeDTO =
        PasswordChangeDTO.builder().newPassword(NEW_PASSWORD)
            .newPasswordConfirm(NEW_PASSWORD_CONFIRM).oldPassword(PASSWORD_CHANGE_DTO_OLD_PASSWORD)
            .build();
    given(authenticationService.getCurrentUserDetails()).willThrow(UserNotLoggedInException.class);

    // When
    passwordOldPasswordMatchesRule.validate(passwordChangeDTO);
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidShouldNotBeValidWhenPasswordChangeDTOsPasswordFieldIsNull()
      throws PasswordOldMatchValidationException {
    // Given
    PasswordChangeDTO
        passwordChangeDTO =
        PasswordChangeDTO.builder().newPassword(NEW_PASSWORD)
            .newPasswordConfirm(NEW_PASSWORD_CONFIRM).oldPassword(null).build();

    // When
    passwordOldPasswordMatchesRule.validate(passwordChangeDTO);
  }

  @Test(expected = PasswordOldMatchValidationException.class)
  public void isValidShouldNotBeValidWhenUserDetailsPasswordFieldIsNull()
      throws UserNotLoggedInException, PasswordOldMatchValidationException {
    // Given
    PasswordChangeDTO
        passwordChangeDTO =
        PasswordChangeDTO.builder().newPassword(NEW_PASSWORD)
            .newPasswordConfirm(NEW_PASSWORD_CONFIRM).oldPassword(PASSWORD_CHANGE_DTO_OLD_PASSWORD)
            .build();

    UserDetailsAdapter
        userDetailsAdapterWithNullPassword =
        new UserDetailsAdapter(USER_ID, null, USER_NAME);

    given(authenticationService.getCurrentUserDetails())
        .willReturn(userDetailsAdapterWithNullPassword);

    // When
    passwordOldPasswordMatchesRule.validate(passwordChangeDTO);
  }
}
