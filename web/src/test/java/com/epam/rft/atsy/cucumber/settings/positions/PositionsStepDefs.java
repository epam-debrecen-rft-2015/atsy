package com.epam.rft.atsy.cucumber.settings.positions;

import static com.epam.rft.atsy.cucumber.util.DriverProvider.getDriver;
import static com.epam.rft.atsy.cucumber.util.DriverProvider.waitForAjax;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PositionsStepDefs {
  private String positionsListContents;
  private String positionName;

  @And("^the user is on an Atsy page$")
  public void the_user_is_on_an_Atsy_page() throws Throwable {
    assertThat(getDriver().getCurrentUrl(), containsString("secure"));
  }

  @When("^the Beállítások menu point clicked$")
  public void the_button_clicked() throws Throwable {
    getDriver().findElement(By.id("settings_link")).click();
  }

  @And("^the options screen appears$")
  public void the_options_screen_appears() throws Throwable {
    WebDriverWait wait = new WebDriverWait(getDriver(), 5);
    wait.until(presenceOfElementLocated(By.id("positions_link")));
    assertThat(getDriver().findElement(By.id("settings")).isDisplayed(), is(true));
  }

  @And("^the positions link is clicked$")
  public void the_positions_link_is_clicked() throws Throwable {
    getDriver().findElement(By.id("positions_link")).click();
  }

  @And("^the positions screen appears$")
  public void the_positions_screen_appears() throws Throwable {
    WebDriverWait wait = new WebDriverWait(getDriver(), 5);
    wait.until(presenceOfElementLocated(By.id("positions")));
    assertThat(getDriver().findElement(By.id("settings")).isDisplayed(), is(true));
    assertThat(getDriver().findElement(By.id("positions")).getTagName(), is("table"));
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

  @When("^the Mentés button clicked$")
  public void the_mentes_button_clicked() throws Throwable {
    getDriver().findElement(By.cssSelector("#position-form .btn-success")).click();
  }

  @When("^the Mégsem button clicked$")
  public void the_megsem_button_clicked() throws Throwable {
    getDriver().findElement(By.cssSelector("#position-form .btn-danger")).click();
  }

  @Then("^the title field cleared$")
  public void the_title_field_cleared() throws Throwable {
    String nameValue = getDriver().findElement(By.id("position_name")).getAttribute("value");
    assertThat(nameValue, isEmptyOrNullString());
  }

  @And("^user enters \"([^\"]*)\" into the title$")
  public void user_enters_into_the_title(String title) throws Throwable {
    getDriver().findElement(By.id("position_name")).clear();
    getDriver().findElement(By.id("position_name")).sendKeys(title);
  }

  @Then("^the new position called (.*) appears in the list of positions$")
  public void the_new_position_appears_in_the_list_of_positions(String name) throws Throwable {
    waitForAjax();
    assertThat(getDriver().findElement(By.cssSelector("table#positions tbody")).getText(),
        containsString(name));
  }

  @Then("^error message appears (.*)$")
  public void error_message_appears_message(String message) throws Throwable {
    WebDriverWait wait = new WebDriverWait(getDriver(), 15);
    wait.until(visibilityOfElementLocated(By.cssSelector("#position-form .error-message")));
    assertThat(getDriver().findElement(By.cssSelector("#position-form .error-message")).getText(),
        containsString(message));
  }

  @And("^the list saved$")
  public void the_list_saved() throws Throwable {
    positionsListContents =
        getDriver().findElement(By.cssSelector("table#positions tbody")).getText();
  }

  @Then("^the list of positions left unchanged$")
  public void the_list_of_positions_left_unchanged() throws Throwable {

    assertThat(getDriver().findElement(By.cssSelector("table#positions tbody")).getText(),
        is(positionsListContents));
  }

  @When("^the Edit button clicked on a position$")
  public void the_Edit_button_clicked_on_a_position() throws Throwable {
    List<WebElement> rows = getDriver().findElements(By.cssSelector("table#positions tbody tr"));
    WebElement firstRow = rows.get(0);
    positionName = firstRow.getText();
    firstRow.findElement(By.cssSelector("a.edit")).click();
  }

  @Then("^the title field filled$")
  public void the_title_field_filled() throws Throwable {
    String nameValue = getDriver().findElement(By.id("position_name")).getAttribute("value");
    assertThat(nameValue, is(positionName));
  }

  @Then("^the list of positions changed$")
  public void the_list_of_positions_changed() throws Throwable {
    waitForAjax();
    assertThat(getDriver().findElement(By.cssSelector("table#positions tbody")).getText(),
        is(not(containsString(positionName))));
  }
}
