package com.epam.rft.atsy.cucumber.candidate.modification;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;
import static com.epam.rft.atsy.cucumber.util.DriverProvider.waitForAjax;
import static com.epam.rft.atsy.cucumber.util.DriverProvider.waitForPageLoadAfter;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import com.epam.rft.atsy.cucumber.util.DriverProvider;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CandidateModificationStepDefs {

  private static final String NAME_ID = "name";
  private static final String EMAIL_ID = "email";
  private static final String PHONE_ID = "phone";
  private static final String REFERER_ID = "referer";
  private static final String EMAIL_SUFFIX = "@epam.com";
  private static final String MODIFY_BUTTON_ID = "enableModify";
  private static final String NAME_ERRORS_DIV_ID = "name-errors";
  private static final String EMAIL_ERRORS_DIV_ID = "email-errors";

  private static final String PHONE_ERRORS_DIV_ID = "phone-errors";
  private static final String FIRST_CHILD_OF_UNORDERED_LIST = "> ul > li:nth-child(1)";
  private static final String PANEL_HEADING_SELECTOR = ".panel-heading";

  private static final String FIELD_MESSAGES_SELECTOR = "#field-messages > li";
  private static final String TABLE_ROW_SELECTOR = "tbody > tr[data-index]";

  private static final String QUOTED_STRING_PATTERN = "\"([^\"]*)\"";

  private List<CandidateEntity> existingCandidates;

  @Given("^the following existing candidates:$")
  public void the_following_existing_candidates(List<CandidateEntity> candidates) {
    this.existingCandidates = candidates;
  }

  @Given(
      "^the user is on the Candidate profile page of the candidate " + QUOTED_STRING_PATTERN + "$")
  public void the_user_is_on_the_Candidate_profile_page_of_the_candidate(String candidateName) {
    List<WebElement>
        webElements =
        DriverProvider.wait(getDriver())
            .until(visibilityOfAllElementsLocatedBy(By.cssSelector(TABLE_ROW_SELECTOR)));

    //find the candidate that needs to be edited, then click the ref link
    for (WebElement element : webElements) {
      List<WebElement> columns = element.findElements(By.tagName("td"));
      String email = columns.get(1).getText();
      //name is not unique, but phone or email is, which ensures we navigate to the right candidate
      if (existingCandidates.stream()
          .anyMatch(c -> c.getEmail().equals(email) && c.getName().equals(candidateName))) {
        waitForPageLoadAfter(event ->
            columns.get(columns.size() - 1).findElement(By.cssSelector("a > i")).click());
        break;
      }
    }

    //now we are on the candidate creation page, lets click modify to start editing
    getDriver().findElement(By.id(MODIFY_BUTTON_ID)).click();
    waitForAjax();
  }

  @When("^the user clicks on the " + QUOTED_STRING_PATTERN + " button$")
  public void the_user_clicks_on_the_button(String text) {
    List<WebElement> buttons = getDriver().findElements(By.tagName("button"));
    WebElement
        element =
        DriverProvider.wait(getDriver()).until(elementToBeClickable(
            buttons.stream().filter(button -> button.getText().equals(text)).findFirst().get()));
    element.click();
    waitForAjax();
  }

  private void clearThenFillAnInputField(By locator, String input) {
    WebElement element = getDriver().findElement(locator);
    element.clear();
    element.sendKeys(input);
  }

  private void waitForAMessageToBeVisible(By locator, String expectedMessage) {
    WebElement elem = DriverProvider.wait(getDriver()).until(visibilityOfElementLocated(locator));
    assertEquals(expectedMessage, elem.getText());
  }


  @Then("^a " + QUOTED_STRING_PATTERN + " message appears under the " + QUOTED_STRING_PATTERN
      + " field$")
  public void a_message_appears_under_the_field(String errorMessage, String field) {
    switch (field) {
      case "name":
        waitForAMessageToBeVisible(
            By.cssSelector("#" + NAME_ERRORS_DIV_ID + FIRST_CHILD_OF_UNORDERED_LIST), errorMessage);
        break;
      case "email":
        waitForAMessageToBeVisible(
            By.cssSelector("#" + EMAIL_ERRORS_DIV_ID + FIRST_CHILD_OF_UNORDERED_LIST),
            errorMessage);
        break;
      case "phone":
        waitForAMessageToBeVisible(
            By.cssSelector("#" + PHONE_ERRORS_DIV_ID + FIRST_CHILD_OF_UNORDERED_LIST),
            errorMessage);
        break;
    }
  }

  @Then("^a " + QUOTED_STRING_PATTERN + " message appears$")
  public void a_message_appears(String errorMessage) {
    //duplicate email/phone scenario
    //hint: error message appears in the heading
    waitForAMessageToBeVisible(By.cssSelector(PANEL_HEADING_SELECTOR), errorMessage);
  }

  @And("^the user enters name " + QUOTED_STRING_PATTERN + "$")
  public void the_user_enters_name(String name) {
    clearThenFillAnInputField(By.id(NAME_ID), name);
  }

  @And("^the user enters e-mail address " + QUOTED_STRING_PATTERN + "$")
  public void the_user_enters_e_mail_address(String email) {
    clearThenFillAnInputField(By.id(EMAIL_ID), email);
  }

  @And("^the user enters phone number " + QUOTED_STRING_PATTERN + "$")
  public void the_user_enters_phone_number(String phone) {
    clearThenFillAnInputField(By.id(PHONE_ID), phone);
  }

  private void waitForAMessageToAppearInTheKnockoutJsListing(String errorMessage) {
    //error messages provided by KnockoutJS as a listing
    //there can be multiple messages
    List<WebElement>
        listings =
        DriverProvider.wait(getDriver())
            .until(visibilityOfAllElementsLocatedBy(By.cssSelector(FIELD_MESSAGES_SELECTOR)));
    assertTrue(listings.size() > 0);
    assertTrue(
        listings.stream().map(li -> li.getText()).anyMatch(text -> text.equals(errorMessage)));
  }

  @Then("^a " + QUOTED_STRING_PATTERN + " message appears in the listing$")
  public void a_message_appears_in_the_listing(String errorMessage) {
    waitForAMessageToAppearInTheKnockoutJsListing(errorMessage);
  }

  private void setMaxLengthAttribute(String id, int length, WebDriver driver) {
    JavascriptExecutor executor = (JavascriptExecutor) driver;
    executor.executeScript(
        "document.getElementById('" + id + "').setAttribute('maxlength', '" + String.valueOf(length)
            + "')");
  }

  @And("^the user enters a valid " + QUOTED_STRING_PATTERN + " longer than " + QUOTED_STRING_PATTERN
      + " characters$")
  public void the_user_enters_valid_input_longer_than(String field, int length) {
    switch (field) {
      case "name":
        String name = RandomStringUtils.randomAlphabetic(length + 1);
        setMaxLengthAttribute(NAME_ID, name.length(), getDriver());
        clearThenFillAnInputField(By.id(NAME_ID), name);
        break;
      case "email":
        String email = RandomStringUtils.randomAlphabetic(length) + EMAIL_SUFFIX;
        setMaxLengthAttribute(EMAIL_ID, email.length(), getDriver());
        clearThenFillAnInputField(By.id(EMAIL_ID), email);
        break;
      case "phone":
        String phoneNumber = RandomStringUtils.randomNumeric(length + 1);
        setMaxLengthAttribute(PHONE_ID, phoneNumber.length(), getDriver());
        clearThenFillAnInputField(By.id(PHONE_ID), phoneNumber);
        break;
      case "referer":
        String longReferer = RandomStringUtils.randomAlphabetic(length + 1);
        setMaxLengthAttribute(REFERER_ID, longReferer.length(), getDriver());
        clearThenFillAnInputField(By.id(REFERER_ID), longReferer);
        break;
    }
  }

  @Given(
      "^the user enters a phone number which doesn't match " + QUOTED_STRING_PATTERN + " pattern$")
  public void the_user_enters_a_phone_number_which_doesn_t_match_digit_pattern(String pattern) {
    String str = RandomStringUtils.randomAlphabetic(11);
    Pattern pat = Pattern.compile(Pattern.quote(pattern));
    Matcher matcher = pat.matcher(str);
    if (!matcher.matches()) {
      clearThenFillAnInputField(By.id(PHONE_ID), str);
    }
  }

  @Given("^the user enters an invalid email address " + QUOTED_STRING_PATTERN + "$")
  public void the_user_enters_an_invalid_email_address(String invalidEmail) {
    clearThenFillAnInputField(By.id(EMAIL_ID), invalidEmail);
  }

}
