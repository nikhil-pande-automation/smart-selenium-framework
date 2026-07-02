package com.nikhil.framework.utilities;

import com.nikhil.framework.driver.DriverManager;

import java.util.Set;

/**
 * Utility class for browser window handling.
 */

public final class WindowUtil {

    private WindowUtil() {

    }

    //   Returns current window handle.
    public static String getCurrentWindow() {
        return DriverManager.getDriver().getWindowHandle();
    }

    // Returns all window handles.
    public static Set<String> getAllWindows() {
        return DriverManager.getDriver().getWindowHandles();
    }

    // Switches to specified window.
    public static void switchToWindow(String windowHandle) {
        DriverManager.getDriver()
                .switchTo()
                .window(windowHandle);
    }
}
