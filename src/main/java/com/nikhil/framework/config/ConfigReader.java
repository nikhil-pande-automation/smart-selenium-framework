package com.nikhil.framework.config;

import com.nikhil.framework.constants.ConfigKeys;
import com.nikhil.framework.enums.BrowserType;
import com.nikhil.framework.enums.ExecutionType;
import com.nikhil.framework.environment.EnvironmentManager;
import com.nikhil.framework.logger.LoggerFactory;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Purpose:
 * Reads framework configuration from config-local.properties.
 * <p>
 * Design:
 * Implemented as Singleton so configuration is loaded
 * only once and reused across the framework.
 * No need of frequent new configReader(); No need multiple objects of configReader
 * Singleton design pattern: framework starts>properties loaded with only single object>stores in memory>use same obj throughout project
 */

public final class ConfigReader {

    // Holds framework configuration.
    private final Properties properties;

    // Single instance of ConfigReader.
//    private static ConfigReader instance; //commenting this line to write another logic for a proper singleton

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ConfigReader.class);

    // Prevent object creation from outside the class.
    private ConfigReader() {

        properties = new Properties();
        String configFilePath = EnvironmentManager.getConfigFilePath();
        LOGGER.info("Generated config file path : {}", configFilePath);

        try (FileInputStream fileInputStream = new FileInputStream(configFilePath)) {
            properties.load(fileInputStream);
            LOGGER.info("Configuration loaded successfully.");
            LOGGER.debug("Loaded Properties : {}", properties); //Check loaded property
        } catch (IOException e) {
            throw new RuntimeException("Unable to load configuration file : " + configFilePath, e);
        }

    }

    public static ConfigReader getInstance() {
//        if (instance == null) {
//            instance = new ConfigReader();
//        }
//        return instance; //commenting this 4 lines as line 28 code commented and adding new code below
        return Holder.INSTANCE; //Holder class added below to bottom of this class
    }

    // Returns configuration value for the given key.
    private String getValue(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Missing configuration key : " + key);
        }
        return value;
    }

    // Returns browser configured for execution.
    public BrowserType getBrowser() {

        return BrowserType.valueOf(
                getValue(ConfigKeys.BROWSER).trim().toUpperCase());

    }

    // Returns application URL.
    public String getUrl() {

        return getValue(ConfigKeys.URL).trim();

    }

    // Returns explicit wait timeout.
    public int getExplicitWait() {

        return Integer.parseInt(
                getValue(ConfigKeys.EXPLICIT_WAIT).trim());

    }

    // Returns page load timeout.
    public int getPageLoadTimeout() {

        return Integer.parseInt(
                getValue(ConfigKeys.PAGE_LOAD_TIMEOUT).trim());

    }

    // Returns headless execution configuration.
    public boolean isHeadless() {

        return Boolean.parseBoolean(
                getValue(ConfigKeys.HEADLESS).trim());

    }

    // Returns screenshot configuration for passed tests.
    public boolean takeScreenshotOnPass() {

        return Boolean.parseBoolean(
                getValue(ConfigKeys.SCREENSHOT_ON_PASS).trim());

    }

    // Returns screenshot configuration for failed tests.
    public boolean takeScreenshotOnFail() {

        return Boolean.parseBoolean(
                getValue(ConfigKeys.SCREENSHOT_ON_FAIL).trim());

    }

    //Returns application username.
    public String getUsername() {
        return getValue(ConfigKeys.USERNAME).trim();
    }

    //    Returns application password
    public String getPassword() {
        return getValue(ConfigKeys.PASSWORD).trim();
    }

    public ExecutionType getExecutionType() {

        return ExecutionType.valueOf(
                getValue("execution").trim().toUpperCase()
        );
    }

    /**
     * Returns Selenium Grid URL from properties file.
     *
     * This URL is required only during REMOTE execution.
     *
     * Flow:
     * config-local.properties
     *          │
     *          ▼
     * grid.url=http://localhost:4444/wd/hub
     *          │
     *          ▼
     * ConfigReader.getGridUrl()
     *          │
     *          ▼
     * DriverFactory
     *          │
     *          ▼
     * RemoteWebDriver connects to Selenium Grid.
     *
     * During LOCAL execution this value is not used.
     */
    public String getGridUrl() {
        return getValue("grid.url").trim();
    }


    /**
     * Holder class responsible for creating the Singleton instance.
     *
     * Why use Holder instead of:
     *
     * if(instance == null)
     *
     * ?
     *
     * Java loads a nested static class only when it is referenced
     * for the first time.
     *
     * Until getInstance() is called,
     * Holder is never loaded.
     *
     * When Holder is loaded,
     * JVM guarantees that INSTANCE is created exactly once,
     * even if multiple threads call getInstance() simultaneously.
     *
     * Advantages:
     * ✔ Lazy initialization
     * ✔ Thread-safe
     * ✔ No synchronized keyword
     * ✔ No performance overhead
     *
     * This pattern is known as the
     * Initialization-on-demand holder idiom.
     */
    private static class Holder {

        private static final ConfigReader INSTANCE =
                new ConfigReader();

    }
}
