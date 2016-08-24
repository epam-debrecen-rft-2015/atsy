package com.epam.rft.atsy.web.configuration;

import com.epam.rft.atsy.service.configuration.ServiceConfiguration;
import com.epam.rft.atsy.web.exceptionhandling.UncheckedExceptionResolver;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolverImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.io.File;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan("com.epam.rft.atsy.web")
@PropertySource("classpath:file_storage.properties")
@Import({ServiceConfiguration.class})
public class WebConfiguration extends DelegatingWebMvcConfiguration {

  @Value("${upload_location_cv}")
  private String uploadLocation;
  public static String UPLOAD_LOCATION;

  @PostConstruct
  public void init() {
    UPLOAD_LOCATION = uploadLocation;
    File folder = new File(UPLOAD_LOCATION);
    if (!folder.exists()) {
      folder.mkdir();
    }
  }

  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/pages/");
    viewResolver.setSuffix(".jsp");
    return viewResolver;
  }

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public HttpMessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
    return new MappingJackson2HttpMessageConverter(objectMapper);
  }

  @Bean
  public MessageKeyResolver messageSource() {
    ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
    source.setBasename("classpath:i18n/messages");
    source.setDefaultEncoding("UTF-8");

    MessageKeyResolver messageKeyResolver = new MessageKeyResolverImpl(source);

    return messageKeyResolver;
  }

  @Bean
  public LocaleResolver localeResolver() {
    CookieLocaleResolver resolver = new CookieLocaleResolver();
    resolver.setDefaultLocale(Locale.forLanguageTag("HU"));
    return resolver;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("locale");
    return localeChangeInterceptor;
  }

  @Bean
  public MappingJackson2JsonView mappingJackson2JsonView() {
    return new MappingJackson2JsonView(objectMapper());
  }

  @Override
  protected void configureHandlerExceptionResolvers(
      List<HandlerExceptionResolver> exceptionResolvers) {
    addDefaultHandlerExceptionResolvers(exceptionResolvers);

    // The index is 1, in order to add it after the ExceptionHandlerExceptionResolver
    exceptionResolvers.add(1, new UncheckedExceptionResolver(mappingJackson2JsonView()));
  }

  @Bean()
  public CommonsMultipartResolver multipartResolver() {
    return new CommonsMultipartResolver();
  }

  //To resolve ${} in @Value
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
