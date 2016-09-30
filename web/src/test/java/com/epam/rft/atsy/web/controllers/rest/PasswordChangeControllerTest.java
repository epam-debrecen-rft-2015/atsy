package com.epam.rft.atsy.web.controllers.rest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordAllFieldFilledValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordContainsValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordLengthValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordNewMatchValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordOldMatchValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordUniqueValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidator;
import com.epam.rft.atsy.web.MediaTypes;
import com.epam.rft.atsy.web.controllers.AbstractControllerTest;
import com.epam.rft.atsy.web.mapper.RuleValidationExceptionMapper;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PasswordChangeControllerTest extends AbstractControllerTest {

  private static final String REQUEST_URL = "/secure/password";

  private static final Object MISSING_REQUEST_BODY = null;

  private static final String REQUEST_BODY_IS_MISSING_MESSAGE = "Required request body is missing";

  private static final String VALIDATION_ERROR_MESSAGE = "Validation error";

  private static final String
      ALL_FIELD_FILLED_EXCEPTION_MESSAGE =
      "passwordchange.validation.allfieldfilled";

  private static final String CONTAINS_EXCEPTION_MESSAGE = "passwordchange.validation.contains";

  private static final String LENGTH_EXCEPTION_MESSAGE = "passwordchange.validation.length";

  private static final String
      NEW_PASSWORD_MATCH_EXCEPTION_MESSAGE =
      "passwordchange.validation.newpasswordmatch";

  private static final String
      OLD_PASSWORD_MATCH_EXCEPTION_MESSAGE =
      "passwordchange.validation.oldpasswordmatch";

  private static final String UNIQUE_EXCEPTION_MESSAGE = "passwordchange.validation.unique";

  private static final PasswordValidationException
      PASSWORD_ALL_FIELD_FILLED_VALIDATION_EXCEPTION =
      new PasswordAllFieldFilledValidationException();

  private static final PasswordValidationException
      PASSWORD_CONTAINS_VALIDATION_EXCEPTION =
      new PasswordContainsValidationException();

  private static final PasswordValidationException
      PASSWORD_LENGTH_VALIDATION_EXCEPTION =
      new PasswordLengthValidationException();

  private static final PasswordValidationException
      PASSWORD_NEW_MATCH_VALIDATION_EXCEPTION =
      new PasswordNewMatchValidationException();

  private static final PasswordValidationException
      PASSWORD_OLD_MATCH_VALIDATION_EXCEPTION =
      new PasswordOldMatchValidationException();

  private static final PasswordValidationException
      PASSWORD_UNIQUE_VALIDATION_EXCEPTION =
      new PasswordUniqueValidationException();

  private static final PasswordChangeDTO
      EMPTY_PASSWORD_CHANGE_DTO =
      PasswordChangeDTO.builder().newPassword("").newPasswordConfirm("").oldPassword("").build();

  private static final PasswordChangeDTO
      WEAK_PASSWORD_CHANGE_DTO =
      PasswordChangeDTO.builder().newPassword("a").newPasswordConfirm("a").oldPassword("abcd1234$")
          .build();

  private static final PasswordChangeDTO
      SHORT_PASSWORD_CHANGE_DTO =
      PasswordChangeDTO.builder().newPassword("short").newPasswordConfirm("short")
          .oldPassword("longpassword").build();

  private static final PasswordChangeDTO
      NON_MATCHING_PASSWORD_CHANGE_DTO =
      PasswordChangeDTO.builder().newPassword("1new1").newPasswordConfirm("2new2")
          .oldPassword("oldpassword").build();

  private static final PasswordChangeDTO
      OLD_MATCHING_PASSWORD_CHANGE_DTO =
      PasswordChangeDTO.builder().newPassword("password").newPasswordConfirm("password")
          .oldPassword("password").build();

  private static final PasswordChangeDTO
      NON_UNIQUE_PASSWORD_CHANGE_DTO =
      PasswordChangeDTO.builder().newPassword("nonunique").newPasswordConfirm("nonunique")
          .oldPassword("oldpassword").build();

  @InjectMocks
  private PasswordChangeController passwordChangeController;

  @Mock
  private MessageKeyResolver messageKeyResolver;

  @Mock
  private PasswordChangeService passwordChangeService;

  @Mock
  private UserService userService;

  @Mock
  private RuleValidationExceptionMapper ruleValidationExceptionMapper;

  @Mock
  private PasswordValidator passwordValidator;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{passwordChangeController};
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorResponseWhenRequestBodyIsMissing()
      throws Exception {

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, MISSING_REQUEST_BODY))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.errorMessage").isNotEmpty())
        .andExpect(jsonPath("$.errorMessage", containsString(REQUEST_BODY_IS_MISSING_MESSAGE)))
        .andExpect(jsonPath("$.fields").isEmpty());

  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorMessageWhenPasswordChangeDTOHasEmptyField()
      throws Exception {

    doThrow(PASSWORD_ALL_FIELD_FILLED_VALIDATION_EXCEPTION).when(passwordValidator)
        .validate(EMPTY_PASSWORD_CHANGE_DTO);
    given(ruleValidationExceptionMapper
        .getMessageKeyByException(PASSWORD_ALL_FIELD_FILLED_VALIDATION_EXCEPTION))
        .willReturn(ALL_FIELD_FILLED_EXCEPTION_MESSAGE);
    given(messageKeyResolver.resolveMessageOrDefault(ALL_FIELD_FILLED_EXCEPTION_MESSAGE))
        .willReturn(VALIDATION_ERROR_MESSAGE);

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, EMPTY_PASSWORD_CHANGE_DTO))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorMessage").isNotEmpty())
        .andExpect(jsonPath("$.errorMessage", equalTo(VALIDATION_ERROR_MESSAGE)))
        .andExpect(jsonPath("$.fields").isEmpty());

  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorMessageWhenNewPasswordIsNotContainingTheRequiredCharacters()
      throws Exception {

    doThrow(PASSWORD_CONTAINS_VALIDATION_EXCEPTION).when(passwordValidator)
        .validate(WEAK_PASSWORD_CHANGE_DTO);
    given(ruleValidationExceptionMapper
        .getMessageKeyByException(PASSWORD_CONTAINS_VALIDATION_EXCEPTION))
        .willReturn(CONTAINS_EXCEPTION_MESSAGE);
    given(messageKeyResolver.resolveMessageOrDefault(CONTAINS_EXCEPTION_MESSAGE))
        .willReturn(VALIDATION_ERROR_MESSAGE);

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, WEAK_PASSWORD_CHANGE_DTO))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorMessage").isNotEmpty())
        .andExpect(jsonPath("$.errorMessage", equalTo(VALIDATION_ERROR_MESSAGE)))
        .andExpect(jsonPath("$.fields").isEmpty());

  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorMessageWhenNewPasswordsLengthIsTooShort()
      throws Exception {

    doThrow(PASSWORD_LENGTH_VALIDATION_EXCEPTION).when(passwordValidator)
        .validate(SHORT_PASSWORD_CHANGE_DTO);
    given(ruleValidationExceptionMapper
        .getMessageKeyByException(PASSWORD_LENGTH_VALIDATION_EXCEPTION))
        .willReturn(LENGTH_EXCEPTION_MESSAGE);
    given(messageKeyResolver.resolveMessageOrDefault(LENGTH_EXCEPTION_MESSAGE))
        .willReturn(VALIDATION_ERROR_MESSAGE);

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, SHORT_PASSWORD_CHANGE_DTO))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorMessage").isNotEmpty())
        .andExpect(jsonPath("$.errorMessage", equalTo(VALIDATION_ERROR_MESSAGE)))
        .andExpect(jsonPath("$.fields").isEmpty());

  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorMessageWhenNewPasswordAndItsConfirmDoNotMatch()
      throws Exception {

    doThrow(PASSWORD_NEW_MATCH_VALIDATION_EXCEPTION).when(passwordValidator)
        .validate(NON_MATCHING_PASSWORD_CHANGE_DTO);
    given(ruleValidationExceptionMapper
        .getMessageKeyByException(PASSWORD_NEW_MATCH_VALIDATION_EXCEPTION))
        .willReturn(NEW_PASSWORD_MATCH_EXCEPTION_MESSAGE);
    given(messageKeyResolver.resolveMessageOrDefault(NEW_PASSWORD_MATCH_EXCEPTION_MESSAGE))
        .willReturn(VALIDATION_ERROR_MESSAGE);

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, NON_MATCHING_PASSWORD_CHANGE_DTO))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorMessage").isNotEmpty())
        .andExpect(jsonPath("$.errorMessage", equalTo(VALIDATION_ERROR_MESSAGE)))
        .andExpect(jsonPath("$.fields").isEmpty());

  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorMessageWhenNewPasswordMatchesTheOldOne()
      throws Exception {

    doThrow(PASSWORD_OLD_MATCH_VALIDATION_EXCEPTION).when(passwordValidator)
        .validate(OLD_MATCHING_PASSWORD_CHANGE_DTO);
    given(ruleValidationExceptionMapper
        .getMessageKeyByException(PASSWORD_OLD_MATCH_VALIDATION_EXCEPTION))
        .willReturn(OLD_PASSWORD_MATCH_EXCEPTION_MESSAGE);
    given(messageKeyResolver.resolveMessageOrDefault(OLD_PASSWORD_MATCH_EXCEPTION_MESSAGE))
        .willReturn(VALIDATION_ERROR_MESSAGE);

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, OLD_MATCHING_PASSWORD_CHANGE_DTO))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorMessage").isNotEmpty())
        .andExpect(jsonPath("$.errorMessage", equalTo(VALIDATION_ERROR_MESSAGE)))
        .andExpect(jsonPath("$.fields").isEmpty());

  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorMessageWhenNewPasswordIsUsedBefore()
      throws Exception {

    doThrow(PASSWORD_UNIQUE_VALIDATION_EXCEPTION).when(passwordValidator)
        .validate(NON_UNIQUE_PASSWORD_CHANGE_DTO);
    given(ruleValidationExceptionMapper
        .getMessageKeyByException(PASSWORD_UNIQUE_VALIDATION_EXCEPTION))
        .willReturn(UNIQUE_EXCEPTION_MESSAGE);
    given(messageKeyResolver.resolveMessageOrDefault(UNIQUE_EXCEPTION_MESSAGE))
        .willReturn(VALIDATION_ERROR_MESSAGE);

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, NON_UNIQUE_PASSWORD_CHANGE_DTO))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorMessage").isNotEmpty())
        .andExpect(jsonPath("$.errorMessage", equalTo(VALIDATION_ERROR_MESSAGE)))
        .andExpect(jsonPath("$.fields").isEmpty());

  }

}
