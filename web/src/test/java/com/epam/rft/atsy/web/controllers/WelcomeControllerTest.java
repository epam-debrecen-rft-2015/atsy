package com.epam.rft.atsy.web.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WelcomeControllerTest {
  @InjectMocks
  private WelcomeController underTest;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldReturnViewModel() {
    //when
    ModelAndView model = underTest.loadPage();
    //then
    assertThat(model.getViewName(), is("candidates"));
  }
}
