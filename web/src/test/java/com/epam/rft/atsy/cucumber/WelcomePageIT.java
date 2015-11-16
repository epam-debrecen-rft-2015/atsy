package com.epam.rft.atsy.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
/**
 * Created by mates on 2015. 11. 11..
 */
@RunWith(Cucumber.class)
@CucumberOptions(format = {"json:target/report.json"}, glue = {"com.epam.rft.atsy.cucumber.welcome", "com.epam.rft.atsy.cucumber.util"})
public class WelcomePageIT {
}
