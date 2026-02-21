package utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ExtentreportListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static WebDriver driver; // You need to set this driver from your test classes

    @Override
    public void onStart(ITestContext context) {
        
        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());

        // Report file path with timestamp
        String reportPath = System.getProperty("user.dir") + "/test-output/extent-report-" + timestamp + ".html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);

    	
        // Report title & name
        reporter.config().setDocumentTitle("Automation Test Report");
        reporter.config().setReportName("My Project Test Results");

        extent = new ExtentReports();
        extent.attachReporter(reporter);

        // Add system/environment info
        extent.setSystemInfo("OS", System.getProperty("Windows"));
        extent.setSystemInfo("User", System.getProperty("Joginder"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Device", "Windows 11");
        extent.setSystemInfo("Tester", "QA Test Engineer");  
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());

        // Capture screenshot on failure
        if (driver != null) {
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String screenshotPath = System.getProperty("user.dir") + "/test-output/screenshots/" 
                        + result.getMethod().getMethodName() + ".png";

                Files.copy(screenshot.toPath(), new File(screenshotPath).toPath());

                test.get().addScreenCaptureFromPath(screenshotPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    // Utility method to set driver from test classes
    public static void setDriver(WebDriver driverInstance) {
        driver = driverInstance;
    }
}