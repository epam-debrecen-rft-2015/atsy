package com.epam.rft.atsy.web.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Ikantik.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.epam.rft.atsy.web")
public class WebConfiguration {

}
