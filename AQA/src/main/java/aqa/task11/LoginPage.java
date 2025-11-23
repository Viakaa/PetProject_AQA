package aqa.task11;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginBtn = By.id("login-button");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://www.saucedemo.com/");
    }

    public void enterUsername(String text) {
        driver.findElement(usernameField).sendKeys(text);
    }

    public void enterPassword(String text) {
        driver.findElement(passwordField).sendKeys(text);
    }

    public void clickLogin() {
        driver.findElement(loginBtn).click();
    }
}
