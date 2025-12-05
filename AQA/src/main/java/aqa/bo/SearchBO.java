package aqa.bo;

import aqa.po.SearchPage;
import org.openqa.selenium.WebDriver;

public class SearchBO {
    private SearchPage searchPage;

    public SearchBO(WebDriver driver) {
        this.searchPage = new SearchPage(driver);
    }

    public void searchForTheme(String themeName) {
        searchPage.enterSearchTerm(themeName);
        searchPage.clickSearch();
    }
}
