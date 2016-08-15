package com.epam.rft.atsy.web.controllers.rest;

import static junit.framework.TestCase.assertTrue;

import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.web.controllers.AbstractControllerTest;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.testng.annotations.Test;

@RunWith(MockitoJUnitRunner.class)
public class StateHistoryControllerTest extends AbstractControllerTest {

  private static final String REQUEST_URL = "/secure/application_state";

  private static final String COMMON_INVALID_INPUT_MESSAGE_KEY = "common.invalid.input";


  @Mock
  private StatesHistoryService statesHistoryService;

  @Mock
  private MessageSource messageSource;

  @InjectMocks
  private StateHistoryController stateHistoryController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{stateHistoryController};
  }

  @Test
  public void saveOrUpdateShould() {
    assertTrue(false);
  }
}
