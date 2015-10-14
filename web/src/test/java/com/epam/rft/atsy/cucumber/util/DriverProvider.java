package com.epam.rft.atsy.cucumber.util;

import cucumber.api.java.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Created by Ikantik.
 */

public class DriverProvider {
    private static final String DRIVER_PROPERTY_KEY = "webdriver";
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
        return "firefox".equals(System.getProperty(DRIVER_PROPERTY_KEY)) ? new FirefoxDriver() : new HtmlUnitDriver();
    }


    @After
    public void tearDown() {
        driver.close();
    }
}
