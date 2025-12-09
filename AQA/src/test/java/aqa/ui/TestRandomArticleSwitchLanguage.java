package aqa.ui;

import aqa.DriverPool;
import aqa.bo.NavigationBO;
import aqa.bo.UserBO;
import aqa.db.LoginDataProvider;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Listeners({aqa.listeners.CustomAllureListener.class})
public class TestRandomArticleSwitchLanguage {

    private WebDriver driver;
    private UserBO userBO;
    private NavigationBO navBO;

    @BeforeMethod
    public void setUp() {
        driver = DriverPool.getDriver();
        userBO = new UserBO(driver);
        navBO = new NavigationBO(driver);
    }

    @Test(groups = "ui", dataProvider = "users", dataProviderClass = LoginDataProvider.class)
    public void randomArticleLanguageSwitchTest(String username, String password) {
        userBO.openLoginPage();
        userBO.login(username, password);

        navBO.ClickRandomArticle();
        String initialUrl = navBO.getCurrentUrl();
        System.out.println("Random article opened: " + driver.getTitle());

        String expectedUrl = navBO.switchArticleToFirstLanguage();

        String currentUrl = navBO.getCurrentUrl();

        currentUrl = URLDecoder.decode(currentUrl, StandardCharsets.UTF_8);
        expectedUrl = URLDecoder.decode(expectedUrl, StandardCharsets.UTF_8);

        System.out.println("Expected (clicked): " + expectedUrl);
        System.out.println("Actual (browser):   " + currentUrl);

        Assert.assertNotEquals(currentUrl, initialUrl,
                "The URL should have changed after switching language.");

        String cleanExpected = expectedUrl.replace("https://", "").replace("http://", "");
        String cleanCurrent = currentUrl.replace("https://", "").replace("http://", "");

        Assert.assertTrue(cleanCurrent.contains(cleanExpected) || cleanExpected.contains(cleanCurrent),
                "Current URL does not match the language link we clicked.");

    }

    @AfterMethod
    public void tearDown() {
        DriverPool.quitDriver();
    }
}