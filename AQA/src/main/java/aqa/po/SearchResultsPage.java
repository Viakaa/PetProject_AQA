package aqa.po;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchResultsPage {
    private final WebDriverWait wait;

    private final By firstResultLocator = By.xpath("(//div[@class='mw-search-result-heading']/a)[1]");

    public SearchResultsPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openFirstResult() {
        WebElement firstArticle = wait.until(ExpectedConditions.elementToBeClickable(firstResultLocator));
        firstArticle.click();
    }
}