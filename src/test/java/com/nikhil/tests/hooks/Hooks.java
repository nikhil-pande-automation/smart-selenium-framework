package com.nikhil.tests.hooks;

import com.nikhil.framework.config.ConfigReader;
import com.nikhil.framework.driver.DriverFactory;
import com.nikhil.framework.logger.LoggerFactory;
import com.nikhil.framework.reports.ReportManager;
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
        ReportManager.createTest(scenario.getName());
        ReportManager.assignCategory("Regression"); //Later make this dynamic using Cucumber tags (@Smoke, @Regression)
        ReportManager.info("Launching browser and starting execution");
        DriverFactory.initializeDriver(ConfigReader.getInstance().getBrowser());
    }

    @After
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {
            ReportManager.fail("Scenario Failed");
        } else {
            ReportManager.pass("Scenario executed successfully");
        }
        ReportManager.info("Closing browser session");
        DriverFactory.quitDriver();
        ReportManager.flush();
        ReportManager.unload();
        LOGGER.info("Finished Scenario : {}", scenario.getName());
    }

}
