package aqa.po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {
    private WebDriver driver;

    @FindBy(id = "wccom-search__q")
    private WebElement searchInput;

    @FindBy(css = "button.wccom-search__search-icon")
    private WebElement searchButton;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterSearchTerm(String term) {
        searchInput.clear();
        searchInput.sendKeys(term);
    }

    public void clickSearch() {
        searchButton.click();
    }
}
