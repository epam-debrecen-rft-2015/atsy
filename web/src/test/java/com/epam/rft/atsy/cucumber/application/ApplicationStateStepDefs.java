package com.epam.rft.atsy.cucumber.application;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;
import static com.epam.rft.atsy.cucumber.util.DriverProvider.waitForAjax;
import static com.epam.rft.atsy.cucumber.util.DriverProvider.waitForPageLoadAfter;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ApplicationStateStepDefs {

  private static final String
      BUTTON_SELECTOR =
      "#body > div.button-panel > form.form-inline  div.btn-group > a.btn";

  private static final String
      PAUSE_BUTTON_SELECTOR =
      "#body > div.button-panel > form.form-inline  div.btn-group > a.btn-warning";

  private static final String
      CANDIDATES_SELECTOR =
      "#candidates tbody tr[data-index=\"2\"] .edit > i";

  private static final String
      APPLICATIONS_SELECTOR =
      "#applications_table tbody tr[data-index=\"0\"] .edit > i";

  private static final String
      LATEST_STATE_NAME_SELECTOR =
      "#stateList > div.page-header:nth-of-type(1) > h4:nth-child(1)";

  private static final String SAVE_BUTTON_SELECTOR = "#stateList button.btn-success[type=submit]";

  @Given("^a candidate with an application$")
  public void a_candidate_with_an_application() throws Throwable {
    waitForAjax();
    waitForPageLoadAfter(driver -> driver.findElement(By.cssSelector(CANDIDATES_SELECTOR)).click());
  }

  @And("^the user is on the application page of the candidate$")
  public void the_user_is_on_the_application_page_of_the_candidate() throws Throwable {
    waitForPageLoadAfter(
        driver -> driver.findElement(By.cssSelector(APPLICATIONS_SELECTOR)).click());
  }

  @And("^the application has the latest state \"([^\"]*)\"$")
  public void the_application_has_the_latest_state(String latestStateName) throws Throwable {
    try {
      getDriver().findElement(By.cssSelector("#body > div.button-panel"));
    } catch (NoSuchElementException e) {
      waitForPageLoadAfter(driver -> driver.get(getDriver().getCurrentUrl() + "&state=newstate"));
      waitForPageLoadAfter(
          driver -> driver.findElement(By.cssSelector(SAVE_BUTTON_SELECTOR))
              .click());
    }

    if (!getDriver()
        .findElement(By.cssSelector(LATEST_STATE_NAME_SELECTOR))
        .getText().equals(latestStateName)) {

      if (!getDriver().findElements(By.cssSelector(BUTTON_SELECTOR)).stream()
          .anyMatch(b -> b.getText().equals(latestStateName))) {
        waitForPageLoadAfter(
            driver -> driver.findElement(By.cssSelector(PAUSE_BUTTON_SELECTOR)).click());
      }

      waitForPageLoadAfter(driver -> driver.findElements(By.cssSelector(
          BUTTON_SELECTOR)).stream().filter(t -> t.getText().equals(latestStateName)).findFirst()
          .ifPresent(x -> x.click()));

      waitForPageLoadAfter(
          driver -> driver.findElement(By.cssSelector(SAVE_BUTTON_SELECTOR))
              .click());
    }
  }

  @When("^the user clicks on \"([^\"]*)\" button$")
  public void the_user_clicks_on_button(String buttonName) throws Throwable {
    waitForPageLoadAfter(driver -> driver.findElements(By.cssSelector(
        BUTTON_SELECTOR)).stream().filter(t -> t.getText().equals(buttonName)).findFirst()
        .ifPresent(x -> x.click()));

    waitForPageLoadAfter(
        driver -> driver.findElement(By.cssSelector(SAVE_BUTTON_SELECTOR))
            .click());
  }

  @Then("^the latest state became \"([^\"]*)\"$")
  public void the_latest_state_became(String expectedLatestStateName) throws Throwable {
    new WebDriverWait(getDriver(), 15)
        .until(textToBe(By.cssSelector(LATEST_STATE_NAME_SELECTOR), expectedLatestStateName));
    String
        latestStateName =
        getDriver()
            .findElement(By.cssSelector(LATEST_STATE_NAME_SELECTOR))
            .getText();
    assertThat(latestStateName, is(expectedLatestStateName));
  }

}
