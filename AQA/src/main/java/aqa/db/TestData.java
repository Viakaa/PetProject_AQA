package aqa.db;

import org.testng.annotations.DataProvider;

public class TestData {

    @DataProvider(name = "searchTerms")
    public static Object[][] searchTerms() {
        return new Object[][]{
                {"Selenium (software)", "Selenium (software) - Wikipedia"},
                {"Java (programming language)", "Java (programming language) - Wikipedia"}
        };
    }
}
