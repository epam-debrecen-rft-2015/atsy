package com.epam.rft.atsy.web.controllers;

import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public abstract class AbstractControllerTest {
  protected static final String VIEW_PREFIX = "/WEB-INF/pages/";
  protected static final String VIEW_SUFFIX = ".jsp";

  protected MockMvc mockMvc;

  protected ViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

    viewResolver.setPrefix(VIEW_PREFIX);
    viewResolver.setSuffix(VIEW_SUFFIX);

    return viewResolver;
  }

  @Before
  public void setUp() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(controllersUnderTest()).setViewResolvers(viewResolver())
            .build();
  }

  protected abstract Object[] controllersUnderTest();
}
