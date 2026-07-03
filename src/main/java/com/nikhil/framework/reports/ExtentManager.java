package com.nikhil.framework.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.nikhil.framework.config.ConfigReader;
import com.nikhil.framework.constants.FrameworkConstants;

/**
 * Creates and returns a singleton ExtentReports instance.
 */

public final class ExtentManager {

    private static ExtentReports extent;

    private ExtentManager() {
    }

    public static ExtentReports getExtentReports() {
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(
                    FrameworkConstants.REPORT_PATH + "ExtentReport.html");

            extent = new ExtentReports();

            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("Framework", "Smart Selenium Framework");
            extent.setSystemInfo("Author", "Nikhil Pande");
            extent.setSystemInfo("Browser", ConfigReader.getInstance().getBrowser().name());
            extent.setSystemInfo("Application URL", ConfigReader.getInstance().getUrl());
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
        }
        return extent;
    }

}
