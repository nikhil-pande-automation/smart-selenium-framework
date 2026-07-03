package com.nikhil.framework.actions;

import com.nikhil.framework.driver.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Performs reusable mouse operations.
 */

public final class MouseActions {

    private MouseActions() {
    }

    private static Actions getAction() {
        return new Actions(DriverManager.getDriver());
    }

    //    Moves mouse over an element.
    public static void hover(WebElement element) {
        getAction().moveToElement(element).perform();
    }

    public static void doubleClick(WebElement element) {
        getAction().doubleClick(element).perform();
    }

    public static void rightClick(WebElement element) {
        getAction().contextClick(element).perform();
    }

    public static void dragAndDrop(WebElement element, WebElement target) {
        getAction().dragAndDrop(element, target).perform();
    }

    public static void clickAndHold(WebElement element) {
        getAction().clickAndHold(element).perform();
    }

    public static void release(WebElement element) {
        getAction().release(element).perform();
    }

}
