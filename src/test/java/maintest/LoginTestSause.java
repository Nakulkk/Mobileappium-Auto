package maintest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MenuPage;

import java.net.MalformedURLException;
import java.net.URI;

public class LoginTestSause {
    private AndroidDriver driver;

    @BeforeMethod
    public void startSession() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android")
                .setAutomationName("uiautomator2")
                .setDeviceName("Android Device")
                .setUdid("MNSWAQAYMVSKYTLV")
                .setPlatformVersion("12")
                .setApp("C:\\Users\\CCST\\Desktop\\MobileAutomation101\\apps\\mda-2.3.0-27.apk")
                .setAppPackage("com.saucelabs.mydemoapp.android")
                .setAppActivity("com.saucelabs.mydemoapp.android.view.activities.SplashActivity")
                .setAutoGrantPermissions(true);

        driver = new AndroidDriver(URI.create("http://localhost:4723").toURL(), options);
    }

    @Test
    public void testSauceLabsLogin() {
        MenuPage menuPage = new MenuPage(driver);
        LoginPage loginPage = menuPage.openMenu().openLogin();

        loginPage.isUsernameVisible();
        loginPage.login("bod@example.com", "Password123");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}