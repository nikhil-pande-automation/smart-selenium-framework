package com.nikhil.framework.utilities;

import com.nikhil.framework.driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;

public final class ScreenshotUtil {

    private ScreenshotUtil() {

    }

    public static File takeScreenshot() {
        return ((TakesScreenshot) DriverManager.getDriver())
                .getScreenshotAs(OutputType.FILE);
    }
}
