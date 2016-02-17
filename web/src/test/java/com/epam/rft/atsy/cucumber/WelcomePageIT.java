package com.epam.rft.atsy.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(format = {"json:target/report.json"}, features = {"src/test/resources/com/epam/rft/atsy/cucumber/Welcome.feature"}, glue = {"com.epam.rft.atsy.cucumber.welcome", "com.epam.rft.atsy.cucumber.util"})
public class WelcomePageIT {
}
