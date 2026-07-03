package com.nikhil.framework.waits;

import com.nikhil.framework.config.ConfigReader;
import com.nikhil.framework.driver.DriverManager;
import com.nikhil.framework.logger.LoggerFactory;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Purpose:
 * Centralizes all explicit wait logic.
 * <p>
 * Why?
 * Avoids duplicate WebDriverWait code
 * across every Page Object.
 * <p>
 * Design:
 * Utility class containing only static methods.
 */

public final class WaitFactory {

    private static final Logger logger =
            LoggerFactory.getLogger(WaitFactory.class);

    // Prevent object creation.
    private WaitFactory() {
    }

    //Waits until element becomes visible.
    public static WebElement waitForVisibility(WebElement element) {
        logger.info("Waiting for visibility of element");
        return new WebDriverWait(DriverManager.getDriver(),
                Duration.ofSeconds(ConfigReader.getInstance().getExplicitWait()))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForClickable(WebElement element) {
        logger.info("Waiting for element to become clickable");
        return new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(
                        ConfigReader.getInstance().getExplicitWait()))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean waitForInvisibility(WebElement element) {
        logger.info("Waiting for element to become Invisible");
        return new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(ConfigReader.getInstance().getExplicitWait()))
                .until(ExpectedConditions.invisibilityOf(element));
    }

    public static boolean waitForTitleContains(String title) {
        logger.info("Waiting for element to get title contains");
        return new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(
                        ConfigReader.getInstance().getExplicitWait()))
                .until(ExpectedConditions.titleContains(title));
    }

    //    Overloaded method for By locator and to exclude the PageFactory WebElement type code
    public static WebElement waitForVisibility(By locator) {
        logger.info("Waiting for visibility of element");
        return new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(
                        ConfigReader.getInstance().getExplicitWait()))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    //    Overloaded method for By locator and to exclude the PageFactory WebElement type code
    public static WebElement waitForClickable(By locator) {
        logger.info("Waiting for element to become clickable");
        return new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(
                        ConfigReader.getInstance().getExplicitWait()))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }
}
