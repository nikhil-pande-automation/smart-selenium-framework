package com.nikhil.framework.utils;

import com.nikhil.framework.driver.DriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public final class JavaScriptUtil {

    /**
     * Utility class for executing JavaScript operations.
     * <p>
     * Use this class only when Selenium WebDriver methods
     * cannot reliably perform an action.
     */

    private JavaScriptUtil() {

    }

    /**
     * Returns JavaScriptExecutor for the current browser.
     *
     * @return JavaScriptExecutor instance
     * and private because this method will be in use for another methods of this call
     * so this method shouldn't be directly use into another class so marked as private.
     */
    private static JavascriptExecutor getJavaScriptExecutor() {
        return (JavascriptExecutor) DriverManager.getDriver();
    }

    /**
     * Performs a JavaScript click on the given element.
     * <p>
     * Use this method only when Selenium's normal click()
     * cannot interact with the element reliably.
     *
     * @param element target WebElement
     *                JavaScriptUtil accept a WebElement instead of a By
     *                Because JavaScriptUtil is responsible only for executing JavaScript. Locating elements is already handled
     *                by the Page layer and WaitFactory. Accepting a WebElement keeps JavaScriptUtil focused on a
     *                single responsibility and avoids duplicating locator and synchronization logic.
     *                Rule for JavaScriptUtil
     *                <p>
     *                Every public method should:
     *                <p>
     *                Accept only the data it needs.
     *                Execute one JavaScript operation.
     *                Never contain Selenium waiting logic.
     *                Never locate elements.
     *                Never catch exceptions (unless we have a very good reason).
     */
    public static void click(WebElement element) {

        getJavaScriptExecutor().executeScript(
                "arguments[0].click();",
                element);
    }

    /**
     * Scrolls the page until the specified element
     * becomes visible in the browser viewport.
     *
     * @param element target WebElement
     *                scrollIntoView(true): It scrolls the page until the element becomes visible. true aligns the element near
     *                the top of the viewport.
     */
    public static void scrollIntoView(WebElement element) {

        getJavaScriptExecutor().executeScript(
                "arguments[0].scrollIntoView(true);",
                element);

    }

    /**
     * Scrolls to the top of the current page.
     */
    public static void scrollToTop() {

        getJavaScriptExecutor().executeScript(
                "window.scrollTo(0,0);");

    }

    /**
     * Scrolls to the bottom of the current page.
     */
    public static void scrollToBottom() {

        getJavaScriptExecutor().executeScript(
                "window.scrollTo(0, document.body.scrollHeight);");

    }

    /**
     * Draws a red border around the specified element.
     * <p>
     * Useful during debugging.
     *
     * @param element target WebElement
     */
    public static void drawBorder(WebElement element) {

        getJavaScriptExecutor().executeScript(
                "arguments[0].style.border='3px solid red';",
                element);

    }

    /**
     * Flashes the element by changing its background color.
     * <p>
     * Useful for debugging.
     *
     * @param element target WebElement
     */
    public static void flash(WebElement element) {

        String script =
                "arguments[0].style.background='yellow';";

        getJavaScriptExecutor().executeScript(script, element);

    }


}
