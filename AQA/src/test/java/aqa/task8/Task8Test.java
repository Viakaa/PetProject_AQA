package aqa.task8;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Task8Test {

    @DataProvider
    public Object[][] loginData() {
        return new Object[][]{
                {"standard_user", "secret_sauce", true},
                {"locked_out_user", "secret_sauce", false},
                {"wrong_user", "wrong_pass", false}
        };
    }

    @Test
    public void loginSuccessTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"), "Login should succeed");

        driver.quit();
    }

    @Test(dataProvider = "loginData")
    public void loginWithDataProvider(String username, String password, boolean shouldPass) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();

        String currentUrl = driver.getCurrentUrl();

        if (shouldPass) {
            Assert.assertTrue(currentUrl.contains("inventory.html"), "Login should succeed for " + username);
        } else {
            Assert.assertFalse(currentUrl.contains("inventory.html"), "Login should fail for " + username);
        }

        driver.quit();
    }
}