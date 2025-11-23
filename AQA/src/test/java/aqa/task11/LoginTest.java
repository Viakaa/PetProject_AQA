package aqa.task11;

import aqa.DriverPool;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class LoginTest {

    @Test
    public void loginSuccessTest() {
        LoginBO loginBO = new LoginBO(DriverPool.getDriver());

        boolean result = loginBO.login("standard_user", "secret_sauce");

        Assert.assertTrue(result, "Login should be successful");
    }

    @AfterMethod
    public void quit() {
        DriverPool.quitDriver();
    }
}
