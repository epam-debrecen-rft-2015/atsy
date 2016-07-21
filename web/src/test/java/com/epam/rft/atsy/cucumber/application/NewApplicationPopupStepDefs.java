package com.epam.rft.atsy.cucumber.application;

import com.epam.rft.atsy.cucumber.util.DriverProvider;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class NewApplicationPopupStepDefs {

  private final WebDriver webDriver = DriverProvider.getDriver();

  @Given("^the user is on a Candidate A profile page$")
  public void the_user_is_on_a_candidate_A_profile_page() throws Throwable {
    webDriver.get("http://localhost:8080/atsy/secure/candidate/1");
  }

  @When("^the user clicks on the Új jelentkezés hozzáadása button$")
  public void the_user_clicks_on_the_add_application_button() throws Throwable {
    webDriver.findElement(By.id("add_application_button")).click();
  }

  @Then("^the Application page appears$")
  public void the_application_page_appers() throws Throwable {
    assertThat(webDriver.getCurrentUrl(), equalTo("http://localhost:8080/atsy/secure/application?candidateId=1"));
  }

  @And("^the New application popup appears$")
  public void the_new_application_popup_appears() throws Throwable {
    assertThat(webDriver.findElement(By.id("position")).isDisplayed(), is(true));
    assertThat(webDriver.findElement(By.id("channel")).isDisplayed(), is(true));
    assertThat(webDriver.findElement(By.id("description")).isDisplayed(), is(true));
    assertThat(webDriver.findElement(By.id("cancel_button")).isDisplayed(), is(true));
    assertThat(webDriver.findElement(By.id("save_button")).isDisplayed(), is(true));
  }

  @And("^the user can select a position from a dropdown$")
  public void the_user_can_select_a_position_from_a_dropdown() throws Throwable {
    assertThat(webDriver.findElements(By.id("position")).size(), greaterThan(0));
  }

  @And("^the user can select a source of application from the list$")
  public void the_user_can_select_a_source_of_application_from_the_list() throws Throwable {
    assertThat(webDriver.findElements(By.id("channel")).size(), greaterThan(0));
  }

  @And("^the user can add a comment$")
  public void the_user_can_add_a_comment() throws Throwable {
    assertThat(webDriver.findElement(By.id("description")).isEnabled(), is(true));
  }

  @And("^the user can submit the new application$")
  public void the_user_can_submit_the_new_application() throws Throwable {
    assertThat(webDriver.findElement(By.id("save_button")).isEnabled(), is(true));
  }


}
