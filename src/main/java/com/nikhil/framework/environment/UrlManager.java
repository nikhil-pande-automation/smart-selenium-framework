package com.nikhil.framework.environment;

import com.nikhil.framework.config.ConfigReader;

/**
 * Purpose:
 * Decides which application URL should be used during test execution.
 * <p>
 * Why this class is required?
 * <p>
 * During LOCAL execution:
 * <p>
 * Browser runs on local machine
 * │
 * ▼
 * http://localhost:8080/parabank/index.htm
 * <p>
 * <p>
 * During REMOTE Docker execution:
 * <p>
 * Browser runs inside Selenium Chrome container
 * │
 * ▼
 * localhost means Selenium container itself.
 * <p>
 * Therefore this URL will NOT work:
 * <p>
 * http://localhost:8080/parabank/index.htm
 * <p>
 * Instead Selenium container should access ParaBank using
 * Docker network hostname:
 * <p>
 * http://parabank:8080/parabank/index.htm
 * <p>
 * <p>
 * Priority:
 * <p>
 * 1. JVM parameter:
 * <p>
 * -Durl=http://parabank:8080/parabank/index.htm
 * <p>
 * │
 * ▼
 * <p>
 * 2. If JVM parameter is not provided,
 * use URL from environment properties file.
 * <p>
 * Why not directly change config-local.properties?
 * <p>
 * Because local execution already works with localhost.
 * We should not break local execution only to support Docker/Jenkins.
 * <p>
 * Therefore:
 * <p>
 * Local:
 * config-local.properties → localhost URL
 * <p>
 * Jenkins/Docker:
 * -Durl=... → Docker network URL
 */

public final class UrlManager {

    /*
     Utility class contains only static method.
     Therefore object creation is not required.
    */
    private UrlManager() {
    }

    /**
     * Returns application URL based on following priority:
     * <p>
     * 1. JVM parameter: -Durl
     * 2. Environment properties file through ConfigReader
     * <p>
     * Example:
     * <p>
     * Local execution:
     * <p>
     * mvn clean test
     * <p>
     * No -Durl provided
     * │
     * ▼
     * ConfigReader.getUrl()
     * │
     * ▼
     * http://localhost:8080/parabank/index.htm
     * <p>
     * <p>
     * Jenkins/Docker execution:
     * <p>
     * mvn clean test
     * -Durl=http://parabank:8080/parabank/index.htm
     * <p>
     * JVM parameter exists
     * │
     * ▼
     * JVM URL overrides properties file URL.
     */
    public static String getUrl() {

        String urlFromJVM = System.getProperty("url");

        /*
         Check whether -Durl JVM parameter was provided.

         Example:
         -Durl=http://parabank:8080/parabank/index.htm
        */
        if (urlFromJVM != null && !urlFromJVM.trim().isEmpty()) {

            return urlFromJVM.trim();
        }

        /*
         No JVM URL was provided.

         Therefore use URL from currently selected
         environment configuration file.
        */
        return ConfigReader.getInstance().getUrl();
    }
}
