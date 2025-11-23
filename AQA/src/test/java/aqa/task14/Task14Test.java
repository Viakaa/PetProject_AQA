package aqa.task14;

import aqa.DriverPool;
import aqa.task12.LoginBO;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Listeners;

@Listeners({CustomListener.class})
public class Task14Test {

    LoginBO loginBO;

    @BeforeTest
    void setup() {
        DriverPool.getDriver().get("https://www.saucedemo.com/");
        loginBO = new LoginBO(DriverPool.getDriver());
    }

    @Test
    void loginSuccessTest() {
        loginBO.login("standard_user", "secret_sauce");

        String currentUrl = DriverPool.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"),
                "Login should be successful, but current URL: " + currentUrl);
        System.out.println("Login successful, navigated to: " + currentUrl);
    }

    @Test
    void loginFailTest() {
        loginBO.login("user", "secret_sauce");

        String currentUrl = DriverPool.getDriver().getCurrentUrl();
        Assert.fail("This test is intentionally failed to check fail scenario");
        System.out.println("Login failed as expected for locked_out_user. Current URL: " + currentUrl);
    }

    @AfterTest
    void teardown() {
        System.out.println("Driver quit done");
        DriverPool.quitDriver();
    }
}
