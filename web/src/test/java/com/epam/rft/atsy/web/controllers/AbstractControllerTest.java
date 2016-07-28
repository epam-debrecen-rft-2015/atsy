package com.epam.rft.atsy.web.controllers;

import com.epam.rft.atsy.web.MediaTypes;
import com.epam.rft.atsy.web.exceptionhandling.GlobalControllerExceptionHandler;
import com.epam.rft.atsy.web.exceptionhandling.UncheckedExceptionResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public abstract class AbstractControllerTest {
  protected static final String VIEW_PREFIX = "/WEB-INF/pages/";
  protected static final String VIEW_SUFFIX = ".jsp";

  protected MockMvc mockMvc;

  protected ObjectMapper objectMapper;

  @Before
  public void setUp() {
    objectMapper = new ObjectMapper();

    mockMvc =
        MockMvcBuilders.standaloneSetup(controllersUnderTest())
            .setViewResolvers(viewResolver())
            .setControllerAdvice(controllerAdvice())
            .setHandlerExceptionResolvers(uncheckedExceptionResolver())
            .build();
  }

  protected ViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

    viewResolver.setPrefix(VIEW_PREFIX);
    viewResolver.setSuffix(VIEW_SUFFIX);

    return viewResolver;
  }

  protected UncheckedExceptionResolver uncheckedExceptionResolver() {
    MappingJackson2JsonView jsonView = new MappingJackson2JsonView(objectMapper);

    jsonView.setContentType(MediaTypes.APPLICATION_JSON_UTF8.toString());

    return new UncheckedExceptionResolver(jsonView);
  }

  protected Object[] controllerAdvice() {
    return new Object[]{new GlobalControllerExceptionHandler()};
  }

  protected abstract Object[] controllersUnderTest();
}
