package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidator;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PasswordChangeControllerTest extends AbstractControllerTest {
  @Mock
  private PasswordChangeService passwordChangeService;

  @Mock
  private UserService userService;

  @Mock
  private PasswordValidator passwordValidator;

  @InjectMocks
  private PasswordChangeController passwordChangeController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{passwordChangeController};
  }
}
