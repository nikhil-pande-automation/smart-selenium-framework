package com.nikhil.framework.actions;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public final class KeyboardActions {

    private KeyboardActions() {
    }

    //    Commenting below getAction() method as ActionFactory.java added,
    //    Because tomorrow if we decide to customize Actions creation, we change one class.
//    private static Actions getActions() {
//        return new Actions(DriverManager.getDriver());
//    }

    public static void pressEnter() {
        ActionFactory.getActions().sendKeys(Keys.ENTER).perform();
    }

    public static void pressEscape() {
        ActionFactory.getActions().sendKeys(Keys.ESCAPE).perform();
    }

    public static void pressTab() {
        ActionFactory.getActions().sendKeys(Keys.TAB).perform();
    }

    public static void copy(WebElement element) {
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.chord(Keys.CONTROL, "c"));
    }

    public static void paste(WebElement element) {
        element.sendKeys(Keys.chord(Keys.CONTROL, "v"));
    }
}
