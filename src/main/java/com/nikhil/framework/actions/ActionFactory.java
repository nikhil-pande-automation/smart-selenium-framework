package com.nikhil.framework.actions;

import com.nikhil.framework.driver.DriverManager;
import org.openqa.selenium.interactions.Actions;

public final class ActionFactory {

    private ActionFactory() {

    }

    /**
     * Returns Actions object for current browser.
     *
     * @return Actions instance
     */
    public static Actions getActions() {

        return new Actions(DriverManager.getDriver());

    }
}
