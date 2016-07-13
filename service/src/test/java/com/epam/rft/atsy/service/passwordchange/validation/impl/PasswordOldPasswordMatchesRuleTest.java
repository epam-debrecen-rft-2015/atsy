package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import com.epam.rft.atsy.service.security.UserDetailsAdapter;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PasswordOldPasswordMatchesRuleTest {

    private static final long USER_ID = 1L;
    private static final String USER_NAME = "Test";
    private static final String USER_PASSWORD = "pass3";

    private static final String NEW_PASSWORD = "$2a$04$puLlTiezukxjwjT7aKi.z.ialkIi505QQwcFtrtuS1MSzNziosX6y";
    private static final String NEW_PASSWORD_CONFIRM = NEW_PASSWORD;
    private static final String OLD_PASSWORD="$2a$04$h/Zpk/z5ZUQATVR4iATmeuCClT0EsW/TNj6tPmmKHLmqZ4c0f9You";
    private static final String PASSWORD_CHANGE_DTO_OLD_PASSWORD="password";
    private static final String PASSWORD_CHANGE_DTO_UPPERCASE_OLD_PASSWORD="PASSWORD";
    private static final String PASSWORD_CHANGE_DTO_DIFFERENT_OLD_PASSWORD="differentpassword";
    private static final String MESSAGE_KEY = "passwordchange.validation.oldpasswordmatch";

    @Mock
    private PasswordValidationRule passwordValidationRule;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private PasswordOldPasswordMatchesRule passwordOldPasswordMatchesRule;

    @Before
    public void setUp(){

        UserDetailsAdapter userDetailsAdapter = new UserDetailsAdapter(USER_ID, USER_NAME, USER_PASSWORD);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetailsAdapter, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    @Test
    public void isValidShouldBeValidWhenOldPasswordMatches(){
        PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder().newPassword(NEW_PASSWORD).newPasswordConfirm(NEW_PASSWORD_CONFIRM).oldPassword(PASSWORD_CHANGE_DTO_OLD_PASSWORD).build();

        // Given
        given(userDetails.getPassword()).willReturn(OLD_PASSWORD);
        given(bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword())).willReturn(true);

        // When
        boolean result = bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword());
        passwordOldPasswordMatchesRule.isValid(passwordChangeDTO);

        // Then
        assertTrue(result);
    }

    @Test
    public void isValidShouldNotBeValidWhenOldPasswordsAreDifferent(){
        PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder().newPassword(NEW_PASSWORD).newPasswordConfirm(NEW_PASSWORD_CONFIRM).oldPassword(PASSWORD_CHANGE_DTO_DIFFERENT_OLD_PASSWORD).build();

        // Given
        given(userDetails.getPassword()).willReturn(OLD_PASSWORD);
        given(bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword())).willReturn(false);

        // When
        boolean result = bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword());
        passwordOldPasswordMatchesRule.isValid(passwordChangeDTO);

        // Then
        assertFalse(result);
    }

    @Test
    public void isValidShouldNotBeValidWhenOldPasswordsAreNotCaseSensitive(){
        PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder().newPassword(NEW_PASSWORD).newPasswordConfirm(NEW_PASSWORD_CONFIRM).oldPassword(PASSWORD_CHANGE_DTO_UPPERCASE_OLD_PASSWORD).build();

        // Given
        given(userDetails.getPassword()).willReturn(OLD_PASSWORD);
        given(bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword())).willReturn(false);

        // When
        boolean result = bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword());
        passwordOldPasswordMatchesRule.isValid(passwordChangeDTO);

        // Then
        assertFalse(result);
    }

    @Test(expected = NullPointerException.class)
    public void isValidShouldNotBeValidWhenPasswordChangeDTOIsNull(){
        PasswordChangeDTO passwordChangeDTO = null;

        // Given
        given(userDetails.getPassword()).willReturn(OLD_PASSWORD);
        given(bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword())).willThrow(NullPointerException.class);

        // When
        boolean result = bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword());
        passwordOldPasswordMatchesRule.isValid(passwordChangeDTO);
    }

    @Test(expected = NullPointerException.class)
    public void isValidShouldNotBeValidWhenUserDetailsIsNull(){
        PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder().newPassword(NEW_PASSWORD).newPasswordConfirm(NEW_PASSWORD_CONFIRM).oldPassword(PASSWORD_CHANGE_DTO_OLD_PASSWORD).build();
        UserDetails userDetails = null;

        // Given
        given(userDetails.getPassword()).willReturn(OLD_PASSWORD);
        given(bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword())).willThrow(NullPointerException.class);

        // When
        boolean result = bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword());
        passwordOldPasswordMatchesRule.isValid(passwordChangeDTO);
    }

    @Test(expected = NullPointerException.class)
    public void isValidShouldNotBeValidWhenPasswordChangeDTOsPasswordFieldIsNull(){
        PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder().newPassword(NEW_PASSWORD).newPasswordConfirm(NEW_PASSWORD_CONFIRM).oldPassword(null).build();

        // Given
        given(userDetails.getPassword()).willReturn(OLD_PASSWORD);
        given(bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword())).willThrow(NullPointerException.class);

        // When
        boolean result = bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword());
        passwordOldPasswordMatchesRule.isValid(passwordChangeDTO);
    }

    @Test(expected = NullPointerException.class)
    public void isValidShouldNotBeValidWhenUserDetailsPasswordFieldIsNull(){
        PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder().newPassword(NEW_PASSWORD).newPasswordConfirm(NEW_PASSWORD_CONFIRM).oldPassword(null).build();

        // Given
        given(userDetails.getPassword()).willReturn(null);
        given(bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword())).willThrow(NullPointerException.class);

        // When
        boolean result = bCryptPasswordEncoder.matches(passwordChangeDTO.getOldPassword(), userDetails.getPassword());
        passwordOldPasswordMatchesRule.isValid(passwordChangeDTO);
    }

    @Test
    public void getErrorMessageKeyTest() {

        // When
        String msg = passwordOldPasswordMatchesRule.getErrorMessageKey();

        //Then
        assertEquals(msg, MESSAGE_KEY);
    }

}
