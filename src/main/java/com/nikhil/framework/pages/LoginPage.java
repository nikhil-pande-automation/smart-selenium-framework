package com.nikhil.framework.pages;

import org.openqa.selenium.By;

/**
 * Purpose:
 * Represents the Login Page of the application.
 * <p>
 * Responsibilities:
 * - Store Login page locators.
 * - Perform Login page actions.
 * <p>
 * Design:
 * Extends BasePage to reuse common Selenium actions.
 */

public class LoginPage extends BasePage {

    /**
     * Removing the LoginPage constructor as initElements approach is deprecated. hence No need.
     *
     *  //    Constructor to Initializes all @FindBy elements.
     *     public LoginPage() {
     *         PageFactory.initElements(
     *                 DriverManager.getDriver(),
     *                 this);
     *     }
     */

    /**
     * Update to use By locator and to exclude the PageFactory WebElement type code also no need of Constructor anymore.
     * Note: login method will gets update.
     * Existing approach to use @FindBy and the new approach use By
     *
     * @FindBy(name = "username")
     * private WebElement txtUsername;
     *
     */
    private final By txtUsername = By.name("username");
    private final By txtPassword = By.name("password");
    private final By btnLogin = By.xpath("//input[@value='Log In']");


    //    Opens application
    public void open() {
        openApplication();
    }

    /**
     * Old login method which works with @FindBy tag approach
     * <p>
     * public void login(String username, String password) {
     * enterText(txtUsername, username);
     * enterText(txtPassword, password);
     * click(btnLogin);
     * }
     */
//  New Login method: Login is no longer returns void. It returns HomePage because after login we're on the Home page.
    public HomePage login(String username, String password) {
        enterText(txtUsername, username);
        enterText(txtPassword, password);
        click(btnLogin);
        return new HomePage();
    }
}
