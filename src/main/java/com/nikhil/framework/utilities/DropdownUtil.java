package com.nikhil.framework.utilities;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public final class DropdownUtil {

    private DropdownUtil() {

    }

    private static Select getSelect(WebElement element) {
        return new Select(element);
    }

    public static void selectByVisibleText(WebElement element, String text) {
        getSelect(element).selectByVisibleText(text);
    }

    public static void selectByValue(WebElement element, String value) {
        getSelect(element).selectByValue(value);
    }

    public static void selectByIndex(WebElement element, int index) {
        getSelect(element).selectByIndex(index);
    }
}
