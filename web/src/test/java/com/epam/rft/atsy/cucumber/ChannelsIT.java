package com.epam.rft.atsy.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(format = {"json:target/report.json"}, features = {"src/test/resources/com/epam/rft/atsy/cucumber/Channels.feature"}, glue = {"com.epam.rft.atsy.cucumber.settings.channels", "com.epam.rft.atsy.cucumber.util"})
public class ChannelsIT {
}
