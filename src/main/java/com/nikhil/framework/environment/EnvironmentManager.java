package com.nikhil.framework.environment;

import com.nikhil.framework.constants.FrameworkConstants;
import com.nikhil.framework.enums.EnvironmentType;

/**
 * Decides which execution environment
 * should be used by the framework.
 */

public final class EnvironmentManager {

    private EnvironmentManager() {
    }

//    public static EnvironmentType getEnvironment() {
//
//        String environment =
//                System.getProperty("environment", "LOCAL");
//
//        System.out.println("Execution environment is : " + environment);
//
//        return EnvironmentType.valueOf(
//                environment.toUpperCase());
//
//    }

    /**
     * Returns the execution environment.
     * Defaults to LOCAL if no JVM parameter is supplied.
     * To avoid exception during value=abc (If value user input differ than implemented env)
     */
    public static EnvironmentType getEnvironment() {
        String environment = System.getProperty("environment", "LOCAL").trim().toUpperCase();

        try {
            return EnvironmentType.valueOf(environment);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "\nInvalid environment : " + environment +
                            "\nSupported environments are : " +
                            "\nLOCAL" +
                            "\nQA" +
                            "\nUAT" +
                            "\nPROD",
                    e
            );
        }
    }

    /**
     * Returns configuration file path
     * based on current execution environment.
     */
    public static String getConfigFilePath() {
        return FrameworkConstants.CONFIG_DIRECTORY + "config-" + getEnvironment().name().toLowerCase()
                + ".properties";
    }


}
