package com.epam.rft.atsy.cucumber.candidate.creation;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
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

import java.util.ArrayList;
import java.util.List;
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

  private static final long TIMEOUT = 60L;

  private static List<CandidateTableRow> candidateTableRows;

  @Given("^other candidates data collected$")
  public void other_candidates_data_collected() {
    WebDriverWait webDriverWait = new WebDriverWait(getDriver(), TIMEOUT);

    List<WebElement>
        webElements =
        webDriverWait
            .until(visibilityOfAllElementsLocatedBy(By.cssSelector("tbody > tr[data-index]")));

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

  @When("^the user clicks on the \"([^\"]*)\" button$")
  public void the_user_clicks_on_the_button(String arg1) throws Throwable {
    getDriver().findElement(By.id(SAVE_BUTTON_ID)).click();
  }

  @Then("^a \"([^\"]*)\" message is shown under the \"([^\"]*)\" field$")
  public void a_message_is_shown_under_the_field(String msg, String field) throws Throwable {
    String nameOrPhoneErrorSelector = ".list-unstyled > li:nth-child(1)";
    String phoneErrorSelector = "#phoneDiv > div > div > ul > li:nth-child(1)";
    WebDriverWait webDriverWait = new WebDriverWait(getDriver(), TIMEOUT);
    if (field.equals("phone")) {
      assertTrue(webDriverWait.until(textToBe(By.cssSelector(phoneErrorSelector), msg)));
    } else {
      assertTrue(webDriverWait.until(textToBe(By.cssSelector(nameOrPhoneErrorSelector), msg)));
    }
  }

  @Given("^the user clears field \"([^\"]*)\"$")
  public void the_user_clears_field(String arg1) throws Throwable {
    WebElement element = null;
    if (arg1.equals(EMAIL_ID)) {
      element = getDriver().findElement(By.id(EMAIL_ID));
    } else if (arg1.equals(NAME_ID)) {
      element = getDriver().findElement(By.id(NAME_ID));
    }
    element.clear();
    assertTrue(element.getText().isEmpty());
  }

  @Given("^another candidate's name is \"([^\"]*)\"$")
  public void another_candidate_s_name_is(String arg1) throws Throwable {
    assertTrue(candidateTableRows.stream().anyMatch(c -> c.getName().equals(arg1)));
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
    assertEquals(arg1, getDriver().findElement(By.cssSelector(selector)));
  }

  @Given("^the user enters name longer than (\\d+) characters$")
  public void the_user_enters_name_longer_than_characters(int arg1) throws Throwable {
    String longName = RandomStringUtils.randomAlphabetic(arg1 + 1);
    setMaxLengthAttribute(NAME_ID, longName.length(), getDriver());
    getDriver().findElement(By.id(NAME_ID)).sendKeys(longName);
  }

  @Given("^the user enters e-mail address longer than (\\d+) characters$")
  public void the_user_enters_e_mail_address_longer_than_characters(int arg1) throws Throwable {
    String longEmail = RandomStringUtils.randomAlphabetic(arg1) + EMAIL_SUFFIX;
    setMaxLengthAttribute(EMAIL_ID, longEmail.length(), getDriver());
    getDriver().findElement(By.id(EMAIL_ID)).sendKeys(longEmail);
  }

  @Given("^the user enters phone number longer than (\\d+) characters$")
  public void the_user_enters_phone_number_longer_than_characters(int arg1) throws Throwable {
    String longPhoneNumber = RandomStringUtils.randomNumeric(arg1 + 1);
    setMaxLengthAttribute(PHONE_ID, longPhoneNumber.length(), getDriver());
    getDriver().findElement(By.id(PHONE_ID)).sendKeys(longPhoneNumber);
  }

  @Given("^the user enters the place where the candidate has heard about the company longer than (\\d+) characters$")
  public void the_user_enters_the_place_where_the_candidate_has_heard_about_the_company_longer_than_characters(
      int arg1) throws Throwable {
    String longReferer = RandomStringUtils.randomAlphabetic(arg1 + 1);
    setMaxLengthAttribute(REFERER_ID, longReferer.length(), getDriver());
    getDriver().findElement(By.id(REFERER_ID)).sendKeys(longReferer);
  }

  @Then("a \"([^\"]*)\" message appears")
  public void a_message_appears(String arg1) {
    String selector = "#field-messages > li";
    WebDriverWait webDriverWait = new WebDriverWait(getDriver(), TIMEOUT);
    List<WebElement>
        listings =
        webDriverWait.until(visibilityOfAllElementsLocatedBy(By.cssSelector(selector)));
    assertTrue(listings.size() > 0);
    assertTrue(listings.stream().map(li -> li.getText()).anyMatch(text -> text.equals(arg1)));
  }

  @Then("^a \"([^\"]*)\" message is shown on the top of the page$")
  public void a_message_is_shown_on_the_top_of_the_page(String arg1) {
    String selector = ".panel-heading";
    WebDriverWait webDriverWait = new WebDriverWait(getDriver(), TIMEOUT);
    assertTrue(webDriverWait.until(textToBe(By.cssSelector(selector), arg1)));
  }

  private void setMaxLengthAttribute(String id, int length, WebDriver driver) {
    JavascriptExecutor executor = (JavascriptExecutor) driver;
    executor.executeScript(
        "document.getElementById('" + id + "').setAttribute('maxlength', '" + String.valueOf(length)
            + "')");
  }
}
