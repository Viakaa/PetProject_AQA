package aqa.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(name = "search")
    private WebElement searchInput;

    @FindBy(xpath = "//a[text()='browser automation']")
    private List<WebElement> links;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void open(String url) {
        driver.get(url);
    }

    public void searchFor(String term) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(term);
        searchInput.submit();

        wait.until(ExpectedConditions.titleContains(term));
    }

    public String getFirstHeading() {
        return driver.getTitle();
    }

    public boolean isLinkPresent(String linkText) {
        try {
            WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//a[text()='" + linkText + "']")));
            wait.until(ExpectedConditions.elementToBeClickable(link));
            link.click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
