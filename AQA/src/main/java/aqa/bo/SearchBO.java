package aqa.bo;

import aqa.po.HomePage;
import aqa.po.SearchResultsPage;
import org.openqa.selenium.WebDriver;

public class SearchBO {
    private HomePage homePage;
    private SearchResultsPage resultsPage;

    public SearchBO(WebDriver driver) {
        homePage = new HomePage(driver);
        resultsPage = new SearchResultsPage(driver);
    }

    public void searchFor(String term) {
        homePage.searchFor(term);
    }

    public String getFirstHeading() {
        return resultsPage.getPageTitle();
    }
}
