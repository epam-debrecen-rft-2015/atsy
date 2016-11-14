package com.epam.rft.atsy.cucumber.welcome;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import com.epam.rft.atsy.cucumber.util.GenericPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CandidatesPage extends GenericPage {

  private By candidatesTableId = By.id("candidates_table");

  public CandidatesPage(WebDriver driver) {
    super(driver);
  }

  public void validateCandidatesTableAppearance() {
    WebElement candidatesTable = new WebDriverWait(driver, 5)
        .until(presenceOfElementLocated(this.candidatesTableId));
    assertThat(candidatesTable.isDisplayed(), is(true));
  }
}
