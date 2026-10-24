package maintest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

public class settingsapp {
    private AndroidDriver driver;
    private static final String APP_PATH = "C:\\Users\\CCST\\Desktop\\MobileAutomation101\\apps\\ApiDemos-debug.apk";

    private static boolean isAppiumServerAvailable() {
        try {
            URL url = URI.create("http://localhost:4723/status").toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            return connection.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    @BeforeMethod
    public void startSession() throws MalformedURLException {
        if (!isAppiumServerAvailable()) {
            throw new SkipException("Appium server is not running on localhost:4723");
        }
        if (!new File(APP_PATH).exists()) {
            throw new SkipException("APK not found at: " + APP_PATH);
        }

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android")
                .setAutomationName("uiautomator2")
                .setDeviceName("Android Device")
                .setUdid("MNSWAQAYMVSKYTLV")
                .setPlatformVersion("12")
                .setApp(APP_PATH)
                .setAutoGrantPermissions(true);

        driver = new AndroidDriver(URI.create("http://localhost:4723").toURL(), options);
    }

    @Test(priority = 1)
    public void testrotatescreen() {
        driver.rotate(org.openqa.selenium.ScreenOrientation.LANDSCAPE);
    }

    @Test(priority = 2)
    public void runappinbaground() {
        driver.runAppInBackground(Duration.ofSeconds(5));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}