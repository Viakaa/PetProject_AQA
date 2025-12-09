package aqa.listeners;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;

public class CustomListener implements ITestListener, ISuiteListener, IExecutionListener {
    private static final Logger logger = LogManager.getLogger(CustomListener.class);

    @Override
    public void onStart(ISuite suite) {
        logger.info("=== Starting test suite: " + suite.getName() + " ===");
    }

    @Override
    public void onFinish(ISuite suite) {
        logger.info("=== Test suite finished: " + suite.getName() + " ===");
    }

    @Override
    public void onExecutionStart() {
        logger.info("=== TestNG execution started ===");
    }

    @Override
    public void onExecutionFinish() {
        logger.info("=== TestNG execution finished ===");
    }

    // ================= Test Logging =================
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("=== Test started: " + result.getName() + " ===");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("=== Test PASSED: " + result.getName() + " ===");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("=== Test FAILED: " + result.getName() + " ===", result.getThrowable());

        Object response = result.getAttribute("responseBody");
        if(response != null){
            Allure.addAttachment("API Response", "application/json", response.toString());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("=== Test SKIPPED: " + result.getName() + " ===");
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("=== All tests in '" + context.getName() + "' have finished ===");
    }
}
