package aqa.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DetailsPage {

    private WebDriver driver;

    private By themeTitle = By.cssSelector("h1.wccom-product-title__product-name");
    private By themeDescription = By.cssSelector("div.wccom-product-short-description");

    public DetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getThemeTitle() {
        return driver.findElement(themeTitle).getText().trim();
    }

    public String getThemeDescription() {
        return driver.findElement(themeDescription).getText().trim();
    }
}
