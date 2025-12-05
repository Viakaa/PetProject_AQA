package aqa.ui;

import aqa.DriverPool;
import aqa.bo.SearchBO;
import aqa.bo.ThemeBO;
import aqa.po.DetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;

public class OpenFirstThemeTest {

    @Test
    public void verifyFirstThemeDetails() {
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

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DetailsPage details = new DetailsPage(driver);

        String themeTitle = details.getThemeTitle();
        Assert.assertNotNull(themeTitle, "Theme title should be present");

        String themeDescription = details.getThemeDescription();
        Assert.assertNotNull(themeDescription, "Theme description should be present");

        System.out.println("Theme title: " + themeTitle);
        System.out.println("Theme description: " + themeDescription);
    }

    @AfterMethod
    public void tearDown() {
        DriverPool.quitDriver();
    }
}
