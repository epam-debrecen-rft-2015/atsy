package com.epam.rft.atsy.web.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.springframework.web.servlet.ModelAndView;
import org.testng.annotations.Test;

public class OptionsControllerTest {
  private OptionsController underTest = new OptionsController();

  @Test
  public void shouldReturnViewModel() {
    //when
    ModelAndView model = underTest.loadPage();
    //then
    assertThat(model.getViewName(), is("settings"));
  }
}
