package aqa.ui;

import aqa.bo.SearchBO;
import aqa.bo.HomeBO;
import aqa.DriverPool;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class TestSearchResultHeading {

    private WebDriver driver;
    private SearchBO searchBO;
    private HomeBO homeBO;


    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverPool.getDriver();
        searchBO = new SearchBO(driver);
        homeBO = new HomeBO(driver);
        homeBO.openHomePage();
    }

    @Test
    public void searchResultHeadingTest() {
        String searchTerm = "Selenium (software)";
        String expectedHeading = "Selenium (software)";

        searchBO.searchFor(searchTerm);
        String actualHeading = searchBO.getFirstHeading();

        Assert.assertEquals(actualHeading, expectedHeading, "The first heading should match the search term.");
    }

    @AfterMethod
    public void tearDown() {
        DriverPool.quitDriver();
    }
}
