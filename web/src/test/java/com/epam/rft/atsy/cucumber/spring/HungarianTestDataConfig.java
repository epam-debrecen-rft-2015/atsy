package com.epam.rft.atsy.cucumber.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/cucumber/lang-hu.properties", encoding = "UTF-8")
@Profile("lang-hu")
public class HungarianTestDataConfig {
}
