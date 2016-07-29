package com.epam.rft.atsy.cucumber.candidate;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.PendingException;
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
  private static final String EMAIL_SUFFIX = "@epam.com";


  @Given("^the user is on the Candidate creation page$")
  public void the_user_is_on_the_Candidate_creation_page() throws Throwable {
    getDriver().get(CANDIDATE_CREATION_URL);
  }

  @Given("^the candidate details are empty$")
  public void the_candidate_details_are_empty() throws Throwable {
    WebElement descriptionElement = getDriver().findElement(By.id(DESCRIPTION_ID));
    descriptionElement.clear();

    assertTrue(descriptionElement.getText().isEmpty());
  }

  @Given("^the user enters name \"([^\"]*)\"$")
  public void the_user_enters_name(String arg1) throws Throwable {
    getDriver().findElement(By.id(NAME_ID)).sendKeys(arg1);
  }

  @Given("^the user enters e-mail address \"([^\"]*)\"$")
  public void the_user_enters_e_mail_address(String arg1) throws Throwable {
    getDriver().findElement(By.id(EMAIL_ID)).sendKeys(arg1);
  }

  @Given("^the user enters phone number \"([^\"]*)\"$")
  public void the_user_enters_phone_number(String arg1) throws Throwable {
    getDriver().findElement(By.id(PHONE_ID)).sendKeys(arg1);
  }

  @Given("^the user enters the place where the candidate has heard about the company \"([^\"]*)\"$")
  public void the_user_enters_the_place_where_the_candidate_has_heard_about_the_company(String arg1)
      throws Throwable {
    getDriver().findElement(By.id(REFERER_ID)).sendKeys(arg1);
  }

  @Given("^the user enters the language level \"([^\"]*)\"$")
  public void the_user_enters_the_language_level(String arg1) throws Throwable {
    Select select = new Select(getDriver().findElement(By.id(LANGUAGE_SKILL_ID)));
    select.selectByVisibleText(arg1);
  }

  @Given("^the user clears field name$")
  public void the_user_clears_field_name() throws Throwable {
    WebElement nameElement = getDriver().findElement(By.id(NAME_ID));
    nameElement.clear();

    assertTrue(nameElement.getText().isEmpty());
  }

  @When("^the user clicks on the \"([^\"]*)\" button$")
  public void the_user_clicks_on_the_button(String arg1) throws Throwable {
    getDriver().findElement(By.id(SAVE_BUTTON_ID)).click();
  }

  @Then("^a \"([^\"]*)\" message is shown under the name field$")
  public void a_message_is_shown_under_the_name_field(String arg1) throws Throwable {
    String selector = ".list-unstyled > li:nth-child(1)";
    WebDriverWait webDriverWait = new WebDriverWait(getDriver(), 5);
    webDriverWait.until(presenceOfElementLocated(By.cssSelector(selector)));

    assertEquals(arg1, getDriver().findElement(By.cssSelector(selector)).getText());
  }

  @Given("^the user clears field email$")
  public void the_user_clears_field_email() throws Throwable {
    WebElement emailElement = getDriver().findElement(By.id(EMAIL_ID));
    emailElement.clear();

    assertTrue(emailElement.getText().isEmpty());
  }

  @Then("^a \"([^\"]*)\" message is shown under the email field$")
  public void a_message_is_shown_under_the_email_field(String arg1) throws Throwable {
    a_message_is_shown_under_the_name_field(arg1);
  }

  @Given("^another candidate's name is \"([^\"]*)\"$")
  public void another_candidate_s_name_is(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Given("^another candidate's e-mail address is \"([^\"]*)\"$")
  public void another_candidate_s_e_mail_address_is(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Given("^another candidate's phone number is \"([^\"]*)\"$")
  public void another_candidate_s_phone_number_is(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Then("^a \"([^\"]*)\" message is shown under the email address field$")
  public void a_message_is_shown_under_the_email_address_field(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Then("^a \"([^\"]*)\" message is shown under the phone number field$")
  public void a_message_is_shown_under_the_phone_number_field(String arg1) throws Throwable {
    String selector = "#phoneDiv > div > div > ul > li:nth-child(1)";
    WebDriverWait webDriverWait = new WebDriverWait(getDriver(), 5);
    webDriverWait.until(presenceOfElementLocated(By.cssSelector(selector)));
    assertEquals(arg1, getDriver().findElement(By.cssSelector(selector)).getText());
  }

  @Given("^the user enters name longer than (\\d+) characters$")
  public void the_user_enters_name_longer_than_characters(int arg1) throws Throwable {
    String longName = RandomStringUtils.randomAlphabetic(arg1 + 1);
    assertTrue(longName.length() > arg1);
    getDriver().findElement(By.id(NAME_ID)).sendKeys(longName);
  }

  @Given("^the user enters e-mail address longer than (\\d+) characters$")
  public void the_user_enters_e_mail_address_longer_than_characters(int arg1) throws Throwable {
    String longEmail = RandomStringUtils.randomAlphabetic(arg1) + EMAIL_SUFFIX;
    assertTrue(longEmail.length() > arg1);
    getDriver().findElement(By.id(EMAIL_ID)).sendKeys(longEmail);
  }

  @Given("^the user enters phone number longer than (\\d+) characters$")
  public void the_user_enters_phone_number_longer_than_characters(int arg1) throws Throwable {
    String longPhoneNumber = RandomStringUtils.randomNumeric(arg1 + 1);
    assertTrue(longPhoneNumber.length() > arg1);
    getDriver().findElement(By.id(PHONE_ID)).sendKeys(longPhoneNumber);
  }

  @Given("^the user enters the place where the candidate has heard about the company longer than (\\d+) characters$")
  public void the_user_enters_the_place_where_the_candidate_has_heard_about_the_company_longer_than_characters(
      int arg1) throws Throwable {
    String longReferer = RandomStringUtils.randomAlphabetic(arg1 + 1);
    assertTrue(longReferer.length() > arg1);
    getDriver().findElement(By.id(REFERER_ID)).sendKeys(longReferer);
  }

  @Then("^a \"([^\"]*)\" message is shown under the referer field$")
  public void a_message_is_shown_under_the_referer_field(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }
}
