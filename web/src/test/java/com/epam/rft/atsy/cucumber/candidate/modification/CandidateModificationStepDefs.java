package com.epam.rft.atsy.cucumber.candidate.modification;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;
import static com.epam.rft.atsy.cucumber.util.DriverProvider.waitForAjax;
import static com.epam.rft.atsy.cucumber.util.DriverProvider.waitForPageLoadAfter;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CandidateModificationStepDefs {

  private static final long TIMEOUT = 60L;

  private static final String WELCOME_PAGE = "http://localhost:8080/atsy/secure/welcome";

  private static final String NAME_ID = "name";
  private static final String EMAIL_ID = "email";
  private static final String PHONE_ID = "phone";
  private static final String REFERER_ID = "referer";
  private static final String EMAIL_SUFFIX = "@epam.com";

  private List<CandidateEntity> existingCandidates;

  @Given("^the following existing candidates:$")
  public void the_following_existing_candidates(List<CandidateEntity> candidates)
      throws Throwable {
    this.existingCandidates = candidates;
  }

  @Given("^the user is on the Candidate profile page of the candidate \"([^\"]*)\"$")
  public void the_user_is_on_the_Candidate_profile_page_of_the_candidate(String candidateName)
      throws Throwable {
    //go to the welcome page and scrape the existingCandidates from the table
    waitForPageLoadAfter(event -> getDriver().get(WELCOME_PAGE));
    List<WebElement>
        webElements =
        new WebDriverWait(getDriver(), TIMEOUT)
            .until(visibilityOfAllElementsLocatedBy(By.cssSelector("tbody > tr[data-index]")));

    //find the candidate that needs to be edited, then click the ref link
    for (WebElement element : webElements) {
      List<WebElement> columns = element.findElements(By.tagName("td"));
      String email = columns.get(1).getText();
      //name is not unique, but phone or email is, which ensures we navigate to the right candidate
      if (existingCandidates.stream()
          .anyMatch(c -> c.getEmail().equals(email) && c.getName().equals(candidateName))) {
        waitForPageLoadAfter(
            event -> columns.get(columns.size() - 1).findElement(By.cssSelector("a > i")).click());
        break;
      }
    }

    //now we are on the candidate creation page, lets click modify to start editing
    the_user_clicks_on_the_button("Módosítás");
  }

  @When("^the user clicks on the \"([^\"]*)\" button$")
  public void the_user_clicks_on_the_button(String text) throws Throwable {
    //reminder: this might not find "buttons" created by Bootstrap, since they are not
    //button tags, only divs with links
    List<WebElement> buttons = getDriver().findElements(By.tagName("button"));
    WebDriverWait webDriverWait = new WebDriverWait(getDriver(), TIMEOUT);
    WebElement
        elem =
        webDriverWait.until(elementToBeClickable(
            buttons.stream().filter(button -> button.getText().equals(text)).findFirst().get()));
    elem.click();
    waitForAjax();
  }

  private void clearThenFillAnInputField(By locator, String input) {
    WebElement element = getDriver().findElement(locator);
    element.clear();
    element.sendKeys(input);
  }

  private void waitForAMessageToBeVisible(By locator, String expectedMessage) {
    WebDriverWait webDriverWait = new WebDriverWait(getDriver(), TIMEOUT);
    WebElement elem = webDriverWait.until(visibilityOfElementLocated(locator));
    assertEquals(expectedMessage, elem.getText());
  }

  @Then("^a \"([^\"]*)\" message appears under the name field$")
  public void a_message_appears_under_the_name_field(String errorMessage) throws Throwable {
    String selector = "#nameDiv > div > div > ul > li";
    waitForAMessageToBeVisible(By.cssSelector(selector), errorMessage);
  }

  @Then("^a \"([^\"]*)\" message appears under the email address field$")
  public void a_message_appears_under_the_email_address_field(String errorMessage)
      throws Throwable {
    String selector = "#emailDiv > div > span > ul > li";
    waitForAMessageToBeVisible(By.cssSelector(selector), errorMessage);
  }

  @Then("^a \"([^\"]*)\" message appears under the phone number field$")
  public void a_message_appears_under_the_phone_number_field(String errorMessage) throws Throwable {
    String selector = "#phoneDiv > div > div > ul > li";
    waitForAMessageToBeVisible(By.cssSelector(selector), errorMessage);
  }

  @Then("^a \"([^\"]*)\" message appears$")
  public void a_message_appears(String errorMessage) throws Throwable {
    //duplicate email/phone scenario
    //hint: error message appears in the heading
    String selector = "div.panel-heading";
    waitForAMessageToBeVisible(By.cssSelector(selector), errorMessage);
  }

  @Given("^the user enters name \"([^\"]*)\"$")
  public void the_user_enters_name(String name) throws Throwable {
    clearThenFillAnInputField(By.id(NAME_ID), name);
  }

  @Given("^the user enters e-mail address \"([^\"]*)\"$")
  public void the_user_enters_e_mail_address(String email) throws Throwable {
    clearThenFillAnInputField(By.id(EMAIL_ID), email);
  }

  @Given("^the user enters phone number \"([^\"]*)\"$")
  public void the_user_enters_phone_number(String phone) throws Throwable {
    clearThenFillAnInputField(By.id(PHONE_ID), phone);
  }

  private void waitForAMessageToAppearInTheKnockoutJsListing(String errorMessage) {
    //error messages provided by KnockoutJS as a listing
    //there can be multiple messages
    String selector = "#field-messages > li";
    WebDriverWait webDriverWait = new WebDriverWait(getDriver(), TIMEOUT);
    List<WebElement>
        listings =
        webDriverWait.until(visibilityOfAllElementsLocatedBy(By.cssSelector(selector)));
    assertTrue(listings.size() > 0);
    assertTrue(
        listings.stream().map(li -> li.getText()).anyMatch(text -> text.equals(errorMessage)));
  }

  @Then("^a \"([^\"]*)\" message appears in the listing$")
  public void a_message_appears_in_the_listing(String errorMessage) {
    waitForAMessageToAppearInTheKnockoutJsListing(errorMessage);
  }

  private void setMaxLengthAttributeById(String id, int length, WebDriver driver) {
    JavascriptExecutor executor = (JavascriptExecutor) driver;
    executor.executeScript(
        "document.getElementById('" + id + "').setAttribute('maxlength', '" + String.valueOf(length)
            + "')");
    assertEquals(length, Integer.parseInt(driver.findElement(By.id(id)).getAttribute("maxlength")));
  }

  @Given("^the user enters a name longer than (\\d+) characters$")
  public void the_user_enters_a_name_longer_than_characters(int length) throws Throwable {
    String longName = RandomStringUtils.randomAlphabetic(length + 1);
    setMaxLengthAttributeById(NAME_ID, longName.length(), getDriver());
    clearThenFillAnInputField(By.id(NAME_ID), longName);
  }

  @Given("^the user enters a valid email address longer than (\\d+) characters$")
  public void the_user_enters_a_valid_email_address_longer_than_characters(int length)
      throws Throwable {
    String longEmail = RandomStringUtils.randomAlphabetic(length) + EMAIL_SUFFIX;
    setMaxLengthAttributeById(EMAIL_ID, longEmail.length(), getDriver());
    clearThenFillAnInputField(By.id(EMAIL_ID), longEmail);
  }

  @Given("^the user enters a valid phone number longer than (\\d+) characters$")
  public void the_user_enters_a_valid_phone_number_longer_than_characters(int length)
      throws Throwable {
    String longPhoneNumber = RandomStringUtils.randomNumeric(length + 1);
    setMaxLengthAttributeById(PHONE_ID, longPhoneNumber.length(), getDriver());
    clearThenFillAnInputField(By.id(PHONE_ID), longPhoneNumber);
  }

  @Given("^the user enters a valid place longer than (\\d+) characters$")
  public void the_user_enters_a_valid_place_longer_than_characters(int length) throws Throwable {
    String longReferer = RandomStringUtils.randomAlphabetic(length + 1);
    setMaxLengthAttributeById(REFERER_ID, longReferer.length(), getDriver());
    clearThenFillAnInputField(By.id(REFERER_ID), longReferer);
  }

  @Given("^the user enters a phone number which doesn't match \"(.)*\" pattern$")
  public void the_user_enters_a_phone_number_which_doesn_t_match_digit_pattern(String pattern)
      throws Throwable {
    String str = RandomStringUtils.randomAlphabetic(11);
    Pattern pat = Pattern.compile(Pattern.quote(pattern));
    Matcher matcher = pat.matcher(str);
    if (!matcher.matches()) {
      clearThenFillAnInputField(By.id(PHONE_ID), str);
    }
  }

  @Given("^the user enters an invalid email address \"([^\"]*)\"$")
  public void the_user_enters_an_invalid_email_address(String invalidEmail) throws Throwable {
    clearThenFillAnInputField(By.id(EMAIL_ID), invalidEmail);
  }

}
