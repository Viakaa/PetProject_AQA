package aqa.db;

import org.testng.annotations.DataProvider;

public class LoginDataProvider {

    @DataProvider(name = "users")
    public static Object[][] users() {
        return new Object[][]{
                {"Carloscarlos123112", "TestPassword"},
//                {"testuseraccountforapi", "TestPassword"},
//                {"TestUserApi112", "TestPassword112"}
        };
    }
}
