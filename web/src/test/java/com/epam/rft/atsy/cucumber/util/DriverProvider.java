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
    private WebDriver driver;

    public WebDriver getDriver() {
        if (driver == null) {
            synchronized (this) {
                if (driver == null) {
                    driver = initiateDriver();
                    Runtime.getRuntime().addShutdownHook(new Thread() {
                        public void run() {
                            driver.close();
                        }
                    });
                }
            }
        }
        return driver;
    }

    private WebDriver initiateDriver() {
        return new FirefoxDriver();
    }

}
