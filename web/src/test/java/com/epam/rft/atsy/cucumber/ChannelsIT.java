package com.epam.rft.atsy.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by mates on 2015. 11. 11..
 */
@RunWith(Cucumber.class)
@CucumberOptions(format = {"json:target/report.json"},features = {"com.epam.rft.atsy.cucumber.Channels.feature"}, glue = {"com.epam.rft.atsy.cucumber.settings.channels.ChannelsStepDefs", "com.epam.rft.atsy.cucumber.util"})
public class ChannelsIT {
}
