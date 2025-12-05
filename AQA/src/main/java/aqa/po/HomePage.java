package aqa.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    @FindBy(id = "wccom-search__q")
    private WebElement searchInput;

    @FindBy(css = "button.wccom-search__search-icon")
    private WebElement searchButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterSearch(String text) {
        searchInput.sendKeys(text);
    }

    public void clickSearch() {
        searchButton.click();
    }
}
