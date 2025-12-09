package aqa.bo;

import aqa.po.LoginPage;
import aqa.po.PageHeader;
import org.openqa.selenium.WebDriver;

public class UserBO {
    private final WebDriver driver;
    private final LoginPage loginPage;
    private final PageHeader userHeaderPage;

    public UserBO(WebDriver driver) {
        this.driver = driver;
        loginPage = new LoginPage(driver);
        userHeaderPage = new PageHeader(driver);
    }

    public void openLoginPage() {
        driver.get("https://en.wikipedia.org/w/index.php?title=Special:UserLogin");
    }

    public void login(String username, String password) {
        loginPage.login(username, password);
    }

    public void goToWatchlist() {
        userHeaderPage.openWatchlist();
    }

    public void logout() {
        userHeaderPage.logout();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}