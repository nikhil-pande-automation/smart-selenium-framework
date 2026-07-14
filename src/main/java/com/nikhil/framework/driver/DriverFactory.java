package com.nikhil.framework.driver;

import com.nikhil.framework.browser.BrowserOptionsFactory;
import com.nikhil.framework.config.ConfigReader;
import com.nikhil.framework.enums.BrowserType;
import com.nikhil.framework.enums.ExecutionType;
import com.nikhil.framework.execution.ExecutionManager;
import com.nikhil.framework.execution.GridManager;
import com.nikhil.framework.logger.LoggerFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;

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
    /**
     * Creates WebDriver based on:
     *
     * 1. Execution Type → LOCAL or REMOTE
     * 2. Browser Type   → CHROME, EDGE or FIREFOX
     *
     * Flow:
     *
     * Hooks
     *   │
     *   ▼
     * DriverFactory.initializeDriver(browser)
     *   │
     *   ▼
     * ExecutionManager.getExecutionType()
     *   │
     *   ├── LOCAL
     *   │      │
     *   │      ▼
     *   │   createLocalDriver(browser)
     *   │
     *   │      ├── ChromeDriver
     *   │      ├── EdgeDriver
     *   │      └── FirefoxDriver
     *   │
     *   └── REMOTE
     *          │
     *          ▼
     *       createRemoteDriver(browser)
     *          │
     *          ▼
     *       RemoteWebDriver
     *          │
     *          ▼
     *       Selenium Grid / Browser Container
     *
     * Why keep LOCAL and REMOTE logic separate?
     *
     * If everything is written inside one large method,
     * DriverFactory becomes difficult to understand and maintain.
     *
     * Separate methods make responsibility clear:
     *
     * createLocalDriver()  → Creates browser on current machine.
     * createRemoteDriver() → Creates browser on Selenium server/Grid.
     */
    public static void initializeDriver(BrowserType browser) {

        WebDriver driver;

        /*
         Reads execution mode.

         It can come from:
         1. JVM parameter: -Dexecution=remote
         2. config-local.properties: execution=local
        */
        ExecutionType executionType = ExecutionManager.getExecutionType();

        logger.info(
                "Execution type : {}, Browser : {}", executionType, browser
        );

        /*
         Decide whether browser should start:

         LOCAL  → On same machine where test is running.
         REMOTE → On Selenium Grid / browser container.
        */
        if (executionType == ExecutionType.LOCAL) {

            driver = createLocalDriver(browser);

        } else if (executionType == ExecutionType.REMOTE) {

            driver = createRemoteDriver(browser);

        } else {

            throw new RuntimeException(
                    "Unsupported execution type : " + executionType
            );
        }

        /*
         Store created driver inside ThreadLocal.

         Example during parallel execution:

         Thread-1 → ChromeDriver / RemoteWebDriver-1
         Thread-2 → ChromeDriver / RemoteWebDriver-2

         Each thread gets its own WebDriver.
        */
        DriverManager.setDriver(driver);

        // Applies common browser settings after driver is successfully created.
        configureBrowser(driver);

        logger.info(
                "{} browser launched successfully in {} mode",
                browser,
                executionType
        );
    }

    /**
     * Creates browser on the same machine where test execution is running.
     *
     * Example:
     *
     * IntelliJ / Local Terminal
     *          │
     *          ▼
     * createLocalDriver(CHROME)
     *          │
     *          ▼
     * new ChromeDriver()
     *          │
     *          ▼
     * Chrome opens on local machine.
     *
     * This is the execution flow we were already using before adding
     * remote execution support.
     */
    private static WebDriver createLocalDriver(BrowserType browser) {

        switch (browser) {

            case CHROME:

                // Downloads/configures compatible ChromeDriver.
                WebDriverManager.chromedriver().setup();

                logger.info("Launching local Chrome browser");

                return new ChromeDriver(
                        BrowserOptionsFactory.getChromeOptions()
                );

            case EDGE:

                WebDriverManager.edgedriver().setup();

                logger.info("Launching local Edge browser");

                return new EdgeDriver(
                        BrowserOptionsFactory.getEdgeOptions()
                );

            case FIREFOX:

                WebDriverManager.firefoxdriver().setup();

                logger.info("Launching local Firefox browser");

                return new FirefoxDriver(
                        BrowserOptionsFactory.getFirefoxOptions()
                );

            default:

                throw new RuntimeException(
                        "Unsupported browser for local execution : " + browser
                );
        }
    }

    /**
     * Creates browser on remote Selenium server/Grid.
     *
     * Important:
     *
     * RemoteWebDriver does NOT open browser inside Jenkins container.
     *
     * Flow:
     *
     * Jenkins Container
     *        │
     *        ▼
     * Selenium Framework
     *        │
     *        ▼
     * RemoteWebDriver
     *        │
     *        │ HTTP request
     *        ▼
     * Selenium Grid : 4444
     *        │
     *        ▼
     * Chrome / Firefox / Edge browser container
     *
     * Therefore Jenkins only executes Java + Maven framework code.
     * Actual browser execution happens inside Selenium container.
     */
    private static WebDriver createRemoteDriver(BrowserType browser) {

        try {

            // Reads Grid URL.
            //
            // Priority:
            // 1. -Dgrid.url JVM parameter
            // 2. config-local.properties
            URL gridUrl = new URL(GridManager.getGridUrl());

            logger.info(
                    "Launching remote {} browser using Grid URL : {}",
                    browser,
                    gridUrl
            );

            switch (browser) {

                case CHROME:

                    // Sends ChromeOptions to Selenium Grid.
                    // Grid creates Chrome browser session remotely.
                    return new RemoteWebDriver(
                            gridUrl,
                            BrowserOptionsFactory.getChromeOptions()
                    );

                case EDGE:

                    // Sends EdgeOptions to Selenium Grid.
                    return new RemoteWebDriver(
                            gridUrl,
                            BrowserOptionsFactory.getEdgeOptions()
                    );

                case FIREFOX:

                    // Sends FirefoxOptions to Selenium Grid.
                    return new RemoteWebDriver(
                            gridUrl,
                            BrowserOptionsFactory.getFirefoxOptions()
                    );

                default:

                    throw new RuntimeException(
                            "Unsupported browser for remote execution : " + browser
                    );
            }

        } catch (MalformedURLException e) {

            // Example invalid URL:
            // localhost:4444
            //
            // Correct URL:
            // http://localhost:4444/wd/hub
            throw new RuntimeException(
                    "Invalid Selenium Grid URL : " +
                            GridManager.getGridUrl(),
                    e
            );
        }
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

