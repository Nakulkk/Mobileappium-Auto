package maintest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.appmanagement.ApplicationState;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MenuPage;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

public class LoginTestSause {
    private AndroidDriver driver;
   private static final String APP_PATH = "C:\\Users\\CCST\\Desktop\\MobileAutomation101\\apps\\mda-2.3.0-27.apk";

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

   private static boolean isAppPackagePresent() {
       return new File(APP_PATH).exists();
   }

   @BeforeMethod
   public void startSession(ITestContext context) throws MalformedURLException {
       if (!isAppiumServerAvailable()) {
           throw new SkipException("Appium server is not running on localhost:4723");
       }
       if (!isAppPackagePresent()) {
           throw new SkipException("APK not found at: " + APP_PATH);
       }

       UiAutomator2Options options = new UiAutomator2Options();
       options.setPlatformName("Android")
               .setAutomationName("uiautomator2")
               .setDeviceName("Android Device")
               .setUdid("MNSWAQAYMVSKYTLV")
               .setPlatformVersion("12")
               .setApp(APP_PATH)
               .setAppPackage("com.saucelabs.mydemoapp.android")
               .setAppActivity("com.saucelabs.mydemoapp.android.view.activities.SplashActivity")
               .setAutoGrantPermissions(true);

       driver = new AndroidDriver(URI.create("http://localhost:4723").toURL(), options);
       context.setAttribute("driver", driver);
   }

   @Test(priority = 1)
   public void testSauceLabsLogin() {
       MenuPage menuPage = new MenuPage(driver);
       LoginPage loginPage = menuPage.openMenu().openLogin();
       loginPage.isUsernameVisible();
       loginPage.login("bod@example.com", "Password123");
   }

   @Test(priority = 2)
   public void runappinbaground() {
       MenuPage menuPage = new MenuPage(driver);
       LoginPage loginPage = menuPage.openMenu().openLogin();
       driver.runAppInBackground(Duration.ofSeconds(5));
       driver.terminateApp("com.saucelabs.mydemoapp.android");
       driver.activateApp("com.saucelabs.mydemoapp.android");
       ApplicationState appState = driver.queryAppState("com.saucelabs.mydemoapp.android");
       System.out.println("Application state: " + appState);
       loginPage.isUsernameVisible();
   }

   @AfterMethod
   public void tearDown() {
       if (driver != null) {
           driver.quit();
       }
   }
}