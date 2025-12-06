package aqa.ui;

import aqa.bo.HomeBO;
import aqa.DriverPool;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

@Listeners({aqa.listeners.CustomAllureListener.class, aqa.listeners.CustomListener.class})
public class TestLinkPresent {

    private WebDriver driver;
    private HomeBO homeBO;

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverPool.getDriver();
        homeBO = new HomeBO(driver);
        homeBO.openHomePage();
    }

    @Test(groups = {"ui"})
    public void linkPresenceTest() {
        String searchTerm = "Selenium (software)";
        String linkText = "browser automation";

        homeBO.searchFor(searchTerm);
        boolean isLinkClicked = homeBO.isLinkPresentInArticle(linkText);

        Assert.assertTrue(isLinkClicked, "The link '" + linkText + "' should be present and clicked.");
    }


    @AfterMethod
    public void tearDown() {
        DriverPool.quitDriver();
    }
}
