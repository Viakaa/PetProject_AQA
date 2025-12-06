package aqa.listeners;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static aqa.DriverPool.driver;


public class CustomAllureListener implements ITestListener {
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Success" + result.getName());
        ITestListener.super.onTestSuccess(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Failure" + result.getName());
        ITestListener.super.onTestFailure(result);
        makeScreenshotAttachment();
        makeDOMAttachment();
    }

    @Attachment(value="Page screen", type="image/png")
    private byte[] makeScreenshotAttachment(){
        System.out.println("make Screenshot");
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value="{0}", type="text/plain")
    private String makeDOMAttachment() {
        return driver.getPageSource();
    }


}
