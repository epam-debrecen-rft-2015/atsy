package com.epam.rft.atsy.cucumber.alternative.login;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;

import com.epam.rft.atsy.cucumber.login.LoginPage;
import com.epam.rft.atsy.cucumber.spring.CucumberStepConfig;
import com.epam.rft.atsy.cucumber.util.Navigation;
import com.epam.rft.atsy.cucumber.welcome.CandidatesPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@ContextConfiguration(classes = CucumberStepConfig.class)
public class AlternativeLoginStepDefs {

  private LoginPage loginPage;
  private CandidatesPage candidatesPage;
  private Navigation navigation;

  @Autowired
  private Environment env;

  @Before
  public void initialize() {
    this.loginPage = new LoginPage(getDriver());
    this.candidatesPage = new CandidatesPage(getDriver());
    this.navigation = new Navigation(getDriver());
  }

  @Given("^the user is not authenticated$")
  public void theUserIsNotAuthenticated() {
    this.navigation.logout();
  }

  @Given("^the user opens the login page$")
  public void theUserOpensTheLoginPage() {
    this.navigation.goToLoginPage(env.getProperty("login.language"));
  }

  @Then("^the user should see mandatory images$")
  public void theUserShouldSeeMandatoryImages() {
    this.loginPage.validateLogoAppearance();
  }

  @Then("^the user should see login fields$")
  public void theUserShouldSeeLoginFields() {
    this.loginPage.validateUsernameFieldExistence();
    this.loginPage.validateUsernameFieldPlaceHolder(env.getProperty("login.username.placeholder"));
    this.loginPage.validatePasswordFieldExistence();
    this.loginPage.validatePasswordFieldPlaceHolder(env.getProperty("login.password.placeholder"));
  }

  @Then("^the user should see login button$")
  public void theUserShouldSeeLoginButton() {
    this.loginPage.validateLoginButtonAppearance(env.getProperty("login.button.label"));
  }

  @Then("^the focus should be set$")
  public void theFocusShouldBeSet() {
    this.loginPage.validateUsernameFieldHasFocus();
  }

  @Then("^the browser title should be correct$")
  public void theBrowserTitleShouldBeCorrect() {
    this.loginPage.validateBrowserTitle(env.getProperty("browser.title"));
  }

  @When("^the user logs in with valid login details$")
  public void theUserLogsInWithValidLoginDetails() {
    this.loginPage.login(
        env.getProperty("login.valid.username"),
        env.getProperty("login.valid.password")
    );
  }

  @Then("^the user should be logged in$")
  public void theUserShouldBeLoggedIn() {
    this.candidatesPage.validateCandidatesTableAppearance();
  }

  @When("^the user logs in with valid (.*) username$")
  public void theUserLogsInWithValidAllUppercaseUsername(String validUsernameTypes) {
    this.loginPage.login(
        env.getProperty("login.valid.username." + validUsernameTypes),
        env.getProperty("login.valid.password")
    );
  }

  @When("^the user tries to log in with (.*) name and (.*) password$")
  public void theUserTriesToLogInWithNameAndPassword(String usernameType, String passwordType) {
    this.loginPage.login(
        env.getProperty("login." + usernameType + ".username"),
        env.getProperty("login." + passwordType + ".password")
    );
  }

  @Then("^incorrect username or password message should appear$")
  public void incorrectUsernameOrPasswordMessageShouldAppear() {
    this.loginPage.validateGlobalMessage(env.getProperty("login.invalid.credentials.message"));
  }

  @When("^the user tries to log in with (.*) name$")
  public void theUserTriesToLoginWithName(String usernameType) {
    this.loginPage.login(
        env.getProperty("login." + usernameType + ".username"),
        env.getProperty("login.valid.password")
    );
  }

  @Then("^empty username error message appears above username field$")
  public void emptyUsernameErrorMessageAppearsAboveUsernameField() {
    this.loginPage.validateUsernameFieldErrorMessage(env.getProperty("login.empty.username.message"));
  }

  @When("^the user tries to log in with ([^\\s]*) password$")
  public void theUserTriesToLoginWithPassword(String passwordType) {
    this.loginPage.login(
        env.getProperty("login.valid.username"),
        env.getProperty("login." + passwordType + ".password")
    );
  }

  @Then("^empty password error message appears above password field$")
  public void emptyPasswordErrorMessageAppearsAbovePasswordField() {
    this.loginPage.validateFieldErrorMessage("password", env.getProperty("login.empty.password.message"));
  }
}
