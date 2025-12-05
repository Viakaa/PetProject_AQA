package aqa.listeners;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static aqa.DriverPool.driver;

public class CustomAllureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Failure: " + result.getName());

        if(driver != null){
            makeScreenshotAttachment();
            makeDOMAttachment(result.getName());
        }
    }

    @Attachment(value="Page screen", type="image/png")
    private byte[] makeScreenshotAttachment(){
        System.out.println("Making screenshot...");
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value="Page DOM: {0}", type="text/plain")
    private String makeDOMAttachment(String testName) {
        System.out.println("Attaching DOM...");
        return driver.getPageSource();
    }
}
