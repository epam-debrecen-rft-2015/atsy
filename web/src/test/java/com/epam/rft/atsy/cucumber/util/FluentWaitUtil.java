package com.epam.rft.atsy.cucumber.util;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.TimeUnit;

public class FluentWaitUtil {

  public static WebElement fluentWait(final WebDriver webDriver, final By locator){
    Wait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
        .withTimeout(10, TimeUnit.SECONDS)
        .pollingEvery(1, TimeUnit.SECONDS)
        .ignoring(NoSuchElementException.class);

    return wait.until(driver -> driver.findElement(locator));
  }
}
