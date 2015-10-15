package com.epam.rft.atsy.cucumber.login;

import com.epam.rft.atsy.cucumber.util.DriverProvider;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;

/**
 * Created by Ikantik.
 */

public class LoginStepDefs {

    private DriverProvider driverProvider;

    public LoginStepDefs(DriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    @When("^I'm opening (.*)")
    public void openingThePage(String url) {
        driverProvider.getDriver().get(url);
    }

    @Then("^I'm happy")
    public void i_am_happy() {
        driverProvider.getDriver().findElement(By.tagName("body"));
    }
}
