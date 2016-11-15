package com.epam.rft.atsy.cucumber.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/cucumber/lang-en.properties")
@Profile({"lang-en", "default"})
public class EnglishTestDataConfig {
}
