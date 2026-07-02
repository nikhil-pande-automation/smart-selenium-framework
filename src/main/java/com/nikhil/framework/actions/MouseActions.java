package com.nikhil.framework.actions;

import org.openqa.selenium.WebElement;

/**
 * Performs reusable mouse operations.
 */

public final class MouseActions {

    private MouseActions() {
    }

//    Commenting below getAction() method as ActionFactory.java added
//    private static Actions getAction() {
//        return new Actions(DriverManager.getDriver());
//    }

    //    Moves mouse over an element.
    public static void hover(WebElement element) {
        ActionFactory.getActions()
                .moveToElement(element).perform();
    }

    public static void doubleClick(WebElement element) {
        ActionFactory.getActions()
                .doubleClick(element).perform();
    }

    public static void rightClick(WebElement element) {
        ActionFactory.getActions().contextClick(element).perform();
    }

    public static void dragAndDrop(WebElement element, WebElement target) {
        ActionFactory.getActions().dragAndDrop(element, target).perform();
    }

    public static void clickAndHold(WebElement element) {
        ActionFactory.getActions().clickAndHold(element).perform();
    }

    public static void release(WebElement element) {
        ActionFactory.getActions().release(element).perform();
    }

}
