package com.epam.rft.atsy.web.controllers.rest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.controllers.AbstractControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
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
}
