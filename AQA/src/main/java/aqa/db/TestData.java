package aqa.db;

import org.testng.annotations.DataProvider;

public class TestData {

    @DataProvider(name = "searchTerms")
    public static Object[][] searchTerms() {
        return new Object[][]{
                {"Selenium (software)", "Selenium (software)", "selenium.dev"},
                {"Java (programming language)", "Java (programming language)", "oracle.com"}
        };
    }
}
