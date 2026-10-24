package hooks;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.io.File;
import java.net.URI;

public class Hooks {
    private static final String APP_PATH =
            "C:\\Users\\CCST\\Desktop\\MobileAutomation101\\apps\\mda-2.3.0-27.apk";
    private static final ThreadLocal<AndroidDriver> DRIVER = new ThreadLocal<>();

    @Before
    public void startApp() throws Exception {
        if (!new File(APP_PATH).isFile()) {
            throw new IllegalStateException("APK not found: " + APP_PATH);
        }

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("uiautomator2")
                .setDeviceName("Android Device")
                .setUdid("MNSWAQAYMVSKYTLV")
                .setPlatformVersion("12")
                .setApp(APP_PATH)
                .setAppPackage("com.saucelabs.mydemoapp.android")
                .setAppActivity("com.saucelabs.mydemoapp.android.view.activities.SplashActivity")
                .setAutoGrantPermissions(true);

        DRIVER.set(new AndroidDriver(URI.create("http://localhost:4723").toURL(), options));
    }

    public static AndroidDriver getDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Appium driver is not initialized. Check the Cucumber hooks configuration.");
        }
        return driver;
    }

    @After
    public void closeApp() {
        AndroidDriver driver = DRIVER.get();
        try {
            if (driver != null) {
                driver.quit();
            }
        } finally {
            DRIVER.remove();
        }
    }
}
