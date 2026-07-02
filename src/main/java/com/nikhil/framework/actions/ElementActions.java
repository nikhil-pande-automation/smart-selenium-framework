package com.nikhil.framework.actions;

import com.nikhil.framework.utils.JavaScriptUtil;
import org.openqa.selenium.WebElement;

public final class ElementActions {

    private ElementActions() {
    }

    /**
     * Clicks an element.
     * <p>
     * Falls back to JavaScript click if normal Selenium click fails.
     *
     * @param element target element
     */
    public static void click(WebElement element) {
        try {
            element.click();
        } catch (Exception exception) {
            JavaScriptUtil.click(element);
        }
    }

//  Implementing methods of element actions in this class. Now BasePage should never directly call Selenium.
//  Instead of element.click(); > use ElementActions.click(element);

    /**
     * Clears existing text and enters new text.
     *
     * @param element target element
     * @param text    value to enter
     */
    public static void enterText(WebElement element, String text) {
        clear(element);
        element.sendKeys(text);
    }

    public static void clear(WebElement element) {
        element.clear();
    }

    /**
     * Returns visible text.
     *
     * @param element target element
     * @return element text
     */
    public static String getText(WebElement element) {
        return element.getText();
    }

    public static boolean isDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    public static String getAttribute(WebElement element, String attributeName) {
        return element.getAttribute(attributeName);
    }

    public static boolean isEnabled(WebElement element) {
        return element.isEnabled();
    }

    public static boolean isSelected(WebElement element) {
        return element.isSelected();
    }




}
