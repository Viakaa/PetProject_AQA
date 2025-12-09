package aqa.db;

import org.testng.annotations.DataProvider;

public class LinkTestData {
    @DataProvider(name = "linkData")
    public Object[][] linkData() {
        return new Object[][]{
                {"Selenium (software)", "browser automation", "Headless browser"},
                {"Java (programming language)", "high-level", "High-level programming language"}
        };
    }

}
