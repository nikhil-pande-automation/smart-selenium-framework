package com.nikhil.tests.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

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

public class TestRunner extends AbstractTestNGCucumberTests {

}
