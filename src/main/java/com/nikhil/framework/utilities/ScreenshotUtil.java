package com.nikhil.framework.utilities;

import com.nikhil.framework.config.ConfigReader;
import com.nikhil.framework.constants.FrameworkConstants;
import com.nikhil.framework.driver.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ScreenshotUtil {

    private ScreenshotUtil() {

    }

    /**
     * Takes screenshot and stores it inside report screenshots folder.
     *
     * @return relative path of the screenshot
     */

    public static String takeScreenshot(String scenarioName) {
        String timestamp = new SimpleDateFormat(
                "yyyyMMdd_HHmmss").format(new Date());

        String browser =
                ConfigReader.getInstance().getBrowser().name();
        String sanitizedScenario =
                scenarioName.replaceAll("[^a-zA-Z0-9]", "_");

        String fileName =
                sanitizedScenario + "_" + browser + "_" + timestamp + ".png";

        String destination =
                FrameworkConstants.SCREENSHOT_PATH + fileName;

        File source = ((TakesScreenshot) DriverManager.getDriver())
                .getScreenshotAs(OutputType.FILE);

        File directory = new File(FrameworkConstants.SCREENSHOT_PATH);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File target = new File(destination);

        try {
            FileUtils.copyFile(source, target);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save screenshot", e);
        }
        return "screenshots/" + fileName;
    }
}
