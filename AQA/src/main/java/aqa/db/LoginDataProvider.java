package aqa.db;

import org.testng.annotations.DataProvider;

public class LoginDataProvider {

    @DataProvider(name = "users")
    public static Object[][] users() {
        return new Object[][]{
                {"Carloscarlos123112", "TestPassword"},
                {"Carloscarlos123113", "TestPassword112"}
        };
    }
}
