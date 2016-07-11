package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class PasswordContainsRuleTest {
    @Parameterized.Parameters(name = "{index}: isValid(\"{0}\") should be {1}.")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { "", false },
                { "abc", false },
                { "!%@", false },
                { "123", false },
                { "a!b%", false },
                { "a1b2", false },
                { "1%2@", false },
                { "a1b@", true }
        });
    }

    private final String newPassword;

    private final boolean expectedIsValid;

    private final PasswordContainsRule passwordContainsRule;

    public PasswordContainsRuleTest(String newPassword, boolean expectedIsValid) {
        this.newPassword = newPassword;

        this.expectedIsValid = expectedIsValid;

        this.passwordContainsRule = new PasswordContainsRule();
    }

    @Test
    public void test() {
        // Given
        PasswordChangeDTO passwordChangeDTO = passwordChangeDTOFromPassword(newPassword);

        // When
        boolean result = passwordContainsRule.isValid(passwordChangeDTO);

        // Then
        assertThat(result, equalTo(expectedIsValid));
    }

    private PasswordChangeDTO passwordChangeDTOFromPassword(String password) {
        // Only the newPassword field will be tested, therefore there's no
        // need to set the other fields.
        return PasswordChangeDTO.builder().newPassword(password).build();
    }
}
