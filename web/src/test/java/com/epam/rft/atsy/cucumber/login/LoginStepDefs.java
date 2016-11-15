package com.epam.rft.atsy.cucumber.login;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;

import com.epam.rft.atsy.cucumber.util.Navigation;
import com.epam.rft.atsy.cucumber.welcome.CandidatesPage;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginStepDefs {

  private Navigation navigation;
  private LoginPage loginPage;
  private CandidatesPage candidatesPage;

  @Before
  public void initialize() {
    this.navigation = new Navigation(getDriver());
    this.loginPage = new LoginPage(getDriver());
    this.candidatesPage = new CandidatesPage(getDriver());
  }

  @Given("^the user is on the login page$")
  public void onLoginPage() {
    this.navigation.logout();
    this.navigation.goToLoginPage();
  }

  @Given("^the user opens the login page$")
  public void openLoginPage() {
    this.navigation.goToLoginPage();
  }

  @When("^the user enters username (.*)$")
  public void userEntersUsernameUser(String userName) {
    this.loginPage.enterName(userName);
  }

  @When("^the user enters password (.*)$")
  public void userEntersPasswordPassword(String password) {
    this.loginPage.enterPassword(password);
  }

  @When("^the user clicks on Bejelentkezés button$")
  public void bejelentkezesButtonClicked() {
    this.loginPage.clickOnLoginButton();
  }

  @Then("^the Candidates page should appear$")
  public void candidatesPageAppears() {
    this.candidatesPage.validateCandidatesTableAppearance();
  }

  @Then("^(.*) message should appear$")
  public void messageAppearance(String message) {
    this.loginPage.validateGlobalMessage(message);
  }

  @Then("^(.*) message should appear above the (.*) field$")
  public void fieldMessageAppearance(String message, String fieldName) {
    this.loginPage.validateFieldErrorMessage(fieldName, message);
  }

  @Then("^the username field should be in focus$")
  public void usernameFieldIsInFocus() {
    this.loginPage.validateUsernameFieldHasFocus();
  }

  @Then("^the browser title should be (.*)$")
  public void browserTitle(String title) {
    this.loginPage.validateBrowserTitle(title);
  }

  @Then("^the user should get epam logo$")
  public void theUserGetsEpamLogo() throws Throwable {
    this.loginPage.validateLogoAppearance();
  }

  @Then("^the user should get (.*) field$")
  public void theUserGetsUsernameField(String fieldId) throws Throwable {
    this.loginPage.validateFieldExistence(fieldId);
  }

  @Then("^the user should see placeholder (.*) in the (.*) field$")
  public void theUserGetFelhasználónévPlaceholderInTheUsernameField(String placeholderText, String fieldId) throws Throwable {
    this.loginPage.validateFieldPlaceHolder(fieldId, placeholderText);
  }

  @Then("^the user should get (.*) button$")
  public void theUserGetsBejelentkezésButton(String buttonLabel) throws Throwable {
    this.loginPage.validateLoginButtonAppearance(buttonLabel);
  }

}