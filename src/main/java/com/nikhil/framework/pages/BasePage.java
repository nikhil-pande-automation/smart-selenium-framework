package com.nikhil.framework.pages;

import com.nikhil.framework.config.ConfigReader;
import com.nikhil.framework.driver.DriverManager;
import com.nikhil.framework.waits.WaitFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Purpose:
 * Contains common Selenium actions shared by all Page Objects.
 * <p>
 * Why?
 * Avoids duplicate click(), sendKeys(), getText()
 * implementation in every page.
 * <p>
 * Design:
 * Parent class for all Page Objects.
 */

public class BasePage {

    //    Opens application URL from config.properties.
    protected void openApplication() {
        DriverManager.getDriver().get(ConfigReader.getInstance().getUrl());
    }

    protected String getTitle() {
        return DriverManager.getDriver().getTitle();
    }

    protected String getCurrentUrl() {
        return DriverManager
                .getDriver().getCurrentUrl();
    }

    //    Overloaded method for By locator and to exclude the PageFactory WebElement type code
    protected void click(By locator) {
        WaitFactory.waitForClickable(locator).click();
    }

    //    Overloaded method for By locator and to exclude the PageFactory WebElement type code
    protected void enterText(By locator, String text) {
        WebElement element = WaitFactory.waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    //    Overloaded method for By locator and to exclude the PageFactory WebElement type code
    protected String getText(By locator) {
        return WaitFactory.waitForVisibility(locator).getText();
    }

    /**
     * Existing implementation idDisplayed Method
     * <p>
     * protected boolean isDisplayed(WebElement element) {
     * return WaitFactory.waitForVisibility(element).isDisplayed();
     * }
     */

    //    Overloaded method for By locator and to exclude the PageFactory WebElement type code
    protected boolean isDisplayed(By locator) {
        return WaitFactory.waitForVisibility(locator).isDisplayed();
    }
}
