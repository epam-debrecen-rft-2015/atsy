package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class) public class PasswordContainsRuleTest {
    @Parameterized.Parameters(name = "{index}: isValid(\"{0}\") should be {1}.")
    public static Collection<Object[]> data() {
        return Arrays.asList(
            new Object[][] {{"", false}, {"abc", false}, {"aBc", false}, {"ABC", false},
                {"!%@", false}, {"123", false}, {"a!b%", false}, {"A!b%", false}, {"A!B%", false},
                {"a1b2", false}, {"A1b2C3", false}, {"A1B2C3", false}, {"1%2@", false},
                {"a1b@", true}, {"aAbB%1", true}, {"1A@$2b3C!", true}, {"-A1B2C812$3$", true}});
    }

    private final String newPassword;

    private final boolean expectedIsValid;

    private final PasswordContainsRule passwordContainsRule;

    public PasswordContainsRuleTest(String newPassword, boolean expectedIsValid) {
        this.newPassword = newPassword;

        this.expectedIsValid = expectedIsValid;

        this.passwordContainsRule = new PasswordContainsRule();
    }

    @Test public void test() {
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
