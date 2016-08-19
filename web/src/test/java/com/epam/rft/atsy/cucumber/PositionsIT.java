package com.epam.rft.atsy.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format = {"json:target/report.json", "pretty"}, features = {
    "src/test/resources/com/epam/rft/atsy/cucumber/Positions.feature"}, glue = {
    "com.epam.rft.atsy.cucumber.settings.positions", "com.epam.rft.atsy.cucumber.util"})
public class PositionsIT {
}
