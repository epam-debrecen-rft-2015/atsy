package com.epam.rft.atsy.cucumber.application;

import com.epam.rft.atsy.cucumber.util.DriverProvider;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class NewApplicationPopupStepDefs {

  private static WebDriver webDriver = DriverProvider.getDriver();
  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy. MM. dd. HH:mm:ss");

  private static final String POP_UP_CONTENT = "pop_up_content";
  private static final String APPLICATIONS_TABLE = "applications_table";
  private static final String POSITION = "position";
  private static final String CHANNEL = "channel";
  private static final String DESCRIPTION = "description";
  private static final String SAVE_NEW_APPLY_BUTTON = "save_new_apply_button";
  private static final String ADD_APPLICATION_BUTTON = "add_application_button";

  private static final String DEVELOPER_IN_HUN = "Fejlesztő";
  private static final String DIRECT_IN_HUN = "direkt";
  private static final String NEW_APPLY_IN_HUN = "Új jelentkezés";

  private static final Integer COLUMN_ZEROTH = 0;
  private static final Integer COLUMN_FIRST = 1;
  private static final Integer COLUMN_SECOND = 2;
  private static final Integer COLUMN_THIRD = 3;


  @Given("^the user is on a Candidate A profile page$")
  public void the_user_is_on_a_candidate_A_profile_page() throws Throwable {
    webDriver.get("http://localhost:8080/atsy/secure/candidate/1");
  }

  @When("^the user clicks on the Új jelentkezés hozzáadása button$")
  public void the_user_clicks_on_the_add_application_button() throws Throwable {
    webDriver.findElement(By.id(ADD_APPLICATION_BUTTON)).click();
  }

  @Then("^the Application page appears$")
  public void the_application_page_appears$() throws Throwable {
    assertThat(webDriver.getCurrentUrl(), equalTo("http://localhost:8080/atsy/secure/application?candidateId=1"));
  }

  @And("^the New application popup appears$")
  public void the_new_application_popup_appears() throws Throwable {
    assertThat(webDriver.findElement(By.id(POP_UP_CONTENT)).isDisplayed(), is(true));
  }

  @And("^the user can select a position from a dropdown$")
  public void the_user_can_select_a_position_from_a_dropdown() throws Throwable {
    Integer dropdownSize = getOptionsSizeFromDropdown(POSITION);
    assertThat(dropdownSize, greaterThan(1));
  }

  @And("^the user can select a source of application from the list$")
  public void the_user_can_select_a_source_of_application_from_the_list() throws Throwable {
    Integer dropdownSize = getOptionsSizeFromDropdown(CHANNEL);
    assertThat(dropdownSize, greaterThan(1));
  }

  @And("^the user can add a comment$")
  public void the_user_can_add_a_comment() throws Throwable {
    assertThat(webDriver.findElement(By.id(DESCRIPTION)).isEnabled(), is(true));
  }

  @And("^the user can submit the new application$")
  public void the_user_can_submit_the_new_application() throws Throwable {
    assertThat(webDriver.findElement(By.id(SAVE_NEW_APPLY_BUTTON)).isEnabled(), is(true));
  }


  @Given("^the user is on a New application popup of an existing candidate$")
  public void the_user_is_on_a_new_application_popup_of_an_existing_candidates() throws Throwable {
    webDriver.get("http://localhost:8080/atsy/secure/application?candidateId=1");
  }

  @And("^position (.*) exists$")
  public void position_developer_exists(String positionName) throws Throwable {
    List<String> existPositionNameList = convertWebElementsToStringList(webDriver.findElements(By.id(POSITION)));
    assertThat(isContainsAKeyword(positionName, existPositionNameList), is(true));
  }

  @And("^the user selects (.*) position$")
  public void the_user_selects_position(String positionName) throws Throwable {
    Select dropdown = new Select(webDriver.findElement(By.id(POSITION)));
    dropdown.selectByVisibleText(positionName);
  }

  @And("^the user selects direkt source$")
  public void the_user_selects_direct_source() throws Throwable {
    Select dropdown = new Select(webDriver.findElement(By.id(CHANNEL)));
    dropdown.selectByVisibleText(DIRECT_IN_HUN);
  }

  @And("^leaves the comment blank$")
  public void leaves_the_comment_blank() throws Throwable {
    // Do nothing
  }

  @When("^the user clicks on the Jelentkezés mentése button$")
  public void the_user_clicks_on_the_save_new_apply_button() throws Throwable {
    webDriver.findElement(By.id(SAVE_NEW_APPLY_BUTTON)).click();
  }

  @Then("^New application popup disappears$")
  public void new_application_popup_disappears() throws Throwable {
    try {
      webDriver.findElement(By.id(POP_UP_CONTENT));
      // Wrong behaviour
      Assert.fail();
    } catch (NoSuchElementException e) {
      // Expect behaviour
    }
  }

  @And("^the Application page is displayed$")
  public void the_application_page_is_displayed() throws Throwable {
    assertThat(webDriver.getCurrentUrl(), equalTo("http://localhost:8080/atsy/secure/candidate/1"));
  }

  @And("^application is listed with all details$")
  public void application_is_listed_with_details() throws Throwable {
    WebElement table = webDriver.findElement(By.id(APPLICATIONS_TABLE));
    List<WebElement> lastRowInTheTable = getValuesFromTheLastRowFromTheTable(table);

    Date presentDate = currentDateMinus(25);
    String positionName = lastRowInTheTable.get(COLUMN_ZEROTH).getText();
    Date addedDate = SIMPLE_DATE_FORMAT.parse(lastRowInTheTable.get(COLUMN_FIRST).getText());
    Date lastModifiedDate = SIMPLE_DATE_FORMAT.parse(lastRowInTheTable.get(COLUMN_SECOND).getText());
    String stateName = lastRowInTheTable.get(COLUMN_THIRD).getText();

    assertThat(positionName, equalTo(DEVELOPER_IN_HUN));
    assertThat(addedDate, greaterThan(presentDate));
    assertThat(lastModifiedDate, greaterThan(presentDate));
    assertThat(stateName, equalTo(NEW_APPLY_IN_HUN));
  }


  @And("^the user enters comment (.*)$")
  public void the_user_enters_comment(String remark) throws Throwable {
    webDriver.findElement(By.id(DESCRIPTION)).sendKeys(remark);
  }

  @And("^the user doesn't select a position$")
  public void the_user_does_not_select_a_position() throws Throwable {
    // Do nothing
  }

  @Then("^the (.*) message appears under the position selector$")
  public void the_position_message_appears_under_the_position_selector(String positionMessage) throws Throwable {
    String positionDropDownErrorMessage = webDriver.findElement(By.id("position_error")).getText();
    assertThat(positionDropDownErrorMessage, equalTo(positionMessage));
  }

  @And("^the user doesn't select a source$")
  public void the_user_does_not_select_a_source() throws Throwable {
    // Do nothing
  }

  @Then("^the (.*) message appears under the source selector$")
  public void the_application_source_message_appears_under_the_source_selector(String applicationSourceMessage)
      throws Throwable {
    String applicationSourceDropDownErrorMessage = webDriver.findElement(By.id("application_source_error")).getText();
    assertThat(applicationSourceDropDownErrorMessage, equalTo(applicationSourceMessage));
  }


  private Integer getOptionsSizeFromDropdown(String dropdownName) {
    return new Select(webDriver.findElement(By.id(dropdownName))).getOptions().size();
  }

  private List<String> convertWebElementsToStringList(List<WebElement> webElementList) {
    return webElementList.stream().map(w -> w.getText()).collect(Collectors.toList());
  }

  private Boolean isContainsAKeyword(String keyword, List<String> keywordList) {
    return keywordList.stream().anyMatch(k -> k.contains(keyword));
  }

  private List<WebElement> getValuesFromTheLastRowFromTheTable(WebElement table) {
    List<WebElement> allRows = table.findElements(By.tagName("tr"));
    int lastRowIndex = allRows.size() - 1;
    return allRows.get(lastRowIndex).findElements(By.tagName("td"));
  }

  private Date currentDateMinus(long seconds) {
    return Date.from(ZonedDateTime.now().minusSeconds(seconds).toInstant());
  }

}