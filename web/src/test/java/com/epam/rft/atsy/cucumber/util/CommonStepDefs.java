package com.epam.rft.atsy.cucumber.util;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;
import static com.epam.rft.atsy.cucumber.util.DriverProvider.waitForPageLoadAfter;

import org.openqa.selenium.By;

import cucumber.api.java.en.Given;

public class CommonStepDefs {
  
  @Given("The user signed in")
  public void loggedIn() {
    getDriver().get("http://localhost:8080/atsy/logout");
    getDriver().get("http://localhost:8080/atsy/login?locale=hu");

    getDriver().findElement(By.id("name")).sendKeys("test");
    getDriver().findElement(By.id("password")).sendKeys("pass3");
    waitForPageLoadAfter(event -> getDriver().findElement(By.id("loginButton")).click());
  }
}