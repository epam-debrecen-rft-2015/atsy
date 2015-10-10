package com.epam.rft.atsy.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by Ikantik.
 */
@RunWith(Cucumber.class)
@CucumberOptions(format = {"json:target/report.json"}, glue = "com.epam.rft.atsy.cucumber.login")
public class LoginPageIT {
}
