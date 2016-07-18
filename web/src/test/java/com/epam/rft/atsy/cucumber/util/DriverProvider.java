package com.epam.rft.atsy.cucumber.util;

import com.google.common.base.Predicate;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Consumer;

public class DriverProvider {
    private static WebDriver driver;

    public synchronized static WebDriver getDriver() {
        if (driver == null) {

            try {
                driver = initiateDriver();
            } finally {
                Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
            }
        }
        return driver;
    }

    private static class BrowserCleanup implements Runnable {
        public void run() {
            driver.close();
        }
    }

    private static WebDriver initiateDriver() {
        return new FirefoxDriver();
    }

    public static void waitForAjax() {
        wait(getDriver()).until(
            (Predicate<WebDriver>) driver -> (Boolean) ((JavascriptExecutor) driver)
                .executeScript("return jQuery.active == 0"));
    }

    public static void waitForPageLoadAfter(Consumer<WebDriver> event) {
        WebDriver driver = getDriver();
        WebElement html = driver.findElement(By.tagName("html"));
        event.accept(driver);
        wait(driver).until(isStale(html));
    }

    private static Predicate<WebDriver> isStale(final WebElement html) {
        return driver -> {
            try {
                html.findElement(By.tagName("body"));
                return false;
            } catch (StaleElementReferenceException e) {
                return true;
            }
        };
    }

    public static WebDriverWait wait(WebDriver driver) {
        return new WebDriverWait(driver, 10);
    }
}
