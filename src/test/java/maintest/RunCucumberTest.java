package maintest;


import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        features = "src/main/resources/feature",
        glue = {"stepdefinitions", "hooks"},
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        dryRun = false
)
public class RunCucumberTest extends AbstractTestNGCucumberTests {
}
