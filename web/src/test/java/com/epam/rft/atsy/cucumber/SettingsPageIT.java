package com.epam.rft.atsy.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * Created by mates on 2015. 11. 11..
 */
@RunWith(Cucumber.class)
@CucumberOptions(format = {"json:target/report.json"}, glue = {"com.epam.rft.atsy.cucumber.settings", "com.epam.rft.atsy.cucumber.util"})
public class SettingsPageIT {
}
