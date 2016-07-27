package com.epam.rft.atsy.cucumber.util;


import com.google.common.base.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.TimeUnit;

public class FluentWaitUtil {

  public static WebElement fluentWait(WebDriver webDriver, final By locator) {
    Wait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
        .withTimeout(30, TimeUnit.SECONDS)
        .pollingEvery(5, TimeUnit.SECONDS)
        .ignoring(NoSuchElementException.class);

    WebElement webElement = wait.until(new Function<WebDriver, WebElement>() {
      public WebElement apply(WebDriver driver) {
        return driver.findElement(locator);
      }
    });
    return webElement;
  }
}
