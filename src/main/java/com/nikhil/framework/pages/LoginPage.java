package com.nikhil.framework.pages;

import com.nikhil.framework.driver.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    //    Constructor to Initializes all @FindBy elements.
    public LoginPage() {
        PageFactory.initElements(
                DriverManager.getDriver(),
                this);
    }

    @FindBy(name = "username")
    private WebElement txtUsername;

    @FindBy(name = "password")
    private WebElement txtPassword;

    @FindBy(xpath = "//input[@value='Log In']")
    private WebElement btnLogin;

    //    Opens application
    public void open() {
        openApplication();
    }

    public void login(String username, String password) {

        enterText(txtUsername, username);
        enterText(txtPassword, password);
        click(btnLogin);

    }
}
