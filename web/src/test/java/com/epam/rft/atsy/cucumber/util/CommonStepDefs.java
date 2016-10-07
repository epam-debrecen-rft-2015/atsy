package com.epam.rft.atsy.cucumber.util;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;
import static com.epam.rft.atsy.cucumber.util.DriverProvider.waitForPageLoadAfter;

import org.openqa.selenium.By;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import cucumber.api.java.en.Given;

public class CommonStepDefs {

  @Given("The user signed in")
  public void loggedIn() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.isAuthenticated()
        && !(authentication instanceof AnonymousAuthenticationToken)) {
      getDriver().get("http://localhost:8080/atsy/login?locale=hu");
      getDriver().findElement(By.id("name")).sendKeys("test");
      getDriver().findElement(By.id("password")).sendKeys("pass3");
      waitForPageLoadAfter(event -> getDriver().findElement(By.id("loginButton")).click());
    } else {
      getDriver().get("http://localhost:8080/atsy/secure/welcome");
    }
  }
}
