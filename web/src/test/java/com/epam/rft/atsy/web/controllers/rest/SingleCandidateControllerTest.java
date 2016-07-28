package com.epam.rft.atsy.web.controllers.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.controllers.AbstractControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class SingleCandidateControllerTest extends AbstractControllerTest {
  private static final String REQUEST_URL = "/secure/candidate";

  private static final String COMMON_INVALID_INPUT_MESSAGE_KEY = "common.invalid.input";

  private static final String NOT_NULL_MESSAGE_KEY = "javax.validation.constraints.NotNull.message";

  private final String emptyName = StringUtils.EMPTY;
  private final String tooLongName = StringUtils.repeat('a', 101);
  private final String correctName = "John Doe";

  private final String emptyEmail = StringUtils.EMPTY;
  private final String tooLongEmail = StringUtils.repeat('a', 400) + "@email.com";
  private final String malformedEmail = "malformed";
  private final String correctEmail = "john@doe.com";

  private final String tooLongPhone = StringUtils.repeat('0', 21);
  private final String malformedPhone = "malformed";
  private final String correctPhone = "06301111111";

  private final String tooLongReferer = StringUtils.repeat('a', 21);
  private final String correctReferer = "John Doe";

  private final Short tooLowLanguageSkill = -1;
  private final Short tooHighLanguageSkill = 11;
  private final Short correctLanguageSkill = 10;

  @Mock
  private CandidateService candidateService;

  @Mock
  private MessageSource messageSource;

  @InjectMocks
  private SingleCandidateController singleCandidateController;

  private CandidateDTO emptyCandidateDto;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{singleCandidateController};
  }

  @Override
  public void setUp() {
    objectMapper = new ObjectMapper();

    LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();

    validatorFactoryBean.setValidationMessageSource(messageSource);

    // always return the message key
    given(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class)))
        .willAnswer(i -> i.getArgumentAt(0, String.class));

    mockMvc =
        MockMvcBuilders.standaloneSetup(controllersUnderTest())
            .setViewResolvers(viewResolver())
            .setControllerAdvice(controllerAdvice())
            .setHandlerExceptionResolvers(uncheckedExceptionResolver())
            .setValidator(validatorFactoryBean)
            .build();
  }

  @Before
  public void setUpTestDate() {
    emptyCandidateDto = CandidateDTO.builder().build();
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorResponseWhenEmailIsNull() throws Exception {
    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, emptyCandidateDto))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorMessage", equalTo(COMMON_INVALID_INPUT_MESSAGE_KEY)))
        .andExpect(jsonPath("$.fields.email").exists())
        .andExpect(jsonPath("$.fields.email", equalTo(NOT_NULL_MESSAGE_KEY)));
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorResponseWhenNameIsNull() throws Exception {
    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, emptyCandidateDto))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorMessage", equalTo(COMMON_INVALID_INPUT_MESSAGE_KEY)))
        .andExpect(jsonPath("$.fields.name").exists())
        .andExpect(jsonPath("$.fields.name", equalTo(NOT_NULL_MESSAGE_KEY)));
  }


}
