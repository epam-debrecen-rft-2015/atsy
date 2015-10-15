package com.epam.rft.atsy.cucumber.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.After;

/**
 * Created by Ikantik.
 */

public class DriverProvider {
    private WebDriver driver;

    public WebDriver getDriver() {
        if (driver == null) {
            synchronized (this) {
                if (driver == null) {
                    driver = initiateDriver();
                }
            }
        }
        return driver;
    }

    private WebDriver initiateDriver() {
        return new FirefoxDriver();
    }


    @After
    public void tearDown() {
        if (driver != null) {
            driver.close();
        }
    }
}
