package com.nikhil.framework.driver;

import com.nikhil.framework.config.ConfigReader;
import com.nikhil.framework.enums.BrowserType;
import com.nikhil.framework.logger.LoggerFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

/**
 * This class is responsible for creating browser instances. & flow will be as follows,
 * BaseTest
 * │
 * ▼
 * DriverFactory
 * │
 * ▼
 * ChromeDriver / EdgeDriver / FirefoxDriver
 * │
 * ▼
 * DriverManager.setDriver(driver)
 * │
 * ▼
 * Test starts
 * <p>
 * Why separate DriverFactory from DriverManager?
 * <p>
 * This separation follows the Single Responsibility Principle (SRP):
 * <p>
 * DriverFactory → Creates and closes browsers.
 * DriverManager → Stores and retrieves the browser for the current thread.
 * <p>
 * working ####################
 * Our DriverFactory will contain these methods:
 * public static void initializeDriver(String browser)
 * public static void quitDriver()
 * <p>
 * Internally, initializeDriver() will:
 * <p>
 * Read the browser name (chrome, edge, firefox).
 * Use WebDriverManager to download/setup the correct driver.
 * Create the browser instance.
 * Maximize the window.
 * Set page load timeout.
 * Store it using DriverManager.setDriver(driver).
 * <p>
 * quitDriver() will:
 * <p>
 * Get the current thread's driver.
 * Call quit().
 * Call DriverManager.unloadDriver() to remove it from ThreadLocal.
 * <p>
 * DriverFactory never stores the driver.
 * <p>
 * It only creates it.
 * <p>
 * Storage is DriverManager's responsibility.
 * <p>
 * Design Decision:
 * Browser creation belongs to DriverFactory,
 * whereas browser storage belongs to DriverManager.
 * <p>
 * Interview Question
 * <p>
 * Q: Why is WebDriver driver declared as a local variable in DriverFactory?
 * <p>
 * Answer:
 * DriverFactory is only responsible for creating the browser instance.
 * After creation, the WebDriver is immediately stored in ThreadLocal
 * via DriverManager.setDriver(driver). Therefore, the local variable is no longer required.
 * Using a local variable also avoids shared mutable state and supports parallel execution.
 */

// Final because DriverFactory is a utility class.
public final class DriverFactory {

    // Prevent object creation.
    private DriverFactory() {
    }

    // Creates browser instance based on supplied browser name.
    public static void initializeDriver(BrowserType browser) {
        WebDriver driver;
        switch (browser) {

            case CHROME:
                // Downloads and configures the compatible ChromeDriver.
                WebDriverManager.chromedriver().setup();

                // Creates a new Chrome browser instance.
                logger.info("Launching Chrome browser");
                driver = new ChromeDriver();
                DriverManager.setDriver(driver); //Stores WebDriver in ThreadLocal.
                configureBrowser(driver);
                logger.info("Chrome browser launched successfully");
                break;

            case EDGE:
                WebDriverManager.edgedriver().setup();
                logger.info("Launching Edge browser");
                driver = new EdgeDriver();
                DriverManager.setDriver(driver);
                configureBrowser(driver);
                logger.info("Edge browser launched successfully");
                break;

            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                logger.info("Launching Firefox browser");
                driver = new FirefoxDriver();
                DriverManager.setDriver(driver);
                configureBrowser(driver);
                logger.info("Firefox browser launched successfully");
                break;

            default:
                throw new RuntimeException("Unsupported browser : " + browser);
        }
        /**
         * WebDriver driver is Local variable bcoz initializeDriver() creates WebDriver driver = new ChromeDriver();
         * Immediately after that: DriverManager.setDriver(driver); Now what happens?
         * DriverFactory
         * DriverFactory
         *
         * driver
         *       │
         *       ▼
         * ChromeDriver
         *
         * ↓
         *
         * DriverManager.setDriver(driver)
         *
         * ↓
         *
         * ThreadLocal stores it
         *
         * ↓
         *
         * initializeDriver() finishes
         *
         * ↓
         *
         * local variable disappears
         *
         * ↓
         *
         * Browser is still alive
         * Because the browser is now stored inside ThreadLocal.
         *
         * So the local variable has completed its job.
         *
         * If we make it static like: private static WebDriver driver; Now every thread shares the same variable.
         * e.g.
         * Thread-1
         *
         * driver → Chrome-1
         *
         * ↓
         *
         * Thread-2
         *
         * driver → Chrome-2
         * Thread-1 loses its reference.
         * 💥 Parallel execution breaks.
         *Exactly what we are trying to avoid.
         *
         */
    }

    // Closes browser and removes WebDriver from current thread.
    public static void quitDriver() {
        if (DriverManager.getDriver() != null) {
            logger.info("Closing browser");
            DriverManager.getDriver().quit();
            DriverManager.unloadDriver();
            logger.info("Browser closed successfully");
        }
    }

    // Applies common browser settings. private bcoz will only use in this class.
    private static void configureBrowser(WebDriver driver) {
        try {
            driver.manage().window().maximize();
        } catch (Exception e) {
            logger.warn("Unable to maximize browser window. Continuing execution.");
        }
        driver.manage().deleteAllCookies();
        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(
                        ConfigReader.getInstance().getPageLoadTimeout()));
    }

    private static final Logger logger =
            LoggerFactory.getLogger(DriverFactory.class);

}
/**
 * DriverManager – Key Takeaways
 * ✅ Single Responsibility: Manages thread-specific WebDriver instances.
 * ✅ ThreadLocal enables safe parallel execution.
 * ✅ private static final protects the shared ThreadLocal container.
 * ✅ remove() is preferred over set(null) to avoid stale thread entries.
 * 🎯 Interview focus: Explain why ThreadLocal is needed, not just how to use it.
 */
