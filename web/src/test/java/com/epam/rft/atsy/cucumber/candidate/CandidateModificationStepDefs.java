package com.epam.rft.atsy.cucumber.candidate;

import com.epam.rft.atsy.cucumber.welcome.CandidateTableRow;

import java.util.List;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CandidateModificationStepDefs {
  private List<CandidateTableRow> existingCandidates;

  @Given("^the following existing candidates:$")
  public void the_following_existing_candidates(List<CandidateTableRow> candidates) {
    existingCandidates = candidates;
  }

  @Given("^the user is on the Candidate profile page of the candidate \"([^\"]*)\"$")
  public void the_user_is_on_the_Candidate_profile_page_of_the_candidate(String arg1)
      throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Given("^the user enters name \"([^\"]*)\"$")
  public void the_user_enters_name(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @When("^the user clicks on the \"([^\"]*)\" button$")
  public void the_user_clicks_on_the_button(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Then("^a \"([^\"]*)\" message appears under the name field$")
  public void a_message_appears_under_the_name_field(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @Given("^the user enters e-mail address \"([^\"]*)\"$")
  public void the_user_enters_e_mail_address(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
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
  public void the_user_enters_phone_number(String arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
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

  @Given("^the user enters an invalid email address$")
  public void the_user_enters_an_invalid_email_address() throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

}
