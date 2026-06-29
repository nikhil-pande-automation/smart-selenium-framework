package com.nikhil.framework.driver;

import org.openqa.selenium.WebDriver;

/**
 * This class use for: Store and return the correct WebDriver for the current thread.This follows the
 * Single Responsibility Principle (SRP).
 * e.g. Thread-1 Login Test | Thread-2 Transfer Fund Test --> public static WebDriver driver;
 * both tests use the same driver object. is the issue-Thread-1's driver reference is overwritten.
 * 💥 Parallel execution breaks.
 * So use ThreadLocal<WebDriver>: which gives Each thread gets its own private WebDriver.
 * No one can overwrite another thread's driver.
 * Purpose:
 * Manages WebDriver instances for parallel execution.
 * Why?
 * ThreadLocal provides a separate WebDriver instance
 * for each executing thread.
 * Private constructor prevents object creation because
 * this is a utility class containing only static methods.
 * Final class because DriverManager should never be extended.
 * ** In Java, constants are written in uppercase. e.g. DRIVER
 * // DRIVER reference cannot point to another ThreadLocal object
 * // because it is final.
 * //
 * // However, DRIVER.set(driver) is allowed because we are modifying
 * // the state inside the ThreadLocal object, not changing the DRIVER reference.
 * * This difference is asked in interviews.
 *
 */

public final class DriverManager {

    // Prevent object instantiation.
    private DriverManager() {
    }

    // One ThreadLocal container shared by the framework.
    // Internally it stores a different WebDriver for each thread.
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    /**
     * Our DriverManager will eventually look like this:
     * <p>
     * Thread-1
     * │
     * ▼
     * DriverManager.setDriver(chrome1)
     * <p>
     * Thread-2
     * │
     * ▼
     * DriverManager.setDriver(chrome2)
     * <p>
     * │
     * ▼
     * ThreadLocal<WebDriver>
     * ----------------------
     * Thread-1 → Chrome1
     * Thread-2 → Chrome2
     * Thread-3 → Chrome3
     * <p>
     * │
     * ▼
     * DriverManager.getDriver()
     * <p>
     * │
     * ▼
     * Returns only the browser belonging to the current thread.
     * <p>
     * This is the entire secret behind safe parallel execution.
     * <p>
     * method public - Anyone in the framework should be able to call it.
     * method static - we have to restrict from object creation.
     * (WebDriver driver) : Suppose DriverFactory creates ChromeDriver, It passes that object here.
     * DRIVER.set(driver) : Thread-1: ChromeDriver@123 & Thread-2: ChromeDriver@456
     * When Thread-1 executes DRIVER.set(driver) internally ThreadLocal stores Thread-1=ChromeDriver@123
     * When Thread-2 executes then stores ChromeDriver@456. They never overwrite each other.
     *
     */

    // Stores WebDriver for the current thread.
    public static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    /**
     * Method-2 : getDriver() Why? Suppose LoginPage wants driver. Instead of driver.findElement(...)
     * it will do DriverManager.getDriver() ThreadLocal automatically returns Current Thread's Driver
     * e.g. If Login Test is running returns Chrome-1 & If Transfer Test is running returns Chrome-2
     * Same code. Different browser.
     *
     */

    // Returns WebDriver of the current thread.
    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    /**
     * Why not DRIVER.set(null) wrong because Thread still exists by holding null, Memory isn't completely cleaned.
     * remove() does removes complete entry, Garbage Collector can clean it. This prevents memory leaks in long-running parallel executions.
     *
     */

    // Removes WebDriver reference from current thread.
    public static void unloadDriver() {
        DRIVER.remove();
    }
}
/**
 * Interview Questions (Very Important)
 * <p>
 * If an interviewer asks:
 * Q1. Why is ThreadLocal static?
 * Answer:
 * Because there should be only one shared ThreadLocal container for the entire framework. The container internally maintains a separate WebDriver instance for each thread.
 * Q2. Why is ThreadLocal final?
 * Answer:
 * The reference to the ThreadLocal object should never change. We only change its internal state using set(), get(), and remove().
 * Q3. Why don't we make WebDriver static?
 * Answer:
 * A static WebDriver creates a single shared browser for all threads. During parallel execution,
 * one thread can overwrite another's browser instance, leading to unstable tests.
 * ThreadLocal<WebDriver> ensures each thread gets its own isolated browser.
 */