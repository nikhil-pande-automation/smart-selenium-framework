package com.nikhil.framework.actions;

import com.nikhil.framework.driver.DriverManager;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public final class KeyboardActions {

    private KeyboardActions() {
    }

    private static Actions getActions() {
        return new Actions(DriverManager.getDriver());
    }

    public static void pressEnter() {
        getActions().sendKeys(Keys.ENTER).perform();
    }

    public static void pressEscape() {
        getActions().sendKeys(Keys.ESCAPE).perform();
    }

    public static void pressTab() {
        getActions().sendKeys(Keys.TAB).perform();
    }

    public static void copy(WebElement element) {
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.chord(Keys.CONTROL, "c"));
    }

    public static void paste(WebElement element) {
        element.sendKeys(Keys.chord(Keys.CONTROL, "v"));
    }
}
