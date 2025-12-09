package aqa.ui;

import aqa.bo.HomeBO;
import aqa.db.TestData;
import aqa.DriverPool;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

@Listeners({aqa.listeners.CustomAllureListener.class, aqa.listeners.CustomListener.class})
public class TestSearch {

    private WebDriver driver;
    private HomeBO homeBO;

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverPool.getDriver();
        homeBO = new HomeBO(driver);
        homeBO.openHomePage();
    }

    @Test(groups = {"ui"},dataProvider = "searchTerms", dataProviderClass = TestData.class)
    public void searchArticleTest(String term, String expectedTitle) {

        homeBO.searchFor(term);

        String actualTitle = homeBO.getFirstHeading();
        Assert.assertEquals(actualTitle, expectedTitle,
                "Article heading must match expected!");

        String currentUrl = driver.getCurrentUrl().toLowerCase();
        Assert.assertTrue(currentUrl.contains(term.split(" ")[0].toLowerCase()),
                "URL should contain search term fragment.");

        String articleContent = homeBO.getArticleContent();
        Assert.assertFalse(articleContent.isEmpty(),
                "Article content should not be empty!");
    }

    @AfterMethod
    public void tearDown() {
        DriverPool.quitDriver();
    }
}
