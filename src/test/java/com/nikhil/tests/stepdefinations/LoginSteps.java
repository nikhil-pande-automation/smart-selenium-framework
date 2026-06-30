package com.nikhil.tests.stepdefinations;

import com.nikhil.framework.config.ConfigReader;
import com.nikhil.framework.pages.LoginPage;
import io.cucumber.java.en.Given;

public class LoginSteps {

    LoginPage loginPage = new LoginPage();

    @Given("User launches the application")
    public void userLaunchesTheApplication() {

        loginPage.open();
        loginPage.login(ConfigReader.getInstance().getUsername(),
                ConfigReader.getInstance().getPassword());

    }
}
