package com.nikhil.framework.config;

import com.nikhil.framework.constants.FrameworkConstants;
import com.nikhil.framework.enums.BrowserType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Purpose:
 * Reads framework configuration from config.properties.
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
    private static ConfigReader instance;

    // Prevent object creation from outside the class.
    private ConfigReader() {
        properties = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream(FrameworkConstants.CONFIG_FILE_PATH)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load config.properties", e);
        }
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
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
                getValue("browser").trim().toUpperCase());

    }

    // Returns application URL.
    public String getUrl() {

        return getValue("url").trim();

    }

    // Returns explicit wait timeout.
    public int getExplicitWait() {

        return Integer.parseInt(
                getValue("explicit.wait").trim());

    }

    // Returns page load timeout.
    public int getPageLoadTimeout() {

        return Integer.parseInt(
                getValue("page.load.timeout").trim());

    }

    // Returns headless execution configuration.
    public boolean isHeadless() {

        return Boolean.parseBoolean(
                getValue("headless").trim());

    }

    // Returns screenshot configuration for passed tests.
    public boolean takeScreenshotOnPass() {

        return Boolean.parseBoolean(
                getValue("take.screenshot.on.pass").trim());

    }

    // Returns screenshot configuration for failed tests.
    public boolean takeScreenshotOnFail() {

        return Boolean.parseBoolean(
                getValue("take.screenshot.on.fail").trim());

    }

    //Returns application username.
    public String getUsername() {
        return getValue("username").trim();
    }

    //    Returns application password
    public String getPassword() {
        return getValue("password").trim();
    }



}
