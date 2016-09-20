package com.epam.rft.atsy.service.passwordchange.validation.impl;

import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyZeroInteractions;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidationRule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;

@RunWith(MockitoJUnitRunner.class)
public class PasswordValidatorImplTest {
  private static final PasswordChangeDTO DUMMY_PASSWORD_CHANGE_DTO = null;

  @Mock
  private PasswordValidationRule successfulRule;

  @Mock
  private PasswordValidationRule failingRule;

  @Mock
  private PasswordValidationRule unreachedRule;

  private Collection<PasswordValidationRule> passwordValidationRules;

  private PasswordValidatorImpl passwordValidator;

  @Before
  public void setUp() {
    passwordValidationRules = new ArrayList<>();

    passwordValidator = new PasswordValidatorImpl(passwordValidationRules);
    try {
      doThrow(new PasswordValidationException()).when(failingRule)
          .validate(any(PasswordChangeDTO.class));
    } catch (PasswordValidationException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void validateShouldReturnTrueWhenAllRulesAreSatisfied()
      throws PasswordValidationException {
    // Given
    passwordValidationRules.add(successfulRule);

    // When
    passwordValidator.validate(DUMMY_PASSWORD_CHANGE_DTO);

    // Then
    then(successfulRule).should().validate(DUMMY_PASSWORD_CHANGE_DTO);
  }

  @Test(expected = PasswordValidationException.class)
  public void validateShouldThrowWhenARuleFails() throws PasswordValidationException {
    // Given
    passwordValidationRules.add(failingRule);

    // When
    passwordValidator.validate(DUMMY_PASSWORD_CHANGE_DTO);
  }

  @Test
  public void validateShouldCallEachRulesIsValidMethodUntilTheFirstFailingRule()
      throws PasswordValidationException {
    // Given
    passwordValidationRules.add(successfulRule);
    passwordValidationRules.add(failingRule);
    passwordValidationRules.add(unreachedRule);

    // When
    try {
      passwordValidator.validate(DUMMY_PASSWORD_CHANGE_DTO);
    } catch (PasswordValidationException e) {
      // Then
      then(successfulRule).should().validate(DUMMY_PASSWORD_CHANGE_DTO);
      then(failingRule).should().validate(DUMMY_PASSWORD_CHANGE_DTO);

      verifyZeroInteractions(unreachedRule);
    }
  }

}
