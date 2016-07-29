package com.epam.rft.atsy.cucumber.candidate;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import com.epam.rft.atsy.cucumber.util.DriverProvider;
import com.epam.rft.atsy.cucumber.welcome.CandidateTableRow;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CandidateCreationStepDefs {

  private static final String
      CANDIDATE_CREATION_URL =
      "http://localhost:8080/atsy/secure/candidate";

  private static final String WELCOME_PAGE_URL = "http://localhost:8080/atsy/secure/welcome";

  private static final String DESCRIPTION_ID = "description";
  private static final String NAME_ID = "name";
  private static final String EMAIL_ID = "email";
  private static final String PHONE_ID = "phone";
  private static final String REFERER_ID = "referer";
  private static final String LANGUAGE_SKILL_ID = "languageSkill";
  private static final String SAVE_BUTTON_ID = "save-button";

  private static final String EMAIL_SUFFIX = "@epam.com";

  private static List<CandidateTableRow> candidateTableRows;

  @Given("^other candidates data collected$")
  public void other_candidates_data_collected() {
    WebDriverWait wait = DriverProvider.wait(getDriver());
    wait.until(presenceOfElementLocated(By.id("candidates_table")));

    List<WebElement>
        webElements =
        getDriver().findElement(By.id("candidates_table"))
            .findElements(By.cssSelector("tbody > tr[data-index]"));

    candidateTableRows = new ArrayList<>();
    for (WebElement element : webElements) {
      List<WebElement> columns = element.findElements(By.tagName("td"));
      String name = columns.get(0).getText();
      String email = columns.get(1).getText();
      String phone = columns.get(2).getText();
      String position = columns.get(3).getText();

      candidateTableRows.add(new CandidateTableRow(name, email, phone, position));
    }
  }

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
    WebDriverWait webDriverWait = DriverProvider.wait(getDriver());
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
    assertTrue(candidateTableRows.stream().anyMatch(c -> c.getName().endsWith(arg1)));
  }

  @Given("^another candidate's e-mail address is \"([^\"]*)\"$")
  public void another_candidate_s_e_mail_address_is(String arg1) throws Throwable {
    assertTrue(candidateTableRows.stream().anyMatch(c -> c.getEmail().equals(arg1)));
  }

  @Given("^another candidate's phone number is \"([^\"]*)\"$")
  public void another_candidate_s_phone_number_is(String arg1) throws Throwable {
    assertTrue(candidateTableRows.stream().anyMatch(c -> c.getPhone().equals(arg1)));
  }

  @Then("^a \"([^\"]*)\" message is shown under the phone number field$")
  public void a_message_is_shown_under_the_phone_number_field(String arg1) throws Throwable {
    String selector = "#phoneDiv > div > div > ul > li:nth-child(1)";
    WebDriverWait webDriverWait = DriverProvider.wait(getDriver());
    webDriverWait.until(presenceOfElementLocated(By.cssSelector(selector)));
    assertEquals(arg1, getDriver().findElement(By.cssSelector(selector)).getText());
  }

  @Given("^the user enters name longer than (\\d+) characters$")
  public void the_user_enters_name_longer_than_characters(int arg1) throws Throwable {
    String longName = RandomStringUtils.randomAlphabetic(arg1 + 1);
    assertTrue(longName.length() > arg1);
    JavascriptExecutor executor = (JavascriptExecutor) getDriver();
    executor.executeScript(
        "document.getElementById('" + NAME_ID + "').setAttribute('maxlength', '" + String
            .valueOf(longName.length()) + "')");

    getDriver().findElement(By.id(NAME_ID)).sendKeys(longName);
  }

  @Given("^the user enters e-mail address longer than (\\d+) characters$")
  public void the_user_enters_e_mail_address_longer_than_characters(int arg1) throws Throwable {
    String longEmail = RandomStringUtils.randomAlphabetic(arg1) + EMAIL_SUFFIX;
    assertTrue(longEmail.length() > arg1);
    JavascriptExecutor executor = (JavascriptExecutor) getDriver();
    executor.executeScript(
        "document.getElementById('" + EMAIL_ID + "').setAttribute('maxlength', '" + String
            .valueOf(longEmail.length()) + "')");
    getDriver().findElement(By.id(EMAIL_ID)).sendKeys(longEmail);
  }

  @Given("^the user enters phone number longer than (\\d+) characters$")
  public void the_user_enters_phone_number_longer_than_characters(int arg1) throws Throwable {
    String longPhoneNumber = RandomStringUtils.randomNumeric(arg1 + 1);
    assertTrue(longPhoneNumber.length() > arg1);
    JavascriptExecutor executor = (JavascriptExecutor) getDriver();
    executor.executeScript(
        "document.getElementById('" + PHONE_ID + "').setAttribute('maxlength', '" + String
            .valueOf(longPhoneNumber.length()) + "')");
    getDriver().findElement(By.id(PHONE_ID)).sendKeys(longPhoneNumber);
  }

  @Given("^the user enters the place where the candidate has heard about the company longer than (\\d+) characters$")
  public void the_user_enters_the_place_where_the_candidate_has_heard_about_the_company_longer_than_characters(
      int arg1) throws Throwable {
    String longReferer = RandomStringUtils.randomAlphabetic(arg1 + 1);
    assertTrue(longReferer.length() > arg1);
    JavascriptExecutor executor = (JavascriptExecutor) getDriver();
    executor.executeScript(
        "document.getElementById('" + REFERER_ID + "').setAttribute('maxlength', '" + String
            .valueOf(longReferer.length()) + "')");
    getDriver().findElement(By.id(REFERER_ID)).sendKeys(longReferer);
  }

  @Then("a \"([^\"]*)\" message appears")
  public void a_message_appears(String arg1) {
    String selector = "#field-messages > li";
    WebDriverWait webDriverWait = DriverProvider.wait(getDriver());
    List<WebElement>
        listings =
        webDriverWait.until(presenceOfAllElementsLocatedBy(By.cssSelector(selector)));
    assertTrue(listings.size() > 0);
    assertTrue(listings.stream().map(li -> li.getText()).anyMatch(text -> text.equals(arg1)));
  }
}