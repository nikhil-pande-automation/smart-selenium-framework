package com.nikhil.framework.pages;

import com.nikhil.framework.config.ConfigReader;
import com.nikhil.framework.driver.DriverManager;
import com.nikhil.framework.waits.WaitFactory;
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


    //    Waits for element and performs click.
    protected void click(WebElement element) {
        WaitFactory.waitForVisibility(element).click();
    }

    //   Waits for element and enters text.
    protected void enterText(WebElement element, String text) {
        WebElement webElement = WaitFactory.waitForVisibility(element);
        webElement.click();
        webElement.sendKeys(text);
    }

    //    Waits for element and returns text.
    protected String getText(WebElement element) {
        return WaitFactory.waitForVisibility(element).getText();
    }

    //    Opens application URL from config.properties.
    protected void openApplication() {
        DriverManager.getDriver().get(ConfigReader.getInstance().getUrl());
    }

}
