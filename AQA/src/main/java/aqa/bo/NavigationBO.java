package aqa.bo;

import aqa.po.*;
import org.openqa.selenium.WebDriver;

public class NavigationBO {
    private final HomePage homePage;
    private final SearchResultsPage resultsPage;
    private final ArticlePage articlePage;
    private final PageSidebar pageSidebar;
    private final RandomArticlePage randomArticlePage;

    public NavigationBO(WebDriver driver) {
        homePage = new HomePage(driver);
        resultsPage = new SearchResultsPage(driver);
        articlePage = new ArticlePage(driver);
        pageSidebar = new PageSidebar(driver);
        randomArticlePage = new RandomArticlePage(driver);
    }

    public void openHomePage() {
        homePage.open("https://en.wikipedia.org");
    }

    public void search(String keyword) {
        homePage.searchFor(keyword);
    }

    public void searchMenu(String keyword){homePage.searchForMenu(keyword);}

    public void clickArticle() {
        resultsPage.openFirstResult();
    }

    public void openExternalLink(String linkText) {
        articlePage.scrollToExternalLinks();
        articlePage.openExternalLink(linkText);
    }

    public String getCurrentUrl() {
        return articlePage.getUrl();
    }

    public String getHeading() {
        return articlePage.getTitle();
    }

    public void ClickRandomArticle() {
        pageSidebar.clickRandomArticle();
    }

    public String switchArticleToFirstLanguage() {
        return randomArticlePage.switchToFirstLanguage();
    }

}