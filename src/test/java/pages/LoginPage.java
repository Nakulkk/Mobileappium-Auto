package pages;

import base.BaseOf;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class LoginPage extends BaseOf {
    private final By usernameField = AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET");
    private final By passwordField = AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordET");
    private final By loginButton = AppiumBy.id("com.saucelabs.mydemoapp.android:id/loginBtn");

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    public LoginPage enterUsername(String username) {
        type(usernameField, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordField, password);
        return this;
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        click(loginButton);
    }

    public boolean isUsernameVisible() {
        return isDisplayed(usernameField);
    }
}