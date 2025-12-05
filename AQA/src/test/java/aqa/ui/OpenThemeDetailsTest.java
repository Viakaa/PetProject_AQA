package aqa.ui;

import aqa.DriverPool;
import aqa.bo.SearchBO;
import aqa.bo.ThemeBO;
import aqa.po.HomePage;
import org.testng.Assert;
import org.testng.annotations.*;

public class OpenThemeDetailsTest {

    @Test
    public void verifyThemeDetailsIsOpened() {
        var driver = DriverPool.getDriver();
        driver.get("https://woocommerce.com/search/?collections=theme&page=1");

        SearchBO search = new SearchBO(driver);
        ThemeBO theme = new ThemeBO(driver);

        search.searchForTheme("Storefront");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        theme.openFirstFoundTheme();

        Assert.assertTrue(driver.getTitle().contains("Storefront"),
                "Theme details page was not opened!");
    }

    @AfterMethod
    public void tearDown() {
        DriverPool.quitDriver();
    }
}
