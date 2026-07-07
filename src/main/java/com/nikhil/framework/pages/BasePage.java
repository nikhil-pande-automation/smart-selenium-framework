package com.nikhil.framework.pages;

import com.nikhil.framework.actions.ElementActions;
import com.nikhil.framework.actions.KeyboardActions;
import com.nikhil.framework.actions.MouseActions;
import com.nikhil.framework.config.ConfigReader;
import com.nikhil.framework.driver.DriverManager;
import com.nikhil.framework.waits.WaitFactory;
import org.openqa.selenium.By;

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

    // Browser Operations/methods

    //    Opens application URL from config-local.properties.
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
    //    Because we are now using ElementActions.click. BasePage no longer knows how the click is performed.

    // Element Operations/methods
    protected void click(By locator) {
        ElementActions.click(
                WaitFactory.waitForClickable(locator));
    }

    //    Overloaded method for By locator and to exclude the PageFactory WebElement type code
    protected void enterText(By locator, String text) {
        ElementActions.enterText(WaitFactory.waitForVisibility(locator), text);
    }

    //    Overloaded method for By locator and to exclude the PageFactory WebElement type code
    //    And after this ElementActions refactoring, BasePage won't need JavaScriptUtil directly anymore—
    //    it will use ElementActions, so that import can be removed.
    protected String getText(By locator) {
        return ElementActions.getText(WaitFactory.waitForVisibility(locator));
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
        return ElementActions.isDisplayed(WaitFactory.waitForVisibility(locator));
    }

    // Mouse Operations/methods
    protected void hover(By locator) {
        MouseActions.hover(WaitFactory.waitForVisibility(locator));
    }

    protected void doubleClick(By locator) {
        MouseActions.doubleClick(WaitFactory.waitForClickable(locator));
    }

    protected void rightClick(By locator) {
        MouseActions.rightClick(WaitFactory.waitForClickable(locator));
    }

    protected void clickAndHold(By locator) {
        MouseActions.clickAndHold(WaitFactory.waitForClickable(locator));
    }

    protected void release(By locator) {
        MouseActions.release(WaitFactory.waitForClickable(locator));
    }

    protected void dragAndDrop(By source, By target) {
        MouseActions.dragAndDrop(WaitFactory.waitForVisibility(source), WaitFactory.waitForVisibility(target));
    }

    // Keyboard Operations/methods

    protected void pressEnter() {
        KeyboardActions.pressEnter();
    }

    protected void pressTab() {
        KeyboardActions.pressTab();
    }

    protected void pressEscape() {
        KeyboardActions.pressEscape();
    }



}
