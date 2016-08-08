package com.epam.rft.atsy.cucumber.application;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;
import static com.epam.rft.atsy.cucumber.util.DriverProvider.waitForPageLoadAfter;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import com.epam.rft.atsy.cucumber.util.DriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
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
      APPLICATIONS_SELECTOR =
      "#applications_table tbody tr[data-index=\"0\"] .edit > i";

  private static final String
      LATEST_STATE_NAME_SELECTOR =
      "#stateList > div.page-header:nth-of-type(1) > h4:nth-child(1)";

  private static final String SAVE_BUTTON_SELECTOR = "#stateList button.btn-success[type=submit]";

  @Given("^a candidate with an application$")
  public void a_candidate_with_an_application() throws Throwable {
    waitForPageLoadAfter(driver -> driver.get("http://localhost:8080/atsy/secure/candidate/3"));
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
      waitUntilElementVisibleThenClick(By.cssSelector(SAVE_BUTTON_SELECTOR), getDriver());
    }

    if (!getDriver().findElement(By.cssSelector(LATEST_STATE_NAME_SELECTOR)).getText()
        .equals(latestStateName)) {

      List<WebElement>
          buttons =
          DriverProvider.wait(getDriver())
              .until(visibilityOfAllElementsLocatedBy(By.cssSelector(BUTTON_SELECTOR)));
      if (!buttons.stream().anyMatch(b -> b.getText().equals(latestStateName))) {
        waitUntilElementVisibleThenClick(By.cssSelector(PAUSE_BUTTON_SELECTOR), getDriver());
        waitUntilElementVisibleThenClick(By.cssSelector(SAVE_BUTTON_SELECTOR), getDriver());
      }

      waitForPageLoadAfter(driver -> driver.findElements(By.cssSelector(
          BUTTON_SELECTOR)).stream().filter(t -> t.getText().equals(latestStateName)).findFirst()
          .ifPresent(x -> x.click()));

      waitUntilElementVisibleThenClick(By.cssSelector(SAVE_BUTTON_SELECTOR), getDriver());
    }
  }

  @When("^the user clicks on \"([^\"]*)\" button$")
  public void the_user_clicks_on_button(String buttonName) throws Throwable {
    List<WebElement> elements = DriverProvider.wait(getDriver())
        .until(visibilityOfAllElementsLocatedBy(By.cssSelector(BUTTON_SELECTOR)));

    waitForPageLoadAfter(
        driver -> elements.stream().filter(t -> t.getText().equals(buttonName)).findFirst()
            .ifPresent(x -> x.click()));

    waitUntilElementVisibleThenClick(By.cssSelector(SAVE_BUTTON_SELECTOR), getDriver());
  }

  @Then("^the latest state became \"([^\"]*)\"$")
  public void the_latest_state_became(String expectedLatestStateName) throws Throwable {
    DriverProvider.wait(getDriver())
        .until(textToBe(By.cssSelector(LATEST_STATE_NAME_SELECTOR), expectedLatestStateName));
    String
        latestStateName =
        getDriver()
            .findElement(By.cssSelector(LATEST_STATE_NAME_SELECTOR))
            .getText();
    assertThat(latestStateName, is(expectedLatestStateName));
  }

  private void waitUntilElementVisibleThenClick(By by, WebDriver driver) {
    WebDriverWait webDriverWait = DriverProvider.wait(driver);
    WebElement element = webDriverWait.until(visibilityOfElementLocated(by));
    waitForPageLoadAfter(e -> element.click());
  }

}
