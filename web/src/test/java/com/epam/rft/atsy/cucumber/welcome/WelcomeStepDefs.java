package com.epam.rft.atsy.cucumber.welcome;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;
import static com.epam.rft.atsy.cucumber.util.DriverProvider.waitForAjax;
import static com.epam.rft.atsy.cucumber.util.DriverProvider.waitForPageLoadAfter;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class WelcomeStepDefs {
  private List<CandidateTableRow> expectedCandidates;

  @When("the user clicks on the Főoldal button")
  public void homeClicked() {
//    waitForPageLoadAfter(
//        driver -> driver.findElement(By.cssSelector(".navbar .navbar-nav :first-child > a"))
//            .click());
    waitForPageLoadAfter(
            driver -> driver.findElement(By.cssSelector(".navbar .navbar-brand .img-rounded"))
                    .click());
  }

  @Then("the Candidates page appears")
  public void candidatesPageAppears() {
    WebDriverWait wait = new WebDriverWait(getDriver(), 5);
    wait.until(presenceOfElementLocated(By.id("candidates_table")));
  }

  @And("the list of candidates appears with the columns: Név, E-Mail, Telefonszám, Pályázott pozíciók")
  public void tableAppears() {
    List<WebElement>
        webElements =
        getDriver().findElements(By.cssSelector("table > thead > tr > th"));
    assertThat(webElements.get(0).getText(), is("Név"));
    assertThat(webElements.get(1).getText(), is("E-Mail cím"));
    assertThat(webElements.get(2).getText(), is("Telefonszám"));
    assertThat(webElements.get(3).getText(), is("Pozíciók"));
    assertThat(webElements.get(4).getText(), is("Műveletek"));
  }

  @Given("there are existing candidates")
  public void candidateExists(List<CandidateTableRow> table) {
    expectedCandidates = table;
  }

  @And("the list of candidates shown in order")
  public void list_of_candidates_shown(List<CandidateTableRow> table) {
    List<WebElement>
        webElements =
        getDriver().findElement(By.id("candidates_table"))
            .findElements(By.cssSelector("tbody > tr[data-index]"));
    assertThat(webElements.size(), is(table.size()));
    for (int index = 0; index < table.size(); index++) {
      CandidateTableRow expectedCandidate = table.get(index);
      List<WebElement> columns = webElements.get(index).findElements(By.tagName("td"));
      assertThat(columns.get(0).getText(), is(expectedCandidate.getName()));
      assertThat(columns.get(1).getText(), is(expectedCandidate.getEmail()));
      assertThat(columns.get(2).getText(), is(expectedCandidate.getPhone()));
      assertThat(columns.get(3).getText(), is(expectedCandidate.getPositions()));
    }
  }

  @When("the user changes the order field to (.*), (.*)")
  public void the_user_changes_the_order_field_to(String field, String order) {
    WebElement
        sortElement =
        getDriver().findElement(By.cssSelector(
            "#candidates_table div.fixed-table-header th[data-field=" + field + "] div.sortable"));

    String currentOrderCss = sortElement.getAttribute("class");

    if (!(currentOrderCss).contains(order)) {
      sortElement.click();
      waitForAjax();
      the_user_changes_the_order_field_to(field, order);
    }
  }

  @And("the list of candidates shown ordered by (.*) as (.*)")
  public void the_list_of_candidates_shown_ordered_by(final String orderField, String order) {
    Comparator<CandidateTableRow>
        fieldComparator =
        new CandidateTableRow.Comparator(orderField, order);
    List<CandidateTableRow> rows = new ArrayList<>(expectedCandidates);
    rows.sort(fieldComparator);
    list_of_candidates_shown(rows);
  }

  @Given("the user fills the search fields")
  public void fillSearchFields() {
    getDriver().findElement(By.id("filter_name")).sendKeys("Candidate");
    getDriver().findElement(By.id("filter_email")).sendKeys("candidate.a");
    getDriver().findElement(By.id("filter_phone")).sendKeys("+3610");
  }

  @When("the user clicks on the Keresés button")
  public void searchClicked() {
    getDriver().findElement(By.cssSelector("#searchButton")).click();
    WebDriverWait wait = new WebDriverWait(getDriver(), 5);
    wait.until(presenceOfElementLocated(By.id("candidates_table")));
  }


  @Then("the list of filtered candidates shown in order")
  public void list_of_filtered_candidates_shown(List<CandidateTableRow> table) {
    List<WebElement>
        webElements =
        getDriver().findElement(By.id("candidates_table"))
            .findElements(By.cssSelector("tbody > tr[data-index]"));
    assertThat(webElements.size(), is(table.size()));
    for (int index = 0; index < table.size(); index++) {
      CandidateTableRow expectedCandidate = table.get(index);
      List<WebElement> columns = webElements.get(index).findElements(By.tagName("td"));
      assertThat(columns.get(0).getText(), is(expectedCandidate.getName()));
      assertThat(columns.get(1).getText(), is(expectedCandidate.getEmail()));
      assertThat(columns.get(2).getText(), is(expectedCandidate.getPhone()));
      assertThat(columns.get(3).getText(), is(expectedCandidate.getPositions()));
    }
  }
}