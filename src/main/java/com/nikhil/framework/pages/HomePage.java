package com.nikhil.framework.pages;

import org.openqa.selenium.By;

public class HomePage extends BasePage {

    private final By lblAccountsOverview =
            By.xpath("//h1[contains(text(),'Accounts Overview')]");

    public boolean isLoginSuccessful() {
        return isDisplayed(lblAccountsOverview);
    }
}
