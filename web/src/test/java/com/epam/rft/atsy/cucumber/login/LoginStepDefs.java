package com.epam.rft.atsy.cucumber.login;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import com.epam.rft.atsy.cucumber.util.DriverProvider;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginStepDefs {

  private DriverProvider driverProvider;

  public LoginStepDefs(DriverProvider driverProvider) {
    this.driverProvider = driverProvider;
  }

  @Given("^the user is on the login page$")
  public void onLoginPage() {
    driverProvider.getDriver().get("http://localhost:8080/atsy/login?locale=hu");
  }

  @When("^the user enters username (.*)$")
  public void userEntersUsernameUser(String userName) {
    driverProvider.getDriver().findElement(By.id("name")).sendKeys(userName);
  }

  @When("^the user enters password (.*)$")
  public void userEntersPasswordPassword(String password) {
    driverProvider.getDriver().findElement(By.id("password")).sendKeys(password);
  }

  @When("^the user clicks on Bejelentkezés button$")
  public void bejelentkezesButtonClicked() {
    driverProvider.getDriver().findElement(By.id("loginButton")).click();
  }

  @Then("^the Candidates page should appear$")
  public void candidatesPageAppears() {
    WebDriverWait wait = new WebDriverWait(getDriver(), 5);
    wait.until(presenceOfElementLocated(By.id("candidates_table")));
  }

  @Then("^(.*) message should appear$")
  public void messageAppearance(String message) {
    WebElement messageElement = driverProvider.getDriver().findElement(By.id("globalMessage"));
    assertThat(messageElement.isDisplayed(), is(true));
    assertThat(messageElement.getText(), is(message));
  }

  @Then("^(.*) message should appear above the (.*) field$")
  public void fieldMessageAppearance(String message, String fieldName) {
    WebElement messageElement = driverProvider.getDriver()
        .findElement(By.id(fieldName))
        .findElement(By.xpath("..")) //parent
        .findElement(By.tagName("span"));
    assertThat(messageElement.isDisplayed(), is(true));
    assertThat(messageElement.getText(), is(message));
  }

  @Then("^the username field should be in focus$")
  public void usernameFieldNotInFocus() {
    assertThat(driverProvider.getDriver().findElement(By.id("name"))
        .equals(driverProvider.getDriver().switchTo().activeElement()), is(true));
  }

  @Then("^the browser title should be (.*)$")
  public void browserTitle(String title) {
    String browserTitle = driverProvider.getDriver().getTitle();
    //assertThat(driverProvider.getDriver().getTitle().equals(title), is(true));
    assertThat(browserTitle, is(title));
  }

  @Then("^the user should get epam logo$")
  public void theUserGetsEpamLogo() throws Throwable {
    WebElement messageElement = driverProvider.getDriver().findElement(By.cssSelector("img.img-rounded"));
    assertThat(messageElement.isDisplayed(), is(true));
  }

  @Then("^the user should get (.*) field$")
  public void theUserGetsUsernameField(String fieldId) throws Throwable {
    WebElement messageElement = driverProvider.getDriver().findElement(By.id(fieldId));
    assertThat(messageElement.isDisplayed(), is(true));
  }

  @Then("^the user should see placeholder (.*) in the (.*) field$")
  public void theUserGetFelhasználónévPlaceholderInTheUsernameField(String placeholderText, String fieldId) throws Throwable {
    String fieldPlaceholderText = driverProvider.getDriver().findElement(By.id(fieldId)).getAttribute("placeholder");
    assertThat(fieldPlaceholderText, is(placeholderText));
  }

  @Then("^the user should get (.*) button$")
  public void theUserGetsBejelentkezésButton(String buttonLabel) throws Throwable {
    WebElement messageElement = driverProvider.getDriver().findElement(By.id("loginButton"));
    assertThat(messageElement.isDisplayed(), is(true));
    assertThat(messageElement.getText(), is(buttonLabel));
  }

}
