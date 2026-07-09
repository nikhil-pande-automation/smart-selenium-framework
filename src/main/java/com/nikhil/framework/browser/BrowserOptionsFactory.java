package com.nikhil.framework.browser;

import com.nikhil.framework.config.ConfigReader;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Creates browser-specific options.
 * <p>
 * DriverFactory should never directly configure
 * browser capabilities.
 */

public final class BrowserOptionsFactory {

    private BrowserOptionsFactory() {
    }

    /**
     * Returns ChromeOptions.
     */
    public static ChromeOptions getChromeOptions() {

        ChromeOptions options = new ChromeOptions();
        if (isHeadless()) {

            options.addArguments("--headless=new");

        }

        /*
         * Placeholder for future:
         *
         * Headless
         * Incognito
         * Download directory
         * Mobile emulation
         * Docker
         * Selenium Grid
         */
        return options;

    }

    /**
     * Returns FirefoxOptions.
     */
    public static FirefoxOptions getFirefoxOptions() {

        FirefoxOptions options = new FirefoxOptions();
        if (isHeadless()) {

            options.addArguments("-headless");

        }

        return options;

    }


    /**
     * Returns EdgeOptions.
     */
    public static EdgeOptions getEdgeOptions() {

        EdgeOptions options = new EdgeOptions();
        if (isHeadless()) { //Edge uses Chromium, so Exactly same as Chrome.

            options.addArguments("--headless=new");

        }

        return options;

    }

    /**
     * Returns headless execution configuration.
     * before writing this method, all 3 if conditions out there needs to have repeated line
     * Before: if(ConfigReader.getInstance().isHeadless())
     * After: if(isHeadless())
     * This is "Don't repeat yourself" principle.
     * Using HeadlessManager.isHeadless(); here so that we can use headless mode using maven commands,
     * This will reduce the dependency from the config file.
     *
     * We can use: mvn clean test -Dheadless=true to run on headless mode
     * even though configfile has headless=false OR vice versa
     *
     */
    private static boolean isHeadless() {
        return HeadlessManager.isHeadless();
    }


}
