package com.epam.rft.atsy.cucumber.login;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Created by Ikantik.
 */
public class LoginStepDefs {
    WebDriver driver = new HtmlUnitDriver();

    @When("^I'm opening (.*)")
    public void openingThePage(String url) {
        driver.get(url);
    }

    @Then("^I'm happy")
    public void i_am_happy() {
        driver.close();
    }
}
