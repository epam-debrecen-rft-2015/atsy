package com.epam.rft.atsy.cucumber.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile({"env-integration", "default"})
@PropertySource("classpath:/cucumber/env-integration.properties")
public class IntegrationEnvConfig {
}
