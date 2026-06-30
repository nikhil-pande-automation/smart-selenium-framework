package com.nikhil.framework.waits;

import com.nikhil.framework.config.ConfigReader;
import com.nikhil.framework.driver.DriverManager;
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

    // Prevent object creation.
    private WaitFactory() {
    }

    //Waits until element becomes visible.
    public static WebElement waitForVisibility(WebElement element) {
        return new WebDriverWait(DriverManager.getDriver(),
                Duration.ofSeconds(ConfigReader.getInstance().getExplicitWait()))
                .until(ExpectedConditions.visibilityOf(element));
    }


}
