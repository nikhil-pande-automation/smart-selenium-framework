package com.nikhil.framework.reports;

import com.aventstack.extentreports.ExtentTest;

/**
 * Stores ExtentTest object per thread.
 */
public final class ExtentTestManager {

    private static final ThreadLocal<ExtentTest> TEST =
            new ThreadLocal<>();

    private ExtentTestManager() {
    }

    public static void setTest(ExtentTest extentTest) {
        TEST.set(extentTest);
    }

    public static ExtentTest getTest() {
        return TEST.get();
    }

    public static void unload() {
        TEST.remove();
    }

}
