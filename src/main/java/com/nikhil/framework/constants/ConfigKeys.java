package com.nikhil.framework.constants;

/**
 * Configuration property keys.
 * <p>
 * Centralized to avoid hardcoded strings from ConfigReader
 * eg getValue("url") or etValue("browser")
 * throughout the framework.
 */
public final class ConfigKeys {

    private ConfigKeys() {
    }

    public static final String BROWSER = "browser";

    public static final String URL = "url";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String EXPLICIT_WAIT = "explicit.wait";

    public static final String PAGE_LOAD_TIMEOUT = "page.load.timeout";

    public static final String HEADLESS = "headless";

    public static final String SCREENSHOT_ON_PASS =
            "take.screenshot.on.pass";

    public static final String SCREENSHOT_ON_FAIL =
            "take.screenshot.on.fail";

}
