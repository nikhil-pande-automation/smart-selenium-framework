package com.nikhil.framework.reports;

import com.aventstack.extentreports.Status;

/**
 * Provides simplified methods for report logging.
 */
public final class ReportManager {

    private ReportManager() {
    }

    public static void createTest(String testName) {
        ExtentTestManager.setTest(
                ExtentManager.getExtentReports()
                        .createTest(testName));
    }

    public static void pass(String message) {
        ExtentTestManager.getTest()
                .log(Status.PASS, message);
    }

    public static void fail(String message) {
        ExtentTestManager.getTest()
                .log(Status.FAIL, message);
    }

    public static void info(String message) {
        ExtentTestManager.getTest()
                .log(Status.INFO, message);
    }

    public static void flush() {
        ExtentManager.getExtentReports().flush();
    }

    public static void unload() {
        ExtentTestManager.unload();
    }

    public static void assignCategory(String category) {
        ExtentTestManager.getTest()
                .assignCategory(category);
    }

}