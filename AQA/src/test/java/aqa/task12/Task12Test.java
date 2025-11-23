package aqa.task12;

import aqa.DriverPool;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class Task12Test {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = DriverPool.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void loginTest() {
        LoginBO loginBO = new LoginBO(driver);
        loginBO.login("standard_user", "secret_sauce");

        // Явне очікування появи елемента на сторінці inventory
        boolean loggedIn = wait.until(ExpectedConditions.urlContains("inventory.html"));
        assertTrue(loggedIn, "Login failed or URL mismatch");
    }

    @AfterMethod
    public void tearDown() {
        DriverPool.quitDriver();
    }
}
