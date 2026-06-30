package com.nikhil.tests.hooks;

import com.nikhil.framework.config.ConfigReader;
import com.nikhil.framework.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

/**
 * Purpose:
 * Manages browser lifecycle for every Scenario.
 * <p>
 * Responsibilities:
 * - Launch browser before Scenario.
 * - Close browser after Scenario.
 * <p>
 * Why Hooks instead of BaseTest?
 * Cucumber executes Scenarios, not TestNG test methods.
 * Therefore browser lifecycle belongs inside Hooks.
 */

public class Hooks {

    @Before
    public void setUp() {
        DriverFactory.initializeDriver(ConfigReader.getInstance().getBrowser());
    }

    @After
    public void tearDown(){
        DriverFactory.quitDriver();
    }

}
