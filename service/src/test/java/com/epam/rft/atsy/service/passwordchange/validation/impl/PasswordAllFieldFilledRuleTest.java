package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class PasswordAllFieldFilledRuleTest {

    //A password is considered blank if it contains only whitespace characters.
    public static final String BLANK_PASSWORD = System.getProperty("line.separator");

    public static final String NOT_BLANK_PASSWORD = "baz";

    private static PasswordAllFieldFilledRule rule;

    @BeforeClass
    public static void setUp() {
        rule = new PasswordAllFieldFilledRule();
    }

    @Test
    public void isValidShouldReturnFalseWhenNewPasswordIsBlank() {
        //Given
        PasswordChangeDTO passwordChangeDTO =
                PasswordChangeDTO.builder().newPassword(BLANK_PASSWORD).build();

        //When
        boolean valid = rule.isValid(passwordChangeDTO);

        //Then
        assertFalse(valid);
    }

    @Test
    public void isValidShouldReturnFalseWhenOldPasswordIsBlank() {
        //Given
        PasswordChangeDTO passwordChangeDTO =
                PasswordChangeDTO.builder().oldPassword(BLANK_PASSWORD).build();

        //When
        boolean valid = rule.isValid(passwordChangeDTO);

        //Then
        assertFalse(valid);
    }

    @Test
    public void isValidShouldReturnFalseWhenNewPasswordConfirmIsBlank() {
        //Given
        PasswordChangeDTO passwordChangeDTO =
                PasswordChangeDTO.builder().newPasswordConfirm(BLANK_PASSWORD).build();

        //When
        boolean valid = rule.isValid(passwordChangeDTO);

        //Then
        assertFalse(valid);
    }

    @Test
    public void isValidShouldReturnTrueWhenAllFieldsFilled() {
        //Given
        PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder()
                .newPassword(NOT_BLANK_PASSWORD)
                .newPasswordConfirm(NOT_BLANK_PASSWORD)
                .oldPassword(NOT_BLANK_PASSWORD)
                .build();

        //When
        boolean valid = rule.isValid(passwordChangeDTO);

        //Then
        assertTrue(valid);
    }

    @Test
    public void isValidShouldReturnFalseWhenNewPasswordAndOldPasswordAreBlank() {
        //Given
        PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder()
                .newPassword(BLANK_PASSWORD)
                .oldPassword(BLANK_PASSWORD)
                .build();

        //When
        boolean valid = rule.isValid(passwordChangeDTO);

        //Then
        assertFalse(valid);
    }

    @Test
    public void isValidShouldReturnFalseWhenNewPasswordAndNewPasswordConfirmAreBlank() {
        //Given
        PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder()
                .newPassword(BLANK_PASSWORD)
                .newPasswordConfirm(BLANK_PASSWORD)
                .build();

        //When
        boolean valid = rule.isValid(passwordChangeDTO);

        //Then
        assertFalse(valid);
    }

    @Test
    public void isValidShouldReturnFalseWhenNewPasswordConfirmAndOldPasswordAreBlank() {
        //Given
        PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder()
                .oldPassword(BLANK_PASSWORD)
                .newPasswordConfirm(BLANK_PASSWORD)
                .build();

        //When
        boolean valid = rule.isValid(passwordChangeDTO);

        //Then
        assertFalse(valid);
    }

    @Test
    public void isValidShouldReturnFalseWhenAllFieldsAreBlank() {
        //Given
        PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder()
                .newPassword(BLANK_PASSWORD)
                .newPasswordConfirm(BLANK_PASSWORD)
                .oldPassword(BLANK_PASSWORD)
                .build();

        //When
        boolean valid = rule.isValid(passwordChangeDTO);

        //Then
        assertFalse(valid);
    }


}
