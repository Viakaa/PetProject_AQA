package aqa.listeners;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static aqa.DriverPool.driver;

public class CustomAllureListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(CustomAllureListener.class);

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test SUCCESS: {}", result.getName());
        ITestListener.super.onTestSuccess(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test FAILURE: {}", result.getName());
        ITestListener.super.onTestFailure(result);
        makeScreenshotAttachment();
        makeDOMAttachment();
    }

    @Attachment(value="Page screen", type="image/png")
    private void makeScreenshotAttachment(){
        logger.info("Taking screenshot for Allure attachment");
        ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value="{0}", type="text/plain")
    private void makeDOMAttachment() {
        logger.info("Attaching page source to Allure report");
        driver.getPageSource();
    }
}
