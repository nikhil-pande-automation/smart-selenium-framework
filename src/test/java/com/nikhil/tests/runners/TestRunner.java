package com.nikhil.tests.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Purpose:
 * Entry point for Cucumber execution.
 * <p>
 * Responsibilities:
 * - Locate feature files.
 * - Locate step definitions.
 * - Configure reporting plugins.
 * - Start Cucumber using TestNG.
 * <p>
 * Why extend AbstractTestNGCucumberTests?
 * It integrates Cucumber with the TestNG execution engine,
 * allowing TestNG to execute feature files.
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.nikhil.tests",
        plugin = {"pretty", "html:reports/cucumber/cucumber.html", "json:reports/cucumber/cucumber.json"},
        monochrome = true, // Makes console output more readable.
        publish = false // Disable Cucumber online report publishing.
)

/**
 * Generally, we just extends AbstractTestNGCucumberTests class in TestRunner class
 * we kept Test class empty when there is no setup for parallel execution.
 * What we implemented inside TestRunner class is the
 * official Cucumber + TestNG way of enabling parallel execution.
 */

public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
