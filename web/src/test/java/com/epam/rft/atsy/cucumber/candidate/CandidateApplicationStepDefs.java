package com.epam.rft.atsy.cucumber.candidate;

import com.epam.rft.atsy.cucumber.util.DriverProvider;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

public class CandidateApplicationStepDefs {
  private static WebDriver webDriver = DriverProvider.getDriver();

  private static final String APPLICATIONS_TABLE = "applications_table";
  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy. MM. dd. HH:mm:ss");


  @Given("^the user on an existing candidates page$")
  public void the_user_on_an_existing_candidates_page() throws Throwable {
    webDriver.get("http://localhost:8080/atsy/secure/candidate/3");
  }

  @And("^there are more than one applications exist for the candidate$")
  public void there_are_more_than_one_applications_exist_for_the_candidate() throws Throwable {
    Integer rowNumber = getNumberOfRowsInTable(APPLICATIONS_TABLE);
    assertThat(rowNumber, greaterThan(1));
  }

  @Then("^the Application list displays all the applications ordered by last modification date and time descending$")
  public void the_application_list_displays_all_the_applications_ordered_by_last_modification_date_and_time_descending()
      throws Throwable {

  }

  @And("^each application has the values of creation date and time, last modification date and time, position, state$")
  public void each_application_has_the_values_of_creation_date_and_time_last_modification_date_and_time_position_state() throws Throwable {

  }

  private Integer getNumberOfRowsInTable(String tableId) {
    WebElement table = webDriver.findElement(By.id(tableId));
    return table.findElements(By.tagName("tr")).size();
  }

}
