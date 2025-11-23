package aqa.task12;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class LoginBO {

    private final LoginPageObject page;

    public LoginBO(WebDriver driver) {
        this.page = new LoginPageObject(driver);
    }

    @Step("Login as user {username}")
    public void login(String username, String password) {
        page.enterUsername(username);
        page.enterPassword(password);
        page.clickLogin();
    }
}
