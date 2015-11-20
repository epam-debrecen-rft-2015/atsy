package com.epam.rft.atsy.cucumber.util;

import java.lang.Runtime;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.After;
import cucumber.runtime.*;

/**
 * Created by Ikantik.
 */

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

}
