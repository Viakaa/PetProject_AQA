package aqa;

import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import io.github.bonigarcia.wdm.managers.FirefoxDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DriverPool {
    private static final Logger logger = LoggerFactory.getLogger(DriverPool.class);
    public static WebDriver driver;

    public static synchronized WebDriver getDriver() {
        String browserType = ConfigReader.GetProperty("browserType");
        if (browserType == null || browserType.isEmpty()) {
            browserType = "Chrome";
        }

        if (driver == null) {
            switch (browserType) {
                case "Chrome":
                    ChromeDriverManager.getInstance().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "Firefox":
                    FirefoxDriverManager.getInstance().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--start-maximized");
                    driver = new FirefoxDriver(firefoxOptions);
                    break;

                default:
                    throw new RuntimeException("Invalid Browser: " + browserType);
            }
        }

        return driver;
    }

    public static void main(String[] args) {
        getDriver().get("https://www.google.com/");
        logger.info("End...");

    }

    public static synchronized void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
