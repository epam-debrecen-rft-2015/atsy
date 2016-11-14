package com.epam.rft.atsy.cucumber.util;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

public class Navigation {

  private static final String DEFAULT_LOCALE = "hu";
  private WebDriver driver;

  public Navigation(WebDriver driver) {
    this.driver = driver;
  }

  public void logout() {
    this.driver.get("http://localhost:8080/atsy/logout");
  }

  public void goToLoginPage(String locale) {
    String localeParameter = "";
    if(!StringUtils.isBlank(locale)) {
      localeParameter = "?locale=" + locale.trim();
    }
    this.driver.get("http://localhost:8080/atsy/login" + localeParameter);
  }

  public void goToLoginPage() {
    this.goToLoginPage(DEFAULT_LOCALE);
  }

}
