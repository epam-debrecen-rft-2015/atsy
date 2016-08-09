package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class PasswordValidatorImplTest {
  private static final PasswordChangeDTO DUMMY_PASSWORD_CHANGE_DTO = null;

  @Mock
  private PasswordValidator successfulRule;

  @Mock
  private PasswordValidator failingRule;

  @Mock
  private PasswordValidator unreachedRule;

  private Collection<PasswordValidator> passwordValidators;

  private PasswordValidatorImpl passwordValidator;

  @Before
  public void setUp() {
    passwordValidators = new ArrayList<>();

    passwordValidator = new PasswordValidatorImpl(passwordValidators);
    try {
      doThrow(new PasswordValidationException()).when(failingRule).validate(any(PasswordChangeDTO.class));
    } catch (PasswordValidationException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void validateShouldReturnTrueWhenAllRulesAreSatisfied() throws PasswordValidationException {
    // Given
    passwordValidators.add(successfulRule);

    // When
    passwordValidator.validate(DUMMY_PASSWORD_CHANGE_DTO);

    // Then
    then(successfulRule).should().validate(DUMMY_PASSWORD_CHANGE_DTO);
  }

  @Test(expected = PasswordValidationException.class)
  public void validateShouldThrowWhenARuleFails() throws PasswordValidationException {
    // Given
    passwordValidators.add(failingRule);

    // When
    passwordValidator.validate(DUMMY_PASSWORD_CHANGE_DTO);
  }

  @Test
  public void validateShouldCallEachRulesIsValidMethodUntilTheFirstFailingRule() throws PasswordValidationException {
    // Given
    passwordValidators.add(successfulRule);
    passwordValidators.add(failingRule);
    passwordValidators.add(unreachedRule);

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
