package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class) public class PasswordOldPasswordMatchesRuleTest {

    private static final String NEW_PASSWORD = "new password";
    private static final String NEW_PASSWORD_CONFIRM = NEW_PASSWORD;
    private static final String OLD_PASSWORD = "password as hash";
    private static final String PASSWORD_CHANGE_DTO_OLD_PASSWORD = "password";
    private static final String PASSWORD_CHANGE_DTO_DIFFERENT_OLD_PASSWORD = "differentpassword";
    private static final String MESSAGE_KEY = "passwordchange.validation.oldpasswordmatch";

    @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock private UserDetails userDetails;

    @InjectMocks private PasswordOldPasswordMatchesRule passwordOldPasswordMatchesRule;

    @Before public void setUp() {

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    @Test public void isValidShouldBeValidWhenOldPasswordMatches() {
        PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder().newPassword(NEW_PASSWORD)
            .newPasswordConfirm(NEW_PASSWORD_CONFIRM).oldPassword(PASSWORD_CHANGE_DTO_OLD_PASSWORD)
            .build();

        // Given
        given(userDetails.getPassword()).willReturn(OLD_PASSWORD);
        given(bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), OLD_PASSWORD))
            .willReturn(true);

        // When
        boolean result = passwordOldPasswordMatchesRule.isValid(passwordChangeDTO);

        // Then
        assertTrue(result);

        then(userDetails).should().getPassword();
        then(bCryptPasswordEncoder).should()
            .matches(PASSWORD_CHANGE_DTO_OLD_PASSWORD, OLD_PASSWORD);
        verifyNoMoreInteractions(userDetails, bCryptPasswordEncoder);
    }

    @Test public void isValidShouldNotBeValidWhenOldPasswordsAreDifferent() {
        PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder().newPassword(NEW_PASSWORD)
            .newPasswordConfirm(NEW_PASSWORD_CONFIRM)
            .oldPassword(PASSWORD_CHANGE_DTO_DIFFERENT_OLD_PASSWORD).build();

        // Given
        given(userDetails.getPassword()).willReturn(OLD_PASSWORD);
        given(bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), OLD_PASSWORD))
            .willReturn(false);

        // When
        boolean result = passwordOldPasswordMatchesRule.isValid(passwordChangeDTO);

        // Then
        assertFalse(result);

        then(userDetails).should().getPassword();
        then(bCryptPasswordEncoder).should()
            .matches(PASSWORD_CHANGE_DTO_DIFFERENT_OLD_PASSWORD, OLD_PASSWORD);
        verifyNoMoreInteractions(userDetails, bCryptPasswordEncoder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isValidShouldNotBeValidWhenPasswordChangeDTOIsNull() {
        // When
        boolean result = passwordOldPasswordMatchesRule.isValid(null);
    }

    @Test public void isValidShouldNotBeValidWhenUserDetailsIsNull() {
        // Given
        PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder().newPassword(NEW_PASSWORD)
            .newPasswordConfirm(NEW_PASSWORD_CONFIRM).oldPassword(PASSWORD_CHANGE_DTO_OLD_PASSWORD)
            .build();
        SecurityContextHolder.getContext()
            .setAuthentication(new UsernamePasswordAuthenticationToken(null, null));

        // When
        boolean result = passwordOldPasswordMatchesRule.isValid(passwordChangeDTO);

        // Then
        assertFalse(result);

        verifyZeroInteractions(userDetails, bCryptPasswordEncoder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isValidShouldNotBeValidWhenPasswordChangeDTOsPasswordFieldIsNull() {
        // Given
        PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder().newPassword(NEW_PASSWORD)
            .newPasswordConfirm(NEW_PASSWORD_CONFIRM).oldPassword(null).build();

        // When
        boolean result = passwordOldPasswordMatchesRule.isValid(passwordChangeDTO);
    }

    @Test public void isValidShouldNotBeValidWhenUserDetailsPasswordFieldIsNull() {
        PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder().newPassword(NEW_PASSWORD)
            .newPasswordConfirm(NEW_PASSWORD_CONFIRM).oldPassword(PASSWORD_CHANGE_DTO_OLD_PASSWORD)
            .build();

        // Given
        given(userDetails.getPassword()).willReturn(null);
        given(bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), null))
            .willReturn(false);

        // When
        boolean result = passwordOldPasswordMatchesRule.isValid(passwordChangeDTO);

        // Then
        assertFalse(result);

        then(userDetails).should().getPassword();
        then(bCryptPasswordEncoder).should().matches(PASSWORD_CHANGE_DTO_OLD_PASSWORD, null);
        verifyNoMoreInteractions(userDetails, bCryptPasswordEncoder);
    }

    @Test public void getErrorMessageKeyTest() {

        // When
        String msg = passwordOldPasswordMatchesRule.getErrorMessageKey();

        //Then
        assertEquals(msg, MESSAGE_KEY);
    }

}
