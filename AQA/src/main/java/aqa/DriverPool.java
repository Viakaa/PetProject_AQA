package aqa;

import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import io.github.bonigarcia.wdm.managers.FirefoxDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import aqa.task16.ConfigReader;

public class DriverPool {
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
                    driver = new ChromeDriver();
                    break;
                case "Firefox":
                    FirefoxDriverManager.getInstance().setup();
                    driver = new FirefoxDriver();
                    break;
                default:
                    throw new RuntimeException("Invalid Browser: " + browserType);
            }
        }

        return driver;
    }


    public static void main(String[] args) {
        getDriver().get("https://www.google.com/");
        System.out.println("End...");
    }

    public static synchronized void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

}
