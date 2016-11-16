package com.epam.rft.atsy.cucumber.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = CucumberStepConfig.class)
public class CucumberStepConfig {
}
