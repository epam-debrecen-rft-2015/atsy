package com.epam.rft.atsy.cucumber.util;

import org.openqa.selenium.By;

import cucumber.api.java.en.Given;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;

public class CommonStepDefs {

  @Given("The user signed in")
  public void loggedIn() {
    getDriver().get("http://localhost:8080/atsy/login?locale=hu");
    getDriver().findElement(By.id("name")).sendKeys("test");
    getDriver().findElement(By.id("password")).sendKeys("pass3");
    getDriver().findElement(By.id("loginButton")).click();
  }
}
