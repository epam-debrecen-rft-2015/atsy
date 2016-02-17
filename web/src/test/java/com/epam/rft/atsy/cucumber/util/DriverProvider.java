package com.epam.rft.atsy.cucumber.util;

import com.google.common.base.Predicate;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverProvider {
    private static WebDriver driver;

    public synchronized static WebDriver getDriver() {
        if (driver == null) {

            try {
                driver = initiateDriver();
            } finally {
                Runtime.getRuntime().addShutdownHook(
                    new Thread(new BrowserCleanup()));
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
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        wait.until((Predicate<WebDriver>) driver -> (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0"));
    }
}
