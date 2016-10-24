package com.epam.rft.atsy.cucumber.settings.positions;

import com.epam.rft.atsy.cucumber.util.DriverProvider;
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

import cucumber.api.PendingException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PositionsStepDefs {
  private DriverProvider driverProvider;
  private String positionsListContents;
  private String positionName;

  public PositionsStepDefs(DriverProvider driverProvider) {
        this.driverProvider = driverProvider;
  }

  // Anita: ez nem szerepelt a feature fájlban úh innen is kivettem
  /*
  @Given("^the user is on an Atsy page$")
  public void the_user_is_on_an_Atsy_page() throws Throwable {
    assertThat(getDriver().getCurrentUrl(), containsString("secure"));
  }
  */

  // Anita: ez innen ki lett törölve a feature fájlból mert felesleges
  /*
  @Given("^the options screen appears$")
  public void the_options_screen_appears() throws Throwable {
    WebDriverWait wait = new WebDriverWait(getDriver(), 5);
    wait.until(presenceOfElementLocated(By.id("positions_link")));
    assertThat(getDriver().findElement(By.id("settings")).isDisplayed(), is(true));
  }
  */

  // Anita: ez innen ki lett törölve a feature fájlból mert felesleges
  /*
  @Given("^the positions screen appears$")
  public void the_positions_screen_appears() throws Throwable {
    WebDriverWait wait = new WebDriverWait(getDriver(), 5);
    wait.until(presenceOfElementLocated(By.id("positions")));
    assertThat(getDriver().findElement(By.id("settings")).isDisplayed(), is(true));
    assertThat(getDriver().findElement(By.id("positions")).getTagName(), is("table"));
  }
  */

  // Anita: ez innen ki lett törölve a feature fájlból mert felesleges
  /*
  @Given("^the user in on the \"([^\"]*)\" page$")
  public void the_user_in_on_the_page(String arg1) throws Throwable {
    the_options_screen_appears();
  }
  */

  @Given("^the list is saved$")
  public void the_list_saved() throws Throwable {
    positionsListContents =
            getDriver().findElement(By.cssSelector("table#positions tbody")).getText();
  }

  @When("^the user clicks on Mentés button$")
  public void the_mentes_button_clicked() throws Throwable {
    getDriver().findElement(By.cssSelector("#position-form .btn-success")).click();
  }

  @When("^the user clicks on Mégsem button$")
  public void the_megsem_button_clicked() throws Throwable {
    getDriver().findElement(By.cssSelector("#position-form .btn-danger")).click();
  }

  @When("^the user enters (.*) into the title$")
  public void user_enters_into_the_title(String title) throws Throwable {
    getDriver().findElement(By.id("position_name")).clear();
    getDriver().findElement(By.id("position_name")).sendKeys(title);
  }

  @When("^the user clicks on Edit button on a position$")
  public void the_Edit_button_clicked_on_a_position() throws Throwable {
    List<WebElement> rows = getDriver().findElements(By.cssSelector("table#positions tbody tr"));
    WebElement firstRow = rows.get(0);
    positionName = firstRow.getText();
    firstRow.findElement(By.cssSelector("a.edit")).click();
  }

  @When("^the user clicks on (.*) tab link$")
  public void theUserClicksOnTabLink(String tab) throws Throwable {
      switch (tab) {
          case "Pozíciók":
              driverProvider.getDriver().findElement(By.id("positions_link")).click();
              break;
          case "Csatornák":
              driverProvider.getDriver().findElement(By.id("channels_link")).click();
              break;
          case "Jelszó-változtatás":
              driverProvider.getDriver().findElement(By.id("password_link")).click();
              break;
        }
    }

  @Then("^the list filled with positions should appear on the page$")
  public void the_list_filled_with_positions_appears_on_the_page() throws Throwable {
    int elementCount = getDriver().findElements(By.cssSelector("table#positions tbody tr")).size();
    assertThat(elementCount, greaterThan(0));
  }

  @Then("^the title should be cleared$")
  public void the_title_field_cleared() throws Throwable {
    String nameValue = getDriver().findElement(By.id("position_name")).getAttribute("value");
    assertThat(nameValue, isEmptyOrNullString());
  }

  @Then("^the position called (.*) should appear in the list of positions$")
  public void the_new_position_appears_in_the_list_of_positions(String name) throws Throwable {
    waitForAjax();
    assertThat(getDriver().findElement(By.cssSelector("table#positions tbody")).getText(),
         containsString(name));
  }

  @Then("^(.*) message should appear$")
  public void error_message_appears_message(String message) throws Throwable {
    WebDriverWait wait = new WebDriverWait(getDriver(), 15);
    wait.until(visibilityOfElementLocated(By.cssSelector("#position-form #errorMessageForCreating")));
    assertThat(getDriver().findElement(By.cssSelector("#position-form #errorMessageForCreating")).getText(),
        containsString(message));
  }

  @Then("^the list of positions should left unchanged$")
  public void the_list_of_positions_left_unchanged() throws Throwable {
    assertThat(getDriver().findElement(By.cssSelector("table#positions tbody")).getText(),
        is(positionsListContents));
  }

  @Then("^the title field should be filled$")
  public void the_title_field_filled() throws Throwable {
    String nameValue = getDriver().findElement(By.id("position_name")).getAttribute("value");
    assertThat(nameValue, is(positionName));
  }

  @Then("^the list of positions should be changed$")
  public void the_list_of_positions_changed() throws Throwable {
    waitForAjax();
    assertThat(getDriver().findElement(By.cssSelector("table#positions tbody")).getText(),
        is(not(containsString(positionName))));
  }

  @Then("^the user should get (.*) title$")
  public void theUserGetsPageTitle(String title) throws Throwable {
    WebElement element = driverProvider.getDriver().findElement(By.cssSelector("h1"));
    assertThat(element.getText(), is(title));
    assertThat(element.isDisplayed(), is(true));
  }

  @Then("^the user should get (.*) tab link$")
  public void theUserGetsTabLink(String tab) throws Throwable {
     WebElement element;
     switch (tab) {
         case "Pozíciók":
             element = driverProvider.getDriver().findElement(By.id("positions_link"));
             assertThat(element.getText(), is("Pozíciók"));
             assertThat(element.isDisplayed(), is(true));
             break;
         case "Csatornák":
             element = driverProvider.getDriver().findElement(By.id("channels_link"));
             assertThat(element.getText(), is("Csatornák"));
             assertThat(element.isDisplayed(), is(true));
             break;
         case "Jelszó-változtatás":
             element = driverProvider.getDriver().findElement(By.id("password_link"));
             assertThat(element.getText(), is("Jelszó változtatás"));
             assertThat(element.isDisplayed(), is(true));
             break;
     }
  }

  @Then("^the user should get (.*) table$")
  public void settingsTableIsShown(String tab) throws Throwable {
      WebElement element;
      switch (tab) {
          case "Pozíciók":
              element = driverProvider.getDriver().findElement(By.id("positions"));
              assertThat(element.isDisplayed(), is(true));
              break;
          case "Csatornák":
              element = driverProvider.getDriver().findElement(By.id("channels"));
              assertThat(element.isDisplayed(), is(true));
              break;
      }
  }

  @Then("^the user should get password change form$")
  public void passwordChangeFormIsShown() throws Throwable {
      WebElement element= driverProvider.getDriver().findElement(By.id("pw-form"));
      assertThat(element.isDisplayed(), is(true));
  }

  @Then("^the user should get (.*) subtitle$")
  public void theUserShouldGetTitleOnThePositionsPage(String title) throws Throwable {
      WebElement element= driverProvider.getDriver().findElement(By.cssSelector("legend"));
      assertThat(element.getText(), is(title));
      assertThat(element.isDisplayed(), is(true));
  }

  @Then("^the user should get position label$")
  public void theUserGetsPositionLabel() throws Throwable {
      WebElement element= driverProvider.getDriver().findElement(By.cssSelector("label"));
      assertThat(element.getText(), is("Pozíció neve:"));
      assertThat(element.isDisplayed(), is(true));
  }

  @Then("^the user should get position field$")
  public void theUserGetsPositionField() throws Throwable {
      WebElement element= driverProvider.getDriver().findElement(By.id("position_name"));
      assertThat(element.isDisplayed(), is(true));
    }

  @Then("^the user should get (.*) button$")
  public void theUserGetsButton(String button) throws Throwable {
      WebElement element;
      switch (button) {
          case "Mentés":
              element = driverProvider.getDriver().findElement(By.cssSelector("button.btn.btn-success"));
              assertThat(element.isDisplayed(), is(true));
              assertThat(element.getText(), is(button));
              break;
          case "Mégsem":
              element = driverProvider.getDriver().findElement(By.cssSelector("button.btn.btn-danger"));
              assertThat(element.isDisplayed(), is(true));
              assertThat(element.getText(), is(button));
              break;
      }
  }


}