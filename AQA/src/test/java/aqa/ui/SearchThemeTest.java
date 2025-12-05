package aqa.ui;

import aqa.DriverPool;
import aqa.bo.SearchBO;
import aqa.bo.ThemeBO;
import aqa.po.HomePage;
import org.testng.Assert;
import org.testng.annotations.*;

public class SearchThemeTest {

    @DataProvider(name = "themes")
    public Object[][] themes() {
        return new Object[][]{
                {"Storefront"},
                {"Astra"},
                {"Fasino"}
        };
    }

    @Test(dataProvider = "themes")
    public void verifyThemeFound(String themeName) {
        var driver = DriverPool.getDriver();
        driver.get("https://woocommerce.com/search/?collections=theme&page=1");

        SearchBO search = new SearchBO(driver);
        ThemeBO theme = new ThemeBO(driver);

        search.searchForTheme(themeName);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(
                theme.getFirstThemeTitle().toLowerCase().contains(themeName.toLowerCase()),
                "Theme not found in results!"
        );
    }


    @AfterMethod
    public void tearDown() {
        DriverPool.quitDriver();
    }
}
