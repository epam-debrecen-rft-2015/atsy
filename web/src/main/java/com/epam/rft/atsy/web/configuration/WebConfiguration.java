package com.epam.rft.atsy.web.configuration;

import com.epam.rft.atsy.persistence.configuration.PersistenceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Ikantik.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.epam.rft.atsy.web")
@Import({PersistenceConfiguration.class})
public class WebConfiguration {

}
