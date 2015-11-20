package com.epam.rft.atsy.cucumber.settings;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author Antal_Kiss on 11/20/2015.
 */
public class SettingsStepDefs {

    @And("^the user is on an Atsy page$")
    public void the_user_is_on_an_Atsy_page() throws Throwable {
        assertThat(getDriver().getCurrentUrl(), containsString("secure"));
    }

    @When("^the Beállítások menu point clicked$")
    public void the_button_clicked() throws Throwable {
        getDriver().findElement(By.id("settings_link")).click();
    }

    @Then("^the options screen appears$")
    public void the_options_screen_appears() throws Throwable {
        WebDriverWait wait = new WebDriverWait(getDriver(), 5);
        wait.until(presenceOfElementLocated(By.id("positions")));
        assertThat(getDriver().findElement(By.id("settings")).isDisplayed(), is(true));
    }

    @Given("^the user in on the \"([^\"]*)\" page$")
    public void the_user_in_on_the_page(String arg1) throws Throwable {
        the_options_screen_appears();
    }

    @Then("^the list filled with positions appears on the page$")
    public void the_list_filled_with_positions_appears_on_the_page() throws Throwable {
        int elementCount = getDriver().findElements(By.cssSelector("table#positions tbody tr")).size();
        assertThat(elementCount, greaterThan(0));
    }

    @When("^the \"([^\"]*)\" button clicked$")
    public void the_button_clicked(String arg1) throws Throwable {
        getDriver().findElement(By.linkText(arg1)).click();
    }

    @Then("^the title field cleared$")
    public void the_title_field_cleared() throws Throwable {
        String nameValue = getDriver().findElement(By.id("position_name")).getText();
        assertThat(nameValue, isEmptyOrNullString());
    }
}
