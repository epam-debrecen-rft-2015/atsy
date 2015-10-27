package com.epam.rft.atsy.cucumber.login;

import com.epam.rft.atsy.cucumber.util.DriverProvider;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;

import static org.junit.Assert.assertThat;

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

    @Given("^the user is on the login page")
    public void onLoginPage(){
        driverProvider.getDriver().get("http://localhost:8080/atsy/login");
    }

    @Given("his username is \"user\"")
    public void usernameIsUser(){
        //TODO
    }

    @Given("his password is \"password\"")
    public void passwordIsPassword(){
        //TODO
    }

    @Given("the user enters username \"user\"")
    public void userEntersUsernameUser(){
        driverProvider.getDriver().findElement(By.id("name")).sendKeys("user");
    }

    @Given("the user enters password \"password\"")
    public void userEntersPasswordPassword(){
        driverProvider.getDriver().findElement(By.id("password")).sendKeys("password");
    }

    @When("the user clicks on Bejelentkezés button")
    public void bejelentkezesButtonClicked(){
        driverProvider.getDriver().findElement(By.id("loginButton")).click();
    }

    @Then("the Candidates page appears")
    public void candidatesPageAppears(){
        //assertThat();
    }
}
