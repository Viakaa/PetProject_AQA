package aqa.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;

public class CustomListener implements ITestListener, ISuiteListener, IExecutionListener {
    private static final Logger logger = LogManager.getLogger(CustomListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("=== Test started: " + result.getName() + " ===");
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("=== All tests in '" + context.getName() + "' have finished ===");
    }


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
}
