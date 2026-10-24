package reports;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;

public class ExtentReportListener implements ITestListener {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    static {
        String reportPath = System.getProperty("user.dir") + "/target/extent-reports/ExtentReport.html";
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Mobile Automation Test Report");
        spark.config().setReportName("Mobile Automation Suite");
        spark.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
        extentTest.assignCategory(result.getTestClass().getRealClass().getSimpleName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        attachScreenshot(result, Status.PASS);
        test.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        attachScreenshot(result, Status.FAIL);
        test.get().log(Status.FAIL, "Test failed");
        if (result.getThrowable() != null) {
            test.get().log(Status.FAIL, result.getThrowable().getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        attachScreenshot(result, Status.SKIP);
        test.get().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    private void attachScreenshot(ITestResult result, Status status) {
        try {
            Object driverObject = result.getTestContext().getAttribute("driver");
            if (driverObject instanceof AndroidDriver) {
                AndroidDriver driver = (AndroidDriver) driverObject;
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String screenshotPath = System.getProperty("user.dir") + "/target/screenshots/" + result.getName() + "_" + status.toString().toLowerCase() + ".png";
                File targetFile = new File(screenshotPath);
                FileUtils.copyFile(screenshot, targetFile);
                test.get().addScreenCaptureFromPath(screenshotPath, result.getMethod().getMethodName());
            }
        } catch (Exception e) {
            test.get().log(Status.WARNING, "Screenshot not captured: " + e.getMessage());
        }
    }
}
