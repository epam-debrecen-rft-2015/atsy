package com.epam.rft.atsy.service.configuration;

import com.epam.rft.atsy.persistence.configuration.PersistenceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({PersistenceConfiguration.class})
@ComponentScan("com.epam.rft.atsy.service")
public class ServiceConfiguration {

}
