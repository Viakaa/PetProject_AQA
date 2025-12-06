package aqa.bo;

import aqa.po.HomePage;
import org.openqa.selenium.WebDriver;

public class HomeBO {
    private HomePage homePage;

    public HomeBO(WebDriver driver) {
        homePage = new HomePage(driver);
    }

    public void openHomePage() {
        homePage.open("https://en.wikipedia.org");
    }

    public void searchFor(String term) {
        homePage.searchFor(term);
    }

    public String getFirstHeading() {
        return homePage.getFirstHeading();
    }

    public boolean isLinkPresentInArticle(String linkText) {
        return homePage.isLinkPresent(linkText);
    }
}
