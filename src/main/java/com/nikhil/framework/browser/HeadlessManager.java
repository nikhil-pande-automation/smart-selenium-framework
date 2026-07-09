package com.nikhil.framework.browser;

import com.nikhil.framework.config.ConfigReader;

/**
 * Determines whether browser should run
 * in headless mode.
 * <p>
 * Priority:
 * 1. JVM parameter
 * 2. Configuration file
 */

public final class HeadlessManager {

    private HeadlessManager() {
    }

    public static boolean isHeadless() {
        String headless = System.getProperty("headless");

        if (headless != null && !headless.trim().isEmpty()) {
            return Boolean.parseBoolean(headless);
        }
        return ConfigReader.getInstance().isHeadless();
    }

}
