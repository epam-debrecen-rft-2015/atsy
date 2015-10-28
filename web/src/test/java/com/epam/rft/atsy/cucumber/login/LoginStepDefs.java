package com.epam.rft.atsy.cucumber.login;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.epam.rft.atsy.cucumber.util.DriverProvider;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by Ikantik.
 */

public class LoginStepDefs {

    private DriverProvider driverProvider;

    public LoginStepDefs(DriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    @Given("^the user is on the login page")
    public void onLoginPage() {
        driverProvider.getDriver().get("http://localhost:8080/atsy/login?locale=hu");
    }


    @Given("the user enters username (.*)")
    public void userEntersUsernameUser(String userName) {
        driverProvider.getDriver().findElement(By.id("name")).sendKeys(userName);
    }

    @Given("the user enters password (.*)")
    public void userEntersPasswordPassword(String password) {
        driverProvider.getDriver().findElement(By.id("password")).sendKeys(password);
    }

    @When("the user clicks on Bejelentkezés button")
    public void bejelentkezesButtonClicked() {
        driverProvider.getDriver().findElement(By.id("loginButton")).click();
    }

    @Then("the Candidates page appears")
    public void candidatesPageAppears() {
        //assertThat();
    }

    @Then("(.*) message appears")
    public void messageAppearance(String message) {
        WebElement messageElement = driverProvider.getDriver().findElement(By.id("globalMessage"));
        assertThat(messageElement.isDisplayed(), is(true));
        assertThat(messageElement.getText(), is(message));
    }
}
