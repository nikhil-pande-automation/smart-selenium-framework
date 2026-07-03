package com.nikhil.tests.hooks;

import com.nikhil.framework.config.ConfigReader;
import com.nikhil.framework.driver.DriverFactory;
import com.nikhil.framework.logger.LoggerFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.Logger;

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

    // LOGGER Uppercase because every class will use exactly this pattern, private static final fields are constants by convention.
    private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        LOGGER.info("Starting Scenario : {}", scenario.getName());
        DriverFactory.initializeDriver(ConfigReader.getInstance().getBrowser());
    }

    @After
    public void tearDown(Scenario scenario) {
        LOGGER.info("Finished Scenario : {}", scenario.getName());
        DriverFactory.quitDriver();
    }

}
