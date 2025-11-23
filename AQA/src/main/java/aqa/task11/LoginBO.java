package aqa.task11;

import org.openqa.selenium.WebDriver;

public class LoginBO {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    public LoginBO(WebDriver driver) {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
    }

    public boolean login(String user, String pass) {
        loginPage.open();
        loginPage.enterUsername(user);
        loginPage.enterPassword(pass);
        loginPage.clickLogin();

        return inventoryPage.isDisplayed();
    }
}
