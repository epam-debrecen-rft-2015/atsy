package com.epam.rft.atsy.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * Created by mates on 2015. 11. 11..
 */
@RunWith(Cucumber.class)
@CucumberOptions(format = {"json:target/report.json"},features = {"com.epam.rft.atsy.cucumber.Positions.feature"}, glue = {"com.epam.rft.atsy.cucumber.settings.positions", "com.epam.rft.atsy.cucumber.util"})
public class PositionsIT {
}
