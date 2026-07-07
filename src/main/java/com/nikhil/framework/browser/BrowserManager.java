package com.nikhil.framework.browser;
import com.nikhil.framework.config.ConfigReader;
import com.nikhil.framework.enums.BrowserType;

/**
 * For Cross Browser Execution from Maven
 * Determines which browser should be used.
 * <p>
 * Priority:
 * 1. JVM parameter
 * 2. Configuration file
 */

public final class BrowserManager {

    private BrowserManager() {
    }

    public static BrowserType getBrowser() {

        String browser = System.getProperty("browser");

        if (browser != null && !browser.trim().isEmpty()) {
            return BrowserType.valueOf(browser.trim().toUpperCase());
        }

        return ConfigReader.getInstance().getBrowser();

    }
}
