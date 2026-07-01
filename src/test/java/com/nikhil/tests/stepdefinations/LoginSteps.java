package com.nikhil.tests.stepdefinations;

import com.nikhil.framework.config.ConfigReader;
import com.nikhil.framework.pages.HomePage;
import com.nikhil.framework.pages.LoginPage;
import io.cucumber.java.en.Given;
import org.testng.Assert;

/**
 * Instead of this:
 *
 * @FindBy(id="username") private WebElement txtUsername;
 * <p>
 * enterText(txtUsername, username);
 * <p>
 * We'll use:
 * <p>
 * private final By txtUsername = By.name("username");
 * <p>
 * enterText(txtUsername, username);
 * <p>
 * Now BasePage becomes much more powerful.
 * <p>
 * BasePage (New Design)
 * <p>
 * Instead of
 * <p>
 * protected void click(WebElement element)
 * <p>
 * we'll have
 * <p>
 * protected void click(By locator)
 * <p>
 * Internally
 * <p>
 * WaitFactory.waitForClickable(locator).click();
 * WaitFactory
 * <p>
 * Instead of
 * <p>
 * waitForVisibility(WebElement)
 * <p>
 * we'll overload methods.
 * <p>
 * waitForVisibility(By locator)
 * <p>
 * Internally
 * <p>
 * driver.findElement(locator)
 * <p>
 * Benefits:
 * No cached WebElements.
 * No stale references.
 */

public class LoginSteps {

    LoginPage loginPage = new LoginPage();

    /**
     * Commenting below implementation as new method created in LoginPage and HomePage
     *
     * @Given("User launches the application")
     * public void userLaunchesTheApplication() {
     * <p>
     * loginPage.open();
     * loginPage.login(ConfigReader.getInstance().getUsername(),
     * ConfigReader.getInstance().getPassword());
     * <p>
     * }
     */

    @Given("User launches the application")
    public void userLaunchesTheApplication() {
        loginPage.open();
        HomePage homePage = loginPage.login(ConfigReader.getInstance().getUsername(),
                ConfigReader.getInstance().getPassword());
        Assert.assertTrue(homePage.isLoginSuccessful());

    }

}
