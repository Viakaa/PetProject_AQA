package aqa.ui;

import aqa.DriverPool;
import aqa.bo.NavigationBO;
import aqa.db.TestData;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

@Listeners({aqa.listeners.CustomAllureListener.class, aqa.listeners.CustomListener.class})
public class TestArticlesLink {

    private WebDriver driver;
    private NavigationBO navBO;

    @BeforeMethod
    public void setUp() {
        driver = DriverPool.getDriver();
        navBO = new NavigationBO(driver);
    }

    @Test(groups = "ui",dataProvider = "searchTerms", dataProviderClass = TestData.class)
    public void seleniumOfficialSiteNavigationTest(String term, String expectedTitle,  String expectedUrl) {
        navBO.openHomePage();

        navBO.searchMenu(term);

        navBO.clickArticle();

        Assert.assertTrue(navBO.getHeading().contains(expectedTitle),
                "Article heading should should contain '" + expectedTitle + "'");

        navBO.openExternalLink(expectedUrl);
        String url = navBO.getCurrentUrl();
        Assert.assertTrue(url.contains(expectedUrl),
                "User should land on official site containing '" + expectedUrl + "'. Actual: " + url);
    }

    @AfterMethod
    public void tearDown() {
        DriverPool.quitDriver();
    }
}