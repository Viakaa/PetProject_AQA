package aqa.task10;

//General
//1. Set up ChromeDriver using the driver file and properties.
//2. Set up ChromeDriver using DriverManager.
//3. Navigate to the page assigned to your variant.
//4. Select any three different elements.
//5. Interact with all the selected elements.
//6. Verify the visibility of each element using assertions.
//7. Wrap all these steps into a TestNG test case.

import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class Task10WebDriverManager {
    public static void main(String[] args) {
        File chromeDriverFile = new File("java_aqa/driver/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver",
                chromeDriverFile.getAbsoluteFile().getAbsolutePath());
//        options.addArguments("--headless=new");

        ChromeDriverManager.getInstance().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.demoblaze.com/");

        WebElement clickOnContact = driver.findElement(By.xpath("//a[.=\"Contact\"]"));
        clickOnContact.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        WebElement clickOnInput = driver.findElement(By.id("recipient-email"));
        clickOnInput.click();
        clickOnInput.sendKeys("Meow");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        WebElement clickOnContactEmail = driver.findElement(By.xpath("//button[.=\"Send message\"]"));
        clickOnContactEmail.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.quit();
    }
}
