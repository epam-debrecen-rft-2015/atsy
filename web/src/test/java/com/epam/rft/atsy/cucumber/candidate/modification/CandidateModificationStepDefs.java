package com.epam.rft.atsy.cucumber.candidate;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;
import static com.epam.rft.atsy.cucumber.util.DriverProvider.waitForAjax;
import static com.epam.rft.atsy.cucumber.util.DriverProvider.waitForPageLoadAfter;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import com.epam.rft.atsy.cucumber.util.DriverProvider;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CandidateModificationStepDefs {
  private static final String
      CANDIDATE_CREATION_URL =
      "http://localhost:8080/atsy/secure/candidate";

  private static final String WELCOME_PAGE = "http://localhost:8080/atsy/secure/welcome";

  private static final String NAME_ID = "name";
  private static final String EMAIL_ID = "email";
  private static final String PHONE_ID = "phone";
  private static final String LANGUAGE_SKILL_ID = "languageSkill";
  private static final String SAVE_BUTTON_TEXT = "Mentés";

  @Given("^the following existing candidates:$")
  public void the_following_existing_candidates(List<CandidateEntity> candidates)
      throws Throwable {
    candidates.stream().forEach(candidate -> {
      waitForPageLoadAfter(event -> getDriver().get(CANDIDATE_CREATION_URL));
      fillOutCreationForm(candidate);
      try {
        the_user_clicks_on_the_button(SAVE_BUTTON_TEXT);
      } catch (Throwable throwable) {
        throwable.printStackTrace();
      }
    });
  }

  private void fillOutCreationForm(CandidateEntity candidate) {
    String inputSelector = "input";
    List<WebElement> inputElements = getDriver().findElements(By.cssSelector(inputSelector));
    for (WebElement elem : inputElements) {
      switch (elem.getAttribute("id")) {
        case "name":
          elem.sendKeys(candidate.getName());
          break;
        case "email":
          elem.sendKeys(candidate.getEmail());
          break;
        case "phone":
          elem.sendKeys(candidate.getPhone());
          break;
        case "referer":
          elem.sendKeys(candidate.getReferer());
          break;
        case "description":
          elem.sendKeys(candidate.getDescription());
          break;
      }
    }
    Select languageSkillSelector = new Select(getDriver().findElement(By.id(LANGUAGE_SKILL_ID)));
    languageSkillSelector.selectByVisibleText(String.valueOf(candidate.getLanguageSkill()));
  }

  @Given("^the user is on the Candidate profile page of the candidate \"([^\"]*)\"$")
  public void the_user_is_on_the_Candidate_profile_page_of_the_candidate(String candidateName)
      throws Throwable {
    waitForPageLoadAfter(event -> getDriver().get(WELCOME_PAGE));
    List<WebElement>
        webElements =
        DriverProvider.wait(getDriver())
            .until(visibilityOfAllElementsLocatedBy(By.cssSelector("tbody > tr[data-index]")));

    for (WebElement element : webElements) {
      List<WebElement> columns = element.findElements(By.tagName("td"));
      String name = columns.get(0).getText();
      if (name.equals(candidateName)) {
        waitForPageLoadAfter(
            event -> columns.get(columns.size() - 1).findElement(By.cssSelector("a > i")).click());
        break;
      }
    }

    //now we are on the candidate creation page, lets click modify
    the_user_clicks_on_the_button("Módosítás");
  }

  @Given("^the user enters name \"([^\"]*)\"$")
  public void the_user_enters_name(String name) throws Throwable {
    WebElement elem = getDriver().findElement(By.id(NAME_ID));
    elem.clear();
    elem.sendKeys(name);
  }

  @When("^the user clicks on the \"([^\"]*)\" button$")
  public void the_user_clicks_on_the_button(String text) throws Throwable {
    String buttonSelector = "button.btn";
    List<WebElement> buttons = getDriver().findElements(By.cssSelector(buttonSelector));
    WebDriverWait webDriverWait = DriverProvider.wait(getDriver());
    WebElement
        elem =
        webDriverWait.until(elementToBeClickable(
            buttons.stream().filter(button -> button.getText().equals(text)).findFirst().get()));
    elem.click();
    waitForAjax();
  }

  @Then("^a \"([^\"]*)\" message appears under the name field$")
  public void a_message_appears_under_the_name_field(String errorMessage) throws Throwable {
    String selector = ".list-unstyled > li:nth-child(1)";
    WebDriverWait webDriverWait = DriverProvider.wait(getDriver());
    WebElement elem = webDriverWait.until(visibilityOfElementLocated(By.cssSelector(selector)));
    assertEquals(errorMessage, elem.getText());
  }

  @Given("^the user enters e-mail address \"([^\"]*)\"$")
  public void the_user_enters_e_mail_address(String email) throws Throwable {
    getDriver().findElement(By.id(EMAIL_ID)).sendKeys(email);
  }

  @Then("^a \"([^\"]*)\" message appears under the email address field$")
  public void a_message_appears_under_the_email_address_field(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Then("^a \"([^\"]*)\" message appears$")
  public void a_message_appears(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Given("^the user enters phone number \"([^\"]*)\"$")
  public void the_user_enters_phone_number(String phone) throws Throwable {
    getDriver().findElement(By.id(PHONE_ID)).sendKeys(phone);
  }

  @Given("^the user enters a name longer than (\\d+) characters$")
  public void the_user_enters_a_name_longer_than_characters(int arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Given("^the user enters a valid email address longer than (\\d+) characters$")
  public void the_user_enters_a_valid_email_address_longer_than_characters(int arg1)
      throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Given("^the user enters a valid phone number longer than (\\d+) characters$")
  public void the_user_enters_a_valid_phone_number_longer_than_characters(int arg1)
      throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Then("^a \"([^\"]*)\" message appears under the phone number field$")
  public void a_message_appears_under_the_phone_number_field(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Given("^the user enters a valid place longer than (\\d+) characters$")
  public void the_user_enters_a_valid_place_longer_than_characters(int arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Then("^a \"([^\"]*)\" message appears under the place field$")
  public void a_message_appears_under_the_place_field(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Given("^the user enters a phone number which doesn't match \"(.)*\" pattern$")
  public void the_user_enters_a_phone_number_which_doesn_t_match_digit_pattern(String pattern)
      throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Given("^the user enters an invalid email address \"([^\"]*)\"$")
  public void the_user_enters_an_invalid_email_address(String invalidEmail) throws Throwable {
    getDriver().findElement(By.id(EMAIL_ID)).sendKeys(invalidEmail);
  }

}
