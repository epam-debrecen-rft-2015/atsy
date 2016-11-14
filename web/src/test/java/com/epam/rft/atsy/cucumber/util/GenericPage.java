package com.epam.rft.atsy.cucumber.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class GenericPage {

  protected WebDriver driver;

  public GenericPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(this.driver, this);
  }

  public void validateBrowserTitle(String expectedTitle) {
    assertThat(this.driver.getTitle(), equalTo(expectedTitle));
  }
}
