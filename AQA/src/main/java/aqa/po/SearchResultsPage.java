package aqa.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultsPage {

    private WebDriver driver;
    private By themeLinks = By.cssSelector("section.wccom-comp-card-theme div.wccom-card__content h3 a");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
    }
    public WebElement getFirstThemeElement() {
        List<WebElement> themes = driver.findElements(themeLinks);
        if (themes.isEmpty()) {
            throw new RuntimeException("No themes found on search results page");
        }
        return themes.get(0);
    }

    public void openFirstProduct() {
        getFirstThemeElement().click();
    }
}
