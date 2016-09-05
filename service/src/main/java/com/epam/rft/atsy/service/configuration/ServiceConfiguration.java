package com.epam.rft.atsy.service.configuration;

import com.epam.rft.atsy.persistence.configuration.PersistenceConfiguration;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.impl.ModelMapperConverterServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({PersistenceConfiguration.class})
@ComponentScan("com.epam.rft.atsy.service")
public class ServiceConfiguration {

  @Bean
  public ConverterService converterService() {
    ModelMapperConverterServiceImpl
        modelMapperConverterService =
        new ModelMapperConverterServiceImpl(new ModelMapper());
    return modelMapperConverterService;
  }

}
