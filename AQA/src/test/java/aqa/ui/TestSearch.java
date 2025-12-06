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
    public void searchArticleTest(String term, String expectedTitle) throws InterruptedException {
        homeBO.searchFor(term);
        String actualTitle = homeBO.getFirstHeading();
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @AfterMethod
    public void tearDown() {
        DriverPool.quitDriver();
    }
}
