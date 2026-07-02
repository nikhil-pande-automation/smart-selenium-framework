package com.nikhil.framework.utilities;

import com.nikhil.framework.driver.DriverManager;
import org.openqa.selenium.Alert;

/**
 * Utility class for handling JavaScript alerts.
 * <p>
 * Provides reusable methods to interact with browser alerts.
 */

public final class AlertUtil {

    private AlertUtil() {

    }

    // This is helper method like JavaScriptUtil.
    private static Alert getAlert() {
        return DriverManager.getDriver().switchTo().alert();
    }

    // Accepts the currently displayed alert.
    public static void accept() {
        getAlert().accept();
    }

    // Dismisses the currently displayed alert.
    public static void dismiss() {
        getAlert().dismiss();
    }

    // Returns alert message.
    public static String getText() {
        return getAlert().getText();
    }

    // Enters text into a prompt alert.
    public static void sendKeys(String text) {
        getAlert().sendKeys(text);
    }
}
