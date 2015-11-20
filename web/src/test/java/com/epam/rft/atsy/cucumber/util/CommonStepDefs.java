package com.epam.rft.atsy.cucumber.util;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;

import org.openqa.selenium.By;

import cucumber.api.java.en.Given;

/**
 * @author Antal_Kiss on 11/20/2015.
 */
public class CommonStepDefs {

    @Given("The user signed in")
    public void loggedIn() {
        getDriver().get("http://localhost:8080/atsy/login?locale=hu");
        getDriver().findElement(By.id("name")).sendKeys("test");
        getDriver().findElement(By.id("password")).sendKeys("pass3");
        getDriver().findElement(By.id("loginButton")).click();
    }
}
