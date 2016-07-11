package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


/**
 * Created by Gabor_Ivanyi-Nagy on 7/11/2016.
 */
public class PasswordNewMatchValidationRuleTest {

    private static final String NEW_PASSWORD = "New password";
    private static final String NEW_PASSWORD_CONFIRM_RIGHT = NEW_PASSWORD;
    private static final String NEW_PASSWORD_CONFIRM_NOT_RIGHT = "Not a new password";
    private static final String NEW_PASSWORD_CONFIRM_UPPER_CASE_NOT_RIGHT = "NEW PASSWORD";
    private static final String ERROR_MESSAGE_KEY = "passwordchange.validation.newpasswordmatch";

    private static PasswordValidationRule passwordValidationRule;

    @BeforeClass
    public static void setUp() {
        passwordValidationRule = new PasswordNewMatchValidationRule();
    }

    @Test
    public void isValidNewPasswordShouldBeRight() {
        PasswordChangeDTO passwordChangeDTO = getPasswordChangeDTO(NEW_PASSWORD, NEW_PASSWORD_CONFIRM_RIGHT);
        assertThat(passwordValidationRule.isValid(passwordChangeDTO), is(true));
    }

    @Test
    public void isValidNewPasswordShouldBeNotRight() {
        PasswordChangeDTO passwordChangeDTO = getPasswordChangeDTO(NEW_PASSWORD, NEW_PASSWORD_CONFIRM_NOT_RIGHT);
        assertThat(passwordValidationRule.isValid(passwordChangeDTO), is(false));
    }

    @Test
    public void isValidNewPasswordShouldBeNotRightUpperCase() {
        PasswordChangeDTO passwordChangeDTO = getPasswordChangeDTO(NEW_PASSWORD, NEW_PASSWORD_CONFIRM_UPPER_CASE_NOT_RIGHT);
        assertThat(passwordValidationRule.isValid(passwordChangeDTO), is(false));
    }

    @Test
    public void getErrorMessageKeyTest() {
        assertEquals(passwordValidationRule.getErrorMessageKey(), ERROR_MESSAGE_KEY);
    }

    private PasswordChangeDTO getPasswordChangeDTO(String newPassword, String newPasswordConfirm) {
        return PasswordChangeDTO.builder().newPassword(newPassword).newPasswordConfirm(newPasswordConfirm).build();
    }
}
