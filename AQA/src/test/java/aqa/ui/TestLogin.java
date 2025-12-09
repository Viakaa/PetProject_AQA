package aqa.ui;

import aqa.DriverPool;
import aqa.bo.UserBO;
import aqa.db.LoginDataProvider;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

@Listeners({aqa.listeners.CustomAllureListener.class, aqa.listeners.CustomListener.class})
public class TestLogin {

    private WebDriver driver;
    private UserBO userBO;

    @BeforeMethod
    public void setUp() {
        driver = DriverPool.getDriver();
        userBO = new UserBO(driver);
    }

    @Test(groups = "ui", dataProvider = "users", dataProviderClass = LoginDataProvider.class)
    public void validLoginAndWatchlistTest(String username, String password) {
        userBO.openLoginPage();

        userBO.login(username, password);
        userBO.goToWatchlist();

        String title = userBO.getPageTitle();
        Assert.assertTrue(title.contains("Watchlist"),
                "Page title should contain 'Watchlist'. Actual: " + title);

        userBO.logout();

        Assert.assertTrue(userBO.getPageTitle().contains("Log out") || userBO.getPageTitle().contains("Wikipedia"),
                "User should be logged out");
    }

    @AfterMethod
    public void tearDown() {
        DriverPool.quitDriver();
    }
}