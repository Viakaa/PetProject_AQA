package aqa.task10;

import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;

public class Task10Test {
    WebDriver driver;

    @BeforeTest
    void setUp(){
        File chromeDriverFile = new File("java_aqa/driver/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver",
                chromeDriverFile.getAbsoluteFile().getAbsolutePath());
        options.addArguments("--headless=new");

        ChromeDriverManager.getInstance().setup();
         driver = new ChromeDriver(options);
    }


    @Test
    void task10Test() {

        driver.get("https://www.demoblaze.com/");

        WebElement clickOnContact = driver.findElement(By.xpath("//a[.=\"Contact\"]"));

        Assert.assertTrue(clickOnContact.isDisplayed());
        clickOnContact.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        WebElement clickOnContactEmail = driver.findElement(By.id("recipient-email"));

        Assert.assertTrue(clickOnContactEmail.isDisplayed());
        clickOnContactEmail.click();
        clickOnContactEmail.sendKeys("Meow");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        WebElement clickOnSendMessage = driver.findElement(By.xpath("//button[.=\"Send message\"]"));

        Assert.assertTrue(clickOnSendMessage.isDisplayed());
        clickOnSendMessage.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

        @AfterTest
        void close(){
            driver.quit();
            }
}
