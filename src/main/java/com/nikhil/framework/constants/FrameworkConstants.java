package com.nikhil.framework.constants;

// Final class because constants are not meant to be inherited.
public final class FrameworkConstants {

    // Private constructor prevents object creation.
    private FrameworkConstants() {
    }

    // Base directory of the framework.Useful to avoid hardcoded paths.
    public static final String PROJECT_ROOT = System.getProperty("user.dir");

    // Configuration file path.
    public static final String CONFIG_FILE_PATH =
            PROJECT_ROOT + "/src/main/resources/config/config.properties";

    // Report directory.
    public static final String REPORT_PATH =
            PROJECT_ROOT + "/reports/";

    // Screenshot directory.
    public static final String SCREENSHOT_PATH =
            PROJECT_ROOT + "/screenshots/";

    // Log directory.
    public static final String LOG_PATH =
            PROJECT_ROOT + "/logs/";



}
