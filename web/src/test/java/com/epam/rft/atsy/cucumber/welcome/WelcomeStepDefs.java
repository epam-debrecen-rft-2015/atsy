package com.epam.rft.atsy.cucumber.welcome;

import com.epam.rft.atsy.cucumber.util.DriverProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import com.epam.rft.atsy.service.domain.CandidateDTO;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by mates on 2015. 11. 11..
 */
public class WelcomeStepDefs {

    private DriverProvider driverProvider;
    private List<CandidateDTO> candidateDTOs;
    private List<CandidateTableRow> expectedCandidates;

    public WelcomeStepDefs(DriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    @Given("The user signed in")
    public void loggedIn() {
        driverProvider.getDriver().get("http://localhost:8080/atsy/login?locale=hu");
        driverProvider.getDriver().findElement(By.id("name")).sendKeys("test");
        driverProvider.getDriver().findElement(By.id("password")).sendKeys("pass3");
        driverProvider.getDriver().findElement(By.id("loginButton")).click();
    }

    @When("the user clicks on the Főoldal button")
    public void homeClicked() {
        driverProvider.getDriver().findElement(By.cssSelector(".navbar .navbar-nav :first-child > a")).click();
    }

    @Then("the Candidates page appears")
    public void candidatesPageAppears() {
        WebDriverWait wait = new WebDriverWait(driverProvider.getDriver(), 5);
        wait.until(presenceOfElementLocated(By.id("candidates_table")));
    }

    @And("the list of candidates appears with the columns: Név, E-Mail, Telefonszám, Pályázott pozíciók")
    public void tableAppears() {
        List<WebElement> webElements = driverProvider.getDriver().findElements(By.cssSelector("table > thead > tr > th"));
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
        List<WebElement> webElements = driverProvider.getDriver().findElement(By.id("candidates_table")).findElements(By.cssSelector("tbody > tr[data-index]"));
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

    @And("the user changes the order field to (.*)")
    public void the_user_changes_the_order_field_to(String field) {
        List<WebElement> sortElement = driverProvider.getDriver().findElements(By.cssSelector("#candidates_table div.fixed-table-header th[data-field=" + field + "] div.sortable"));
        sortElement.get(0).click();
    }

    @And("the list of candidates shown ordered by (.*) as (.*)")
    public void the_list_of_candidates_shown_ordered_by(final String orderField, String order) {
        Comparator<CandidateTableRow> fieldComparator = new Comparator<CandidateTableRow>() {
            @Override
            public int compare(CandidateTableRow o1, CandidateTableRow o2) {
                return getFieldValue(o1).compareTo(getFieldValue(o2));
            }

            private String getFieldValue(CandidateTableRow row) {
                String value = "";
                try {
                    Field field = CandidateTableRow.class.getField(orderField);
                    field.setAccessible(true);
                    value = (String) field.get(row);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                return value;
            }
        };
        if ("descending".equals(order)) {
            fieldComparator = fieldComparator.reversed();
        }
        List<CandidateTableRow> rows = new ArrayList<CandidateTableRow>(expectedCandidates);
        rows.sort(fieldComparator);
        list_of_candidates_shown(rows);
    }

    @When("the user clicks on the (.*) header")
    public void clickFieldHeader(String field) {
        List<WebElement> webElements = driverProvider.getDriver().findElements(By.cssSelector("thead > tr > th"));
        for (WebElement webElement : webElements) {
            if (webElement.getText().equals(field)) {
                webElement.click();
            }
        }
    }
}
