package com.epam.rft.atsy.cucumber.candidate.creation;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;
import static com.epam.rft.atsy.cucumber.util.DriverProvider.waitForPageLoadAfter;
import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;

import com.epam.rft.atsy.cucumber.welcome.CandidateTableRow;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CandidateCreationStepDefs {

  private static final String
      CANDIDATE_CREATION_URL =
      "http://localhost:8080/atsy/secure/candidate";

  private static final String DESCRIPTION_ID = "description";
  private static final String NAME_ID = "name";
  private static final String EMAIL_ID = "email";
  private static final String PHONE_ID = "phone";
  private static final String REFERER_ID = "referer";
  private static final String LANGUAGE_SKILL_ID = "languageSkill";
  private static final String SAVE_BUTTON_ID = "save-button";
  private static final String NAME_ERRORS_DIV_ID = "name-errors";
  private static final String EMAIL_ERRORS_DIV_ID = "email-errors";
  private static final String PHONE_ERRORS_DIV_ID = "phone-errors";

  private static final String FIRST_CHILD_OF_UNORDERED_LIST = "> ul > li:nth-child(1)";
  private static final String PANEL_HEADING_SELECTOR = ".panel-heading";
  private static final String FIELD_MESSAGES_SELECTOR = "#field-messages > li";

  private static final String QUOTED_STRING_PATTERN = "\"([^\"]*)\"";

  private static final String EMAIL_SUFFIX = "@epam.com";

  private static final long TIMEOUT = 60L;

  private static List<CandidateTableRow> candidates;

  @Given("^there are existing candidates:$")
  public void there_are_existing_candidates(List<CandidateTableRow> candidates) {
    this.candidates = candidates;
  }

  @Given("^the user is on the Candidate creation page$")
  public void the_user_is_on_the_Candidate_creation_page() {
    waitForPageLoadAfter(event -> getDriver().get(CANDIDATE_CREATION_URL));
  }

  @Given("^the candidate details are empty$")
  public void the_candidate_details_are_empty() {
    WebElement descriptionElement = getDriver().findElement(By.id(DESCRIPTION_ID));
    descriptionElement.clear();
    assertTrue(descriptionElement.getText().isEmpty());
  }

  @Given("^the user enters name " + QUOTED_STRING_PATTERN + "$")
  public void the_user_enters_name(String arg1) {
    WebElement element = getDriver().findElement(By.id(NAME_ID));
    element.clear();
    element.sendKeys(arg1);
  }

  @Given("^the user enters e-mail address " + QUOTED_STRING_PATTERN + "$")
  public void the_user_enters_e_mail_address(String arg1) {
    WebElement element = getDriver().findElement(By.id(EMAIL_ID));
    element.clear();
    element.sendKeys(arg1);
  }

  @Given("^the user enters phone number " + QUOTED_STRING_PATTERN + "$")
  public void the_user_enters_phone_number(String arg1) {
    WebElement element = getDriver().findElement(By.id(PHONE_ID));
    element.clear();
    element.sendKeys(arg1);
  }

  @Given(
      "^the user enters the place where the candidate has heard about the company "
          + QUOTED_STRING_PATTERN
          + "$")
  public void the_user_enters_the_place_where_the_candidate_has_heard_about_the_company(
      String arg1) {
    WebElement element = getDriver().findElement(By.id(REFERER_ID));
    element.clear();
    element.sendKeys(arg1);
  }

  @Given("^the user enters the language level " + QUOTED_STRING_PATTERN + "$")
  public void the_user_enters_the_language_level(String arg1) {
    Select select = new Select(getDriver().findElement(By.id(LANGUAGE_SKILL_ID)));
    select.selectByVisibleText(arg1);
  }

  @When("^the user clicks on the " + QUOTED_STRING_PATTERN + " button$")
  public void the_user_clicks_on_the_button(String buttonText) {
    if (buttonText.equals("Mentés")) {
      getDriver().findElement(By.id(SAVE_BUTTON_ID)).click();
    }
  }

  @Then("^a " + QUOTED_STRING_PATTERN + " message is shown under the " + QUOTED_STRING_PATTERN
      + " field$")
  public void a_message_is_shown_under_the_field(String msg, String field) {
    WebDriverWait webDriverWait = new WebDriverWait(getDriver(), TIMEOUT);
    switch (field) {
      case "name":
        assertTrue(webDriverWait.until(
            textToBe(By.cssSelector("#" + NAME_ERRORS_DIV_ID + FIRST_CHILD_OF_UNORDERED_LIST),
                msg)));
        break;
      case "email":
        assertTrue(webDriverWait.until(
            textToBe(By.cssSelector("#" + EMAIL_ERRORS_DIV_ID + FIRST_CHILD_OF_UNORDERED_LIST),
                msg)));
        break;
      case "phone":
        assertTrue(webDriverWait.until(
            textToBe(By.cssSelector("#" + PHONE_ERRORS_DIV_ID + FIRST_CHILD_OF_UNORDERED_LIST),
                msg)));
        break;
    }
  }

  @Given("^the user clears field " + QUOTED_STRING_PATTERN + "$")
  public void the_user_clears_field(String field) {
    WebElement element = null;
    switch (field) {
      case "email":
        element = getDriver().findElement(By.id(EMAIL_ID));
        break;
      case "name":
        element = getDriver().findElement(By.id(NAME_ID));
        break;
    }
    element.clear();
    assertTrue(element.getText().isEmpty());
  }

  @Given("^another candidate's " + QUOTED_STRING_PATTERN + " is " + QUOTED_STRING_PATTERN
      + "$")
  public void another_candidate_s_e_mail_address_is(String field, String actual) {
    if (field.equals("email")) {
      assertTrue(candidates.stream().anyMatch(c -> c.getEmail().equals(actual)));
    } else if (field.equals("phone")) {
      assertTrue(candidates.stream().anyMatch(c -> c.getPhone().equals(actual)));
    }
  }

  @And("^the user enters " + QUOTED_STRING_PATTERN + " longer than " + QUOTED_STRING_PATTERN
      + " characters$")
  public void the_user_enters_input_longer_than(String field, int length) {
    switch (field) {
      case "name":
        String name = RandomStringUtils.randomAlphabetic(length + 1);
        setMaxLengthAttribute(NAME_ID, name.length(), getDriver());
        the_user_enters_name(name);
        break;
      case "email":
        String email = RandomStringUtils.randomAlphabetic(length) + EMAIL_SUFFIX;
        setMaxLengthAttribute(EMAIL_ID, email.length(), getDriver());
        the_user_enters_e_mail_address(email);
        break;
      case "phone":
        String phoneNumber = RandomStringUtils.randomNumeric(length + 1);
        setMaxLengthAttribute(PHONE_ID, phoneNumber.length(), getDriver());
        the_user_enters_phone_number(phoneNumber);
        break;
      case "referer":
        String longReferer = RandomStringUtils.randomAlphabetic(length + 1);
        setMaxLengthAttribute(REFERER_ID, longReferer.length(), getDriver());
        the_user_enters_the_place_where_the_candidate_has_heard_about_the_company(longReferer);
        break;
    }
  }

  @Then("^a " + QUOTED_STRING_PATTERN + " message appears$")
  public void a_message_appears(String arg1) {
    WebDriverWait webDriverWait = new WebDriverWait(getDriver(), TIMEOUT);
    List<WebElement>
        listings =
        webDriverWait
            .until(visibilityOfAllElementsLocatedBy(By.cssSelector(FIELD_MESSAGES_SELECTOR)));
    assertTrue(listings.size() > 0);
    assertTrue(listings.stream().map(WebElement::getText).anyMatch(text -> text.equals(arg1)));
  }

  @Then("^a " + QUOTED_STRING_PATTERN + " message is shown on the top of the page$")
  public void a_message_is_shown_on_the_top_of_the_page(String arg1) {
    WebDriverWait webDriverWait = new WebDriverWait(getDriver(), TIMEOUT);
    assertTrue(webDriverWait.until(textToBe(By.cssSelector(PANEL_HEADING_SELECTOR), arg1)));
  }

  private void setMaxLengthAttribute(String id, int length, WebDriver driver) {
    JavascriptExecutor executor = (JavascriptExecutor) driver;
    executor.executeScript(
        "document.getElementById('" + id + "').setAttribute('maxlength', '" + String.valueOf(length)
            + "')");
  }
}
