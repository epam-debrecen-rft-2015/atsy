package com.epam.rft.atsy.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.epam.rft.atsy.web.MediaTypes;
import com.epam.rft.atsy.web.exceptionhandling.GlobalControllerExceptionHandler;
import com.epam.rft.atsy.web.exceptionhandling.UncheckedExceptionResolver;
import com.epam.rft.atsy.web.validator.LazySpringConstraintValidatorFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public abstract class AbstractControllerTest {
  protected static final String VIEW_PREFIX = "/WEB-INF/pages/";
  protected static final String VIEW_SUFFIX = ".jsp";

  protected MockMvc mockMvc;

  protected ObjectMapper objectMapper;

  protected LazySpringConstraintValidatorFactory lazySpringConstraintValidatorFactory = new LazySpringConstraintValidatorFactory();

  @Before
  public void setUp() {
    objectMapper = new ObjectMapper();

    mockMvc =
        MockMvcBuilders.standaloneSetup(controllersUnderTest())
            .setViewResolvers(viewResolver())
            .setControllerAdvice(controllerAdvice().toArray())
            .setHandlerExceptionResolvers(exceptionResolvers())
            .setValidator(localValidatorFactoryBean())
            .setCustomArgumentResolvers(customArgumentResolvers())
            .apply(new LazySpringContstraintValidatorFactoryMockMvcConfigurer())
            .build();
  }

  protected ViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

    viewResolver.setPrefix(VIEW_PREFIX);
    viewResolver.setSuffix(VIEW_SUFFIX);

    return viewResolver;
  }

  protected HandlerExceptionResolver[] exceptionResolvers() {
    HandlerExceptionResolver
        defaultHandlerExceptionResolver =
        new DefaultHandlerExceptionResolver();
    return new HandlerExceptionResolver[]{uncheckedExceptionResolver(),
        defaultHandlerExceptionResolver};
  }

  protected UncheckedExceptionResolver uncheckedExceptionResolver() {
    MappingJackson2JsonView jsonView = new MappingJackson2JsonView(objectMapper);

    jsonView.setContentType(MediaTypes.APPLICATION_JSON_UTF8.toString());

    return new UncheckedExceptionResolver(jsonView);
  }

  protected LocalValidatorFactoryBean localValidatorFactoryBean() {
    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    validator.setConstraintValidatorFactory(this.lazySpringConstraintValidatorFactory);
    return validator;
  }

  protected List<Object> controllerAdvice() {
    List<Object> advices = new LinkedList<>();
    advices.add(new GlobalControllerExceptionHandler());
    addBeans(advices);
    return advices;
  }

  protected void addBeans(List<Object> beans) {
  }

  protected HandlerMethodArgumentResolver[] customArgumentResolvers() {
    return new HandlerMethodArgumentResolver[]{new AuthenticationPrincipalArgumentResolver()};
  }

  /**
   * Builds a {@code POST} request with {@code Content-Type} and {@code Accept} headers set to
   * {@code application/json;cahrset=UTF-8}. The passed object is included as a JSON document in the
   * request body.
   * @param requestUrl the queried url
   * @param content the object that will be serialized into the request body
   * @return a request builder that can be further customized or directly passed to {@link
   * org.springframework.test.web.servlet.MockMvc#perform(RequestBuilder)}
   * @throws JsonProcessingException if the serialization of the passed object fails
   */
  protected MockHttpServletRequestBuilder buildJsonPostRequest(String requestUrl, Object content)
      throws JsonProcessingException {
    return post(requestUrl).contentType(MediaTypes.APPLICATION_JSON_UTF8)
        .accept(MediaTypes.APPLICATION_JSON_UTF8)
        .content(objectMapper.writeValueAsBytes(content));
  }

  protected abstract Object[] controllersUnderTest();

  private class LazySpringContstraintValidatorFactoryMockMvcConfigurer implements MockMvcConfigurer {

    @Override
    public void afterConfigurerAdded(ConfigurableMockMvcBuilder<?> builder) {
    }

    @Override
    public RequestPostProcessor beforeMockMvcCreated(ConfigurableMockMvcBuilder<?> builder, WebApplicationContext context) {
      AbstractControllerTest.this.lazySpringConstraintValidatorFactory.setDelegate(new SpringConstraintValidatorFactory(context.getAutowireCapableBeanFactory()));
      AbstractControllerTest.this.lazySpringConstraintValidatorFactory.setBeanFactory(context.getAutowireCapableBeanFactory());
      return null;
    }

  }
}
