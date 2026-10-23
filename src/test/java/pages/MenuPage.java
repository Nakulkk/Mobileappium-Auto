package pages;

import base.BaseOf;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class MenuPage extends BaseOf {
    private final By menuIcon = AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuIV");
    private final By loginTab = AppiumBy.accessibilityId("Login Menu Item");

    public MenuPage(AndroidDriver driver) {
        super(driver);
    }

    public MenuPage openMenu() {
        click(menuIcon);
        return this;
    }

    public LoginPage openLogin() {
        click(loginTab);
        return new LoginPage(driver);
    }

    public boolean isMenuOpen() {
        return isDisplayed(loginTab);
    }
}