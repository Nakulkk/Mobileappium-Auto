package stepdefinitions;

import hooks.Hooks;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.LoginPage;
import pages.MenuPage;

import java.time.Duration;

public class LoginSteps {

    private static final String APP_PACKAGE = "com.saucelabs.mydemoapp.android";

    @Given("the app is launched")
    public void theAppIsLaunched() {
        Assert.assertEquals(Hooks.getDriver().getCurrentPackage(), APP_PACKAGE,
                "The Demo app is not in the foreground");
    }

    @When("the user opens the navigation menu and selects {string}")
    public void openMenuAndSelect(String item) {
        Assert.assertEquals(item, "Login", "This scenario expects the Login menu item");
        new MenuPage(Hooks.getDriver()).openMenu().openLogin();
    }

    @When("the user logs in with username {string} and password {string}")
    public void userLogsInWithCredentials(String username, String password) {
        new LoginPage(Hooks.getDriver()).login(username, password);
    }

    @Then("the login screen is displayed")
    public void loginScreenIsDisplayed() {
        Assert.assertTrue(isVisible("nameET"), "Login screen was not displayed");
    }

    @Then("the product catalog is displayed")
    public void productCatalogIsDisplayed() {
        By locator = AppiumBy.androidUIAutomator("new UiSelector().text(\"Products\")");
        Assert.assertTrue(new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(locator))
                .isDisplayed(), "Product catalog was not displayed");
    }

    @Then("the login attempt is rejected and the login form remains displayed")
    public void loginAttemptIsRejectedAndLoginFormRemainsDisplayed() {
        Assert.assertTrue(isVisible("nameET"), "Login form is no longer displayed");
        Assert.assertTrue(isVisible("passwordET"), "Password field is no longer displayed");
        Assert.assertTrue(isVisible("loginBtn"), "Login button is no longer displayed");
    }

    @Then("the username and password fields are visible")
    public void usernameAndPasswordFieldsAreVisible() {
        Assert.assertTrue(isVisible("nameET"), "Username field is not visible");
        Assert.assertTrue(isVisible("passwordET"), "Password field is not visible");
    }

    @Then("the Login button is visible")
    public void loginButtonIsVisible() {
        Assert.assertTrue(isVisible("loginBtn"), "Login button is not visible");
    }

    private boolean isVisible(String resourceId) {
        AndroidDriver driver = Hooks.getDriver();
        By locator = AppiumBy.id(APP_PACKAGE + ":id/" + resourceId);
        return new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(locator))
                .isDisplayed();
    }
}
