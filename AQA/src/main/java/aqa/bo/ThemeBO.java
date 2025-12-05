package aqa.bo;

import aqa.po.SearchResultsPage;
import aqa.po.DetailsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ThemeBO {

    private WebDriver driver;
    private SearchResultsPage resultsPage;
    private DetailsPage detailsPage;

    public ThemeBO(WebDriver driver) {
        this.driver = driver;
        resultsPage = new SearchResultsPage(driver);
        detailsPage = new DetailsPage(driver);
    }

    public String getFirstThemeTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement firstTheme = wait.until(ExpectedConditions.visibilityOf(resultsPage.getFirstThemeElement()));

        wait.until(driver -> !firstTheme.getText().isEmpty());

        return firstTheme.getText().trim();
    }


    public void openFirstFoundTheme() {
        getFirstThemeTitle();
        resultsPage.openFirstProduct();
    }

}
