package com.nikhil.framework.utilities;

import com.nikhil.framework.driver.DriverManager;
import org.openqa.selenium.By;

public final class FrameUtil {

    private FrameUtil() {

    }

    //     Switches to frame using index.
    public static void switchToFrame(int index) {
        DriverManager.getDriver().switchTo().frame(index);
    }

    //     Switches to frame using name or id.
    public static void switchToFrame(String nameOrId) {
        DriverManager.getDriver().switchTo().frame(nameOrId);
    }

    // Switches to frame using locator.
    public static void switchToFrame(By locator) {
        DriverManager.getDriver().switchTo().frame(DriverManager.getDriver().findElement(locator));
    }

    // Switches back to parent frame.
    public static void switchToParentFrame() {
        DriverManager.getDriver().switchTo().parentFrame();
    }

    // Switches back to default content.
    public static void switchToDefaultContent() {
        DriverManager.getDriver().switchTo().defaultContent();
    }
}
