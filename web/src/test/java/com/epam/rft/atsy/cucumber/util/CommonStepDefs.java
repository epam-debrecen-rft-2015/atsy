package com.epam.rft.atsy.cucumber.util;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;
import static com.epam.rft.atsy.cucumber.util.DriverProvider.waitForPageLoadAfter;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;

import cucumber.api.java.en.Given;
import org.openqa.selenium.WebElement;

public class CommonStepDefs {


  @Given("The user signed in")
  public void loggedIn() {
    getDriver().get("http://localhost:8080/atsy/login?locale=hu");
    getDriver().findElement(By.id("name")).sendKeys("test");
    getDriver().findElement(By.id("password")).sendKeys("pass3");
    waitForPageLoadAfter(event -> getDriver().findElement(By.id("loginButton")).click());
  }

  @When("^the user clicks on (.*) in the header$")
  public void theUserClicksOnButtonInTheHeader(String button) throws Throwable {
      switch (button) {
          case "epam-logo":
              getDriver().findElement(By.cssSelector("img.img-rounded")).click();
              break;
          case "Beállítások-icon":
              getDriver().findElement(By.id("settings_link")).click();
              break;
          case "Beállítások-text":
              getDriver().findElement(By.linkText("Beállítások")).click();
              break;
          case "Kilépés-icon":
              getDriver().findElement(By.id("logout_link")).click();
              break;
          case "Kilépés-text":
              getDriver().findElement(By.linkText("Kilépés")).click();
              break;
      }

  }

  @Then("^the user should get epam logo in the header$")
  public void theUserGetsEpamLogoInTheHeader() throws Throwable {
      WebElement element = getDriver().findElement(By.cssSelector("img.img-rounded"));
      assertThat(element.isDisplayed(), is(true));
  }

  @Then("^the user should get Beállítások link in the header$")
  public void theUserGetsSettingsLinkInTheHeader() throws Throwable {
      WebElement text_element = getDriver().findElement(By.linkText("Beállítások"));
      assertThat(text_element.getText(), is("Beállítások"));
      assertThat(text_element.isDisplayed(), is(true));
      WebElement icon_element = getDriver().findElement(By.id("settings_link"));
      assertThat(icon_element.isDisplayed(), is(true));
  }

  @Then("^the user should get Kilépés link in the header$")
  public void theUserGetsLogoutLinkInTheHeader() throws Throwable {
     WebElement text_element = getDriver().findElement(By.linkText("Kilépés"));
     assertThat(text_element.getText(), is("Kilépés"));
     assertThat(text_element.isDisplayed(), is(true));
     WebElement icon_element = getDriver().findElement(By.id("logout_link"));
     assertThat(icon_element.isDisplayed(), is(true));
  }

  @Then("^(.*) url should open$")
  public void urlOpens(String page) throws Throwable {
    // TODO
    switch (page) {
      case "welcome":
            assertThat(getDriver().getCurrentUrl(), is("http://localhost:8080/atsy/secure/welcome"));
            break;
      case "candidate-creation":
            assertThat(getDriver().getCurrentUrl(), is("http://localhost:8080/atsy/secure/candidate"));
            break;
      case "settings":
            assertThat(getDriver().getCurrentUrl(), is("http://localhost:8080/atsy/secure/settings"));
            break;
      case "logout":
            assertThat(getDriver().getCurrentUrl(), is("http://localhost:8080/atsy/login?logout"));
            break;
       //the other pages should be put here too
       //warning: some pages have the same URL
    }
  }



}
