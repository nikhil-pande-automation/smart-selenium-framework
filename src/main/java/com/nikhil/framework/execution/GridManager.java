package com.nikhil.framework.execution;

import com.nikhil.framework.config.ConfigReader;

/**
 * Purpose:
 * Decides which Selenium Grid URL should be used for remote execution.
 *
 * Priority:
 *
 * JVM parameter
 *      │
 *      ▼
 * -Dgrid.url=...
 *
 * If JVM parameter is not provided
 *      │
 *      ▼
 * config-local.properties
 *      │
 *      ▼
 * grid.url=...
 *
 * Why?
 *
 * Local execution can use:
 * http://localhost:4444/wd/hub
 *
 * Jenkins execution may use:
 * http://selenium-chrome:4444/wd/hub
 *
 * Therefore we should not hardcode Grid URL inside DriverFactory.
 */
public final class GridManager {

    /*
     Utility class contains only static method.
     Therefore object creation is not required.
    */
    private GridManager() {
    }

    /**
     * Returns Grid URL based on following priority:
     *
     * 1. JVM parameter: -Dgrid.url
     * 2. config properties file
     *
     * Example:
     *
     * mvn clean test -Dexecution=remote
     *                -Dgrid.url=http://selenium-chrome:4444/wd/hub
     *
     * If -Dgrid.url is provided, it overrides config file value.
     */
    public static String getGridUrl() {

        String gridUrlFromJVM = System.getProperty("grid.url");

        /*
         If JVM parameter exists and is not blank,
         use JVM value instead of properties file.
        */
        if (gridUrlFromJVM != null && !gridUrlFromJVM.trim().isEmpty()) {

            return gridUrlFromJVM.trim();
        }

        /*
         JVM parameter was not provided.
         Therefore return Grid URL from config properties file.
        */
        return ConfigReader.getInstance().getGridUrl();
    }
}